package com.jrelax.weixin.msg;

import java.util.Date;

/**
 * 对外发送的消息基类
 */
public abstract class WxMsg {
    private String toUserName;//接收方账号（用户的OpenId）
    private String fromUserName;//开发者微信号
    private Date createTime = new Date();//消息创建时间
    private WxMsgType msgType;//消息类型

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public WxMsgType getMsgType() {
        return msgType;
    }

    protected void setMsgType(WxMsgType msgType) {
        this.msgType = msgType;
    }

    public String toXml() {
        StringBuilder xml = new StringBuilder();
        xml.append("<ToUserName><![CDATA[").append(this.toUserName).append("]]></ToUserName>").append(System.lineSeparator());
        xml.append("<FromUserName><![CDATA[").append(this.fromUserName).append("]]></FromUserName>").append(System.lineSeparator());
        long time = 0;
        if (this.getCreateTime() != null)
            time = this.getCreateTime().getTime() / 1000;
        xml.append("<CreateTime><![CDATA[|").append(time).append("]]></CreateTime>").append(System.lineSeparator());
        xml.append("<MsgType><![CDATA[").append(this.getMsgType()).append("]]></MsgType>").append(System.lineSeparator());
        return xml.toString();
    }

    protected String getXmlStart() {
        return "<xml>";
    }

    protected String getXmlEnd() {
        return "</xml>";
    }
}
