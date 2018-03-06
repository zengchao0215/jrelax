package com.jrelax.token;

import javax.servlet.http.HttpServletRequest;

/**
 * TokenSession工具类
 * Created by zengchao on 2017-03-23.
 */
public class TokenSessionUtil {
    public static TokenSession getSession(HttpServletRequest request){
        TokenSession session = null;
        Object value = request.getAttribute(TokenSessionConfig.ATTRIBUTE_NAME);
        if(value != null && value instanceof TokenSession){
            session = (TokenSession) value;
        }
        return session;
    }
}
