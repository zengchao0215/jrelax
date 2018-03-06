package com.jrelax.weixin.menu;

import net.sf.json.JSONObject;

/**
 * 跳转图文消息URL用户点击view_limited类型按钮后，
 * 微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，永久素材类型只支持图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
 */
public class WxViewLimitedMenu extends WxMenu {
    {
        super.setType("view_limited");
    }

    private String mediaId = "";

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json =  super.toJson();
        json.element("media_id", this.mediaId);

        return json;
    }
}
