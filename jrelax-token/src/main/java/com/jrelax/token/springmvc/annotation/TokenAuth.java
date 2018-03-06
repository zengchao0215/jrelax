package com.jrelax.token.springmvc.annotation;

import com.jrelax.token.TokenSessionConfig;

import java.lang.annotation.*;

/**
 * Token认证注解
 * Created by zengchao on 2017-03-23.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenAuth {
    String value() default TokenSessionConfig.TOKEN_USER;
}
