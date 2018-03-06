package com.jrelax.token.springmvc.annotation;

import java.lang.annotation.*;

/**
 * TokenSession参数注解
 * Created by zengchao on 2017-03-23.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenSessionAttribute {
    String value();
}
