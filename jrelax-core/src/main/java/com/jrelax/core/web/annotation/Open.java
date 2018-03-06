package com.jrelax.core.web.annotation;

import java.lang.annotation.*;

/**
 * 开放连接
 * Created by zengchao on 2017/4/19.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Open {
    OpenScope scope() default OpenScope.ALL;
}
