package com.jrelax.weixin.menu;

import net.sf.json.JSONObject;

/**
 * 跳转URL用户点击view类型按钮后，
 * 微信客户端将会打开开发者在按钮中填写的网页URL，
 * 可与网页授权获取用户基本信息接口结合，获得用户基本信息。
 */
public class WxViewMenu extends WxMenu {
    {
        super.setType("view");
    }

    private String url = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.element("url", this.url);

        return json;
    }
}
