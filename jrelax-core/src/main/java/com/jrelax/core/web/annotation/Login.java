package com.jrelax.core.web.annotation;

import java.lang.annotation.*;

/**
 * 标记为登录请求
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
    LoginType type() default LoginType.ADMIN;
}
