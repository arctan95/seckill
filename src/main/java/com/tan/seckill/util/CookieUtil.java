package com.tan.seckill.util;

import com.tan.seckill.redis.UserKey;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CookieUtil
 * @Description TODO
 * @Author tan
 * @Date 2020/4/15 13:58
 * @Version 1.0
 **/
public class CookieUtil {

    public static final String COOKIE_NAME_TOKEN = "token";

    //遍历所有cookie，找到需要的那个cookie
    public static String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }


    // 写入cookie
    public static void writeCookie(HttpServletResponse response, String token){
        Cookie cookie = new Cookie(CookieUtil.COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    // 删除cookie
    public static void delCookie(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                if(StringUtils.equals(ck.getName(),COOKIE_NAME_TOKEN)){
                    ck.setPath("/");
                    //设置成0，代表删除此cookie
                    ck.setMaxAge(0);
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }
}
