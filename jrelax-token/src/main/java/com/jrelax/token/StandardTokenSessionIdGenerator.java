package com.jrelax.token;

import java.util.UUID;

/**
 * StandardTokenSessionID生成器
 * Created by zengchao on 2017-03-22.
 */
public class StandardTokenSessionIdGenerator implements TokenSessionIdGenerator {
    public String generateSessionId() {
        String uuid = UUID.randomUUID().toString();
        if (uuid.contains("-"))
            uuid = uuid.replaceAll("-", "");
        return uuid.toUpperCase();
    }
}
