package com.jrelax.weixin;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信参数配置
 */
public class WxConfig {
    private static Map<String, WxConfig> wxConfigMap = new HashMap<>();
    private String appId = "";
    private String appSecret = "";
    private String token = "";

    private WxConfig() {
    }

    public static WxConfig config(String appId, String appSecret) {
        return config(appId, appSecret, null);
    }

    public static WxConfig config(String appId, String appSecret, String token) {
        WxConfig wxConfig = new WxConfig();

        wxConfig.appId = appId;
        wxConfig.appSecret = appSecret;
        wxConfig.token = token;

        wxConfigMap.put(appId, wxConfig);
        return wxConfig;
    }

    public static WxConfig get(String appId) {
        return wxConfigMap.get(appId);
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
