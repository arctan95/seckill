package com.tan.seckill.controller;

import com.tan.seckill.bean.OrderInfo;
import com.tan.seckill.bean.User;
import com.tan.seckill.redis.RedisService;
import com.tan.seckill.result.CodeMsg;
import com.tan.seckill.result.Result;
import com.tan.seckill.service.GoodsService;
import com.tan.seckill.service.OrderService;
import com.tan.seckill.service.UserService;
import com.tan.seckill.vo.GoodsVo;
import com.tan.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName OrderController
 * @Description TODO
 * @Author tan
 * @Date 2020/4/25 20:50
 * @Version 1.0
 **/
@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, User user, @RequestParam("orderId") long orderId) {
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }

}
