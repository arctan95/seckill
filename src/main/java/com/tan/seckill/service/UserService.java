package com.tan.seckill.service;

import com.tan.seckill.bean.User;
import com.tan.seckill.exception.GlobalException;
import com.tan.seckill.mapper.UserMapper;
import com.tan.seckill.redis.RedisService;
import com.tan.seckill.redis.UserKey;
import com.tan.seckill.result.CodeMsg;
import com.tan.seckill.util.CookieUtil;
import com.tan.seckill.util.MD5Util;
import com.tan.seckill.util.UUIDUtil;
import com.tan.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author tan
 * @Date 2020/4/15 11:20
 * @Version 1.0
 **/
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisService redisService;

    public User getById(long id) {
        // 对象缓存
        User user = redisService.get(UserKey.getById, "" + id, User.class);
        if (user != null) {
            return user;
        }

        // 取数据库
        user = userMapper.getById(id);

        //再存入缓存
        if (user != null) {
            redisService.set(UserKey.getById, "" + id, user);
        }
        return user;
    }

    /**
     * @description 典型缓存同步场景：更新密码
     * @author tan
     * @date 2020/4/15 13:52
     * @param token
     * @param id
     * @param formPass
     * @return boolean
     **/
    public boolean updatePassword(String token, long id, String formPass) {
        //取user
        User user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        User toBeUpdate = new User();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass,user.getSalt()));
        userMapper.update(toBeUpdate);
        //更新缓存：先删除再插入
        redisService.delete(UserKey.getById, "" + id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(UserKey.token, token, user);
        return true;
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        User user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成唯一id作为token
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    /**
     * @description 将token作为key，用户信息作为value存入redis模拟session 同时将token存入cookie，保存登录状态
     * @author tan
     * @date 2020/4/16 16:13
     * @param response
     * @param token
     * @param user
     * @return void
     **/
    public void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKey.token, token, user);
        CookieUtil.writeCookie(response, token);
    }

    /**
     * @description 根据token获取用户信息
     * @author tan
     * @date 2020/4/16 16:29
     * @param response
     * @param token
     * @return com.tan.seckill.bean.User
     **/
    public User getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        // 延长session有效期，有效期等于最后一次操作+有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.getCookieValue(request, CookieUtil.COOKIE_NAME_TOKEN);
        redisService.delete(UserKey.token, token);
        CookieUtil.delCookie(request,  response);
    }
}
