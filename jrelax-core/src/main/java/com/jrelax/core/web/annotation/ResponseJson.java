package com.jrelax.core.web.annotation;

import java.lang.annotation.*;

/**
 * 响应JSON配置
 * Created by zengchao on 2017-02-14.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseJson {
    boolean jsonp() default false;
    String jsoupFunction() default "callback";
    String[] includes() default {};
    String[] excludes() default {};
}
