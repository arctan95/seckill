package com.tan.seckill.redis;

/**
 * @ClassName SeckillKey
 * @Description TODO
 * @Author tan
 * @Date 2020/4/18 23:37
 * @Version 1.0
 **/
public class SeckillKey extends BasePrefix {

    private SeckillKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }

    public static SeckillKey isGoodsOver = new SeckillKey(0,"go");
    public static SeckillKey getSeckillPath = new SeckillKey(60,"sp");
}
