package com.jrelax.weixin;

import net.sf.json.JSONObject;

public class WxException extends RuntimeException {
    private int code = 0;
    private String message = "";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public WxException() {

    }

    public WxException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static WxException fromJson(String json) {
        return fromJson(JSONObject.fromObject(json));
    }

    public static WxException fromJson(JSONObject json) {
        JSONObject obj = JSONObject.fromObject(json);
        WxException wxException = new WxException();
        wxException.setCode(Integer.parseInt(obj.getString("errcode")));
        wxException.setMessage(obj.getString("errmsg"));
        return wxException;
    }

    @Override
    public String toString() {
        return "微信接口异常：errcode: " + this.code + ", errmsg: " + this.message;
    }
}
