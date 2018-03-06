package com.jrelax.core.web.model;

/**
 * 后台用户
 */
public interface User extends Model {
    String getUserName();

    String getPassword();

    String getRealName();

    String getPageStyle();

    String getLayout();

    String getHeadImage();

    String getEmail();

    boolean isSystemAdmin();
}
