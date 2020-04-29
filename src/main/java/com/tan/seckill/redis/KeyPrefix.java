package com.tan.seckill.redis;

/**
 * @ClassName KeyPrefix
 * @Description 缓冲key前缀
 * @Author tan
 * @Date 2020/4/15 11:32
 * @Version 1.0
 **/
public interface KeyPrefix {

    /**
     * @description 有效期
     * @author tan
     * @date 2020/4/15 11:33
     * @return int
     **/
    public int expireSeconds();

    /**
     * @description 前缀
     * @author tan
     * @date 2020/4/15 11:33
     * @return java.lang.String
     **/
    public String getPrefix();
}
