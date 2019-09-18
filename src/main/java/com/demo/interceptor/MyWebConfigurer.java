package com.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 拦截器配置类
 * @author jack
 */
@Configuration
public class MyWebConfigurer implements WebMvcConfigurer {
    @Resource
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        /**
         * 添加需要拦截的资源和接口
         * excludePathPatterns（）是除了括号里的内容都将被拦截，还可以拦截图片、文字等多种资源
         */
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/getbyname");
//        registry.addInterceptor(loginInterceptor).excludePathPatterns("/login","/index");
    }
}
