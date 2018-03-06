package com.jrelax.core.web.annotation;

import java.lang.annotation.*;

/**
 * json字符串解析
 * Created by zengchao on 2017-02-17.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestJson {
    /**
     * 参数名
     * @return
     */
    String value();
}
