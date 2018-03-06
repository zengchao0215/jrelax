package com.jrelax.core.web.annotation;

import java.lang.annotation.*;

/**
 * List集合解析
 * Created by zengchao on 2017-02-17.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestList {
    /**
     * 参数名
     * @return
     */
    String value();

    /**
     * 分隔符
     * @return
     */
    String separator() default ",";
}
