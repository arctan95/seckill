package com.tan.seckill.redis;

/**
 * @ClassName GoodsKey
 * @Description TODO
 * @Author tan
 * @Date 2020/4/17 12:23
 * @Version 1.0
 **/
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
    public static GoodsKey getGoodsStock = new GoodsKey(0, "gs");
}
