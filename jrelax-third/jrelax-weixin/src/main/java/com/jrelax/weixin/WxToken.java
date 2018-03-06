package com.jrelax.weixin;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信全局接口凭证
 */
public class WxToken {
    private static Map<String, WxToken> wxTokenMap = new HashMap<>();

    private WxConfig wxConfig;
    private String accessToken = null;
    private int expiresIn = 0;//过期时间
    private long lastUpdateTime = 0;//最后更新时间
    private Thread thread = null;//定时任务
    private boolean stop = false;

    private WxToken() {

    }

    /**
     * 获取
     *
     * @param wxConfig
     * @return
     */
    public static WxToken get(WxConfig wxConfig) {
        WxToken wxToken = wxTokenMap.get(wxConfig.getAppId());
        if (wxToken == null)
            wxToken = new WxToken();
        wxToken.wxConfig = wxConfig;
        wxToken.startInterval();

        wxTokenMap.put(wxConfig.getAppId(), wxToken);
        return wxToken;
    }

    /**
     * 调试使用
     * @param accessToken
     * @return
     */
    @Deprecated
    public static WxToken config(WxConfig wxConfig, String accessToken){
        WxToken wxToken = new WxToken();
        wxToken.accessToken = accessToken;
        wxToken.wxConfig = wxConfig;

        return wxToken;
    }

    /**
     * 获取接口凭证
     *
     * @return
     */
    public String getAccessToken() {
        if (accessToken == null) {
            requestToken();
        }
        return accessToken;
    }

    public WxConfig getWxConfig() {
        return wxConfig;
    }

    /**
     * 从服务器请求
     *
     * @return
     */
    private void requestToken() {
        WxLog.info("获取AccessToken, AppId:" + wxConfig.getAppId());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", wxConfig.getAppId());
        paramMap.put("secret", wxConfig.getAppSecret());
        paramMap.put("grant_type", "client_credential");

        String url = "https://api.weixin.qq.com/cgi-bin/token";
        JSONObject json = WxKit.request(wxConfig, url, paramMap);
        if (WxKit.isSuccess(json)) {
            this.accessToken = json.getString("access_token");
            this.expiresIn = json.getInt("expires_in");
        }else{
            throw WxException.fromJson(json);
        }
    }

    /**
     * 定时刷新
     */
    private void startInterval() {
        if (thread == null) {
            this.stop = false;
            thread = new Thread(() -> {
                while (!stop) {
                    try {
                        long t = (expiresIn - 60) * 1000;
                        Thread.sleep(t);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    requestToken();
                    lastUpdateTime = System.currentTimeMillis();
                }

                thread = null;
            });
            thread.start();
        }
    }

    /**
     * 停止刷新
     */
    public void stopInterval(){
        this.stop = true;
    }
}
