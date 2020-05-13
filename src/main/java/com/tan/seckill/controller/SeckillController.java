package com.tan.seckill.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.tan.seckill.bean.SeckillOrder;
import com.tan.seckill.bean.User;
import com.tan.seckill.rabbitmq.MQSender;
import com.tan.seckill.rabbitmq.SeckillMessage;
import com.tan.seckill.redis.GoodsKey;
import com.tan.seckill.redis.RedisService;
import com.tan.seckill.redis.UserKey;
import com.tan.seckill.result.CodeMsg;
import com.tan.seckill.result.Result;
import com.tan.seckill.service.GoodsService;
import com.tan.seckill.service.OrderService;
import com.tan.seckill.service.SeckillService;
import com.tan.seckill.util.CookieUtil;
import com.tan.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SeckillController
 * @Description TODO
 * @Author tan
 * @Date 2020/4/18 23:13
 * @Version 1.0
 **/
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SeckillService seckillService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender sender;


    //基于令牌桶算法的限流实现类
    RateLimiter rateLimiter = RateLimiter.create(10);

    //做标记，判断该商品是否被处理过了
    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

    /**
     * @description 系统初始化调用此方法，将商品信息加载到redis和本地内存
     * @author tan
     * @date 2020/4/20 11:37
     * @return void
     **/
    @Override
    public void afterPropertiesSet() {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if (goodsVoList == null) {
            return;
        }
        for (GoodsVo goods : goodsVoList) {
            redisService.set(GoodsKey.getGoodsStock, "" + goods.getId(), goods.getStockCount());
            //初始化商品都是没有处理过的
            localOverMap.put(goods.getId(), false);
        }
    }

    /**
     * @description 获取秒杀地址
     * @author tan
     * @date 2020/5/9 0:20
     * @param request
     * @param user
     * @param goodsId
     * @return com.tan.seckill.result.Result<java.lang.String>
     **/
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getSeckillPath(HttpServletRequest request, User user,
                                         @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        String path = seckillService.createSeckillPath(user, goodsId);
        return Result.success(path);
    }

    /**
     * @description GET POST区别
     *      1、GET幂等,服务端获取数据，无论调用多少次结果都一样,不会影响服务端数据
     *      2、POST不幂等，向服务端提交数据，可能会修改服务端数据
     *      将同步下单改为异步下单
     * @author tan
     * @date 2020/4/20 11:13
     * @param user
     * @param goodsId
     * @return com.tan.seckill.result.Result<java.lang.Integer>
     **/
    @RequestMapping(value = "/{path}/seckill", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> list(User user, @RequestParam("goodsId") long goodsId, @PathVariable("path") String path) {
        if(!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            return Result.error(CodeMsg.ACCESS_LIMIT_REACHED);
        }

        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //验证path
        boolean check = seckillService.checkPath(user, goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        // 内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        // redis预减库存
        long stock = redisService.decr(GoodsKey.getGoodsStock, "" + goodsId);
        if (stock < 0) {
            afterPropertiesSet();
            long stock2 = redisService.decr(GoodsKey.getGoodsStock, "" + goodsId);
            if (stock2 < 0) {
                localOverMap.put(goodsId, true);
                return Result.error(CodeMsg.SECKILL_OVER);
            }
        }
        // 判断重复秒杀
        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        // 入队
        SeckillMessage message = new SeckillMessage();
        message.setUser(user);
        message.setGoodsId(goodsId);
        sender.sendSeckillMessage(message);
        //排队中
        return Result.success(0);
    }

    /**
     * @description orderId： 成功 -1：秒杀失败  0： 排队中
     * @author tan
     * @date 2020/4/27 20:34
     * @param model
     * @param user
     * @param goodsId
     * @return com.tan.seckill.result.Result<java.lang.Long>
     **/
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> seckillResult(Model model, User user,
                                      @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long orderId = seckillService.getSeckillResult(user.getId(), goodsId);
        return Result.success(orderId);
    }
}
