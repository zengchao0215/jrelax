package com.jrelax.core.web.annotation;

/**
 * 开放连接范围
 * Created by zengchao on 2017/4/19.
 */
public enum OpenScope {
    ALL, //所有人可以访问
    SESSION, //后台用户登陆session可以访问
    FRONT_SESSION //前台登录session可以访问
}
