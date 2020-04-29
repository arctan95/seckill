package com.tan.seckill.redis;

/**
 * @ClassName SeckillKey
 * @Description TODO
 * @Author tan
 * @Date 2020/4/18 23:37
 * @Version 1.0
 **/
public class SeckillKey extends BasePrefix {

    private SeckillKey(String prefix) {
        super(prefix);
    }

    public static SeckillKey isGoodsOver = new SeckillKey("go");
}
