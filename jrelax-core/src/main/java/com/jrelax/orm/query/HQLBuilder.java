package com.jrelax.orm.query;

import java.util.ArrayList;
import java.util.List;

/**
 * HQL构建器
 */
public class HQLBuilder extends Builder {

    public HQLBuilder(String base) {
        super(base);
    }

    public String getHQL() {
        return super.getQuery().toString();
    }
}
