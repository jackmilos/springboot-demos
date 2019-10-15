package com.demo.interceptor;

import com.demo.annotation.NoneAuth;
import com.demo.constant.NormalConstant;
import com.demo.entity.JsonData;
import com.demo.utils.CookieUtli;
import com.demo.utils.JsonUtils;
import com.demo.utils.token.TokenHelper;
import com.demo.utils.token.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author jack
 * 把拦截器交给spring
 * 登陆拦截器
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private CookieUtli cookieUtli;

    /**
     * 拦截逻辑
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception{
        System.out.println("经过拦截器");

        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        //如果被@NoneAuth注解代表不需要登陆验证，直接通过
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(method.getAnnotation(NoneAuth.class) != null) return true;

        //验证浏览器中cookie里和redis中的token信息是否存在
        boolean flag=false;
        if(cookieUtli.findAuthStr(request) == null) return flag;
        else {
            String authstr = cookieUtli.findAuthStr(request).getValue();
            TokenModel model = tokenHelper.get(authstr);
            //检查redis中的token信息
            if(tokenHelper.check(model)) flag = true;
        }
        if (flag) return true;

        //验证未通过
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JsonUtils.obj2String(JsonData.buildError(401,"权限未验证")));
        return false;
    }
}
