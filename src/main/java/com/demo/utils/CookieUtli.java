package com.demo.utils;

import com.demo.constant.NormalConstant;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Cookie辅助类，取出token信息
 */
@Component
public class CookieUtli {

    public Cookie findAuthStr(HttpServletRequest request){
        //取出浏览器的cookies
        Cookie[] cookie = request.getCookies();
        if(cookie == null) return null;
        int found = 0;
        //取出需要的用户信息
        for (int i = 0; i < cookie.length; i++) {
            //找到就返回该cookie
            if(cookie[i].getName().equals(NormalConstant.AUTHORIZATION) && !cookie[i].getValue().isEmpty()){
                found = i;
            }
        }
        //默认返回第一个cookie
        return cookie[found];
    }
}
