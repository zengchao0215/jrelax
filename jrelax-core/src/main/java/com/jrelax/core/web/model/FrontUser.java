package com.jrelax.core.web.model;

/**
 * 前端用户接口
 */
public interface FrontUser extends Model{
    String getUserName();

    String getRealName();

    String getEmail();

    String getPhone();
}
