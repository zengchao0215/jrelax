package com.jrelax.weixin.msg.req;

/**
 * 文本消息
 */
public class WxReqTextMsg extends WxReqMsg {
    {
        setMsgType(WxReqMsgType.TEXT);
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
