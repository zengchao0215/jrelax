package com.jrelax.core.web.model;

/**
 * 系统资源
 */
public interface Resource extends Model {
    String getParentId();

    String getName();

    String getUrl();

    void setUrl(String url);

    String getIcon();

    String getDescript();

    boolean isEnabled();

    boolean hasChildren();
}
