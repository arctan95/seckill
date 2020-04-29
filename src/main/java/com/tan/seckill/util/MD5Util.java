package com.tan.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName MD5Util
 * @Description TODO
 * @Author tan
 * @Date 2020/4/15 13:48
 * @Version 1.0
 **/
public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    /**
     * 第一次MD5加密，用于网络传输
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass){
        //避免在网络传输被截取然后反推出密码，所以在md5加密前先打乱密码
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次MD5加密，用于存储到数据库
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    // 合并两次加密过程
    public static String inputPassToDbPass(String input, String saltDB){
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToDbPass("123456","1a2b3c4d"));
    }

}