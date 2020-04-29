package com.tan.seckill.vo;

import com.tan.seckill.bean.OrderInfo;

/**
 * @ClassName OrderDetailVo
 * @Description TODO
 * @Author tan
 * @Date 2020/4/25 20:59
 * @Version 1.0
 **/
public class OrderDetailVo {

    private GoodsVo goods;
    private OrderInfo order;

    public GoodsVo getGoods() {
        return goods;
    }
    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }
    public OrderInfo getOrder() {
        return order;
    }
    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
