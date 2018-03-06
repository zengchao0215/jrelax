package com.jrelax.core.web.support.http;

import javax.servlet.http.HttpServletResponse;

/**
 * HandlerResponse
 * Created by zengchao on 2017-02-27.
 */
public class HandlerResponse {
    public static HandlerResponse fromWebResponse(HttpServletResponse response){
        return new HandlerResponse();
    }
}
