package com.tan.seckill.redis;

/**
 * @ClassName OrderKey
 * @Description TODO
 * @Author tan
 * @Date 2020/4/18 23:22
 * @Version 1.0
 **/
public class OrderKey extends BasePrefix {

    public OrderKey(String prefix) {
        super(prefix);
    }
    public static OrderKey getSeckillOrderByUidGid = new OrderKey("seckill");
}
