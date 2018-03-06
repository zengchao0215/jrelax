package com.jrelax.core.web.model;

public interface Unit extends Model {
    String getName();

    String getCode();

    String getParentId();

    boolean isSystem();

    boolean isEnabled();

    boolean hasChildren();
}
