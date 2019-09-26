package com.demo.interceptor;

import com.demo.annotation.NoneAuth;
import com.demo.constant.NormalConstant;
import com.demo.entity.JsonData;
import com.demo.utils.JsonUtils;
import com.demo.utils.token.TokenHelper;
import com.demo.utils.token.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
        //token验证
        String authStr = request.getHeader(NormalConstant.AUTHORIZATION);
        TokenModel model = tokenHelper.get(authStr);

        //验证通过
        if(tokenHelper.check(model)){
            request.setAttribute(NormalConstant.CURRENT_USER_ID, model.getUserId());
            return true;
        }

        //验证未通过
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JsonUtils.obj2String(JsonData.buildError(401,"权限未验证")));
        return false;
    }
}
