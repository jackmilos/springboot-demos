package com.demo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 实用本注解的方法不回进行登陆验证
 * @author jack
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface NoneAuth {
}
