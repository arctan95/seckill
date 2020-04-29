package com.tan.seckill.rabbitmq;

import com.tan.seckill.bean.User;

/**
 * @ClassName SeckillMessage
 * @Description 消息体
 * @Author tan
 * @Date 2020/4/19 21:59
 * @Version 1.0
 **/
public class SeckillMessage {

    private User user;
    private long goodsId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

}
