package com.tan.seckill.util;

import java.util.UUID;

/**
 * @ClassName UUIDUtil
 * @Description 唯一id生成类
 * @Author tan
 * @Date 2020/4/16 15:34
 * @Version 1.0
 **/
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
