package com.jrelax.weixin.menu;

import net.sf.json.JSONObject;

public abstract class WxEventMenu extends WxMenu{
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json =  super.toJson();
        json.element("key", this.key);

        return json;
    }
}
