package com.jrelax.weixin.menu;

import net.sf.json.JSONObject;

/**
 * 微信菜单
 */
public abstract class WxMenu {
    private String name;//菜单名称
    private String type;//菜单类型

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }

    /**
     * 转换为JSON
     * @return
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.element("name", this.name);
        json.element("type", this.type);

        return json;
    }

    @Override
    public String toString() {
        return this.toJson().toString();
    }
}
