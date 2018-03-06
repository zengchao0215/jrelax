package com.jrelax.core.web.annotation;

import java.lang.annotation.*;

/**
 * 视图名前缀
 * Created by zengchao on 2017-02-24.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ViewPrefix {
    //视图前缀名
    String value();
}
