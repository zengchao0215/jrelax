package com.jrelax.weixin.msg.req;

/**
 * 链接消息
 */
public class WxReqLinkMsg extends WxReqMsg {
    {
        super.setMsgType(WxReqMsgType.LINK);
    }

    private String title;//标题
    private String description;//描述
    private String url;//链接地址

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
