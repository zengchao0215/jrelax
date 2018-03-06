package com.jrelax.weixin.api;

import com.jrelax.weixin.WxKit;
import com.jrelax.weixin.WxToken;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信用户API
 */
public class WxUserApi {
    public static final String URL_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 获取OpenId
     * @param wxToken
     * @param code
     * @return
     */
    public static String getOpenId(WxToken wxToken, String code){
        String openId = "";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", wxToken.getWxConfig().getAppId());
        paramMap.put("secret", wxToken.getWxConfig().getAppSecret());
        paramMap.put("code", code);
        paramMap.put("grant_type", "authorization_code");

        JSONObject json = WxKit.request(URL_OPENID, paramMap);
        if(WxKit.isSuccess(json))
            openId = json.getString("openid");
        return openId;
    }
}
