package com.jrelax.test;

import java.util.Map;

/**
 * Api测试接口
 * Created by zengc on 2016-05-20.
 */
public interface IApiTest {
    /**
     * 请求类型
     */
    enum RequestMethod{
        GET,POST,PUT
    }

    /**
     * 请求
     * @param method
     * @param url
     * @param params
     * @param response 地址响应内容，key=response
     * @return HTTP状态值
     */
    public int request(RequestMethod method, String url, Map<String, String> params, Map<String, String> response);
}
