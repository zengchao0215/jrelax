package com.jrelax.aop.annotation;

import java.lang.annotation.*;

/**
 * 系统日志记录
 * Created by zengchao on 2017-02-25.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String content();//日志内容
    String name();//功能模块名
    Level level() default Level.INFO;//日志级别
    Target target() default Target.ALL;//日志记录到哪

    enum Level{
        DEBUG, INFO
    }

    enum Target{
        ALL, DB, LOGGER
    }
}
