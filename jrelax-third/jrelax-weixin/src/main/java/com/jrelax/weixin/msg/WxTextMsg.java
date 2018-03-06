package com.jrelax.weixin.msg;

/**
 * 文本消息
 */
public class WxTextMsg extends WxMsg {
    {
        super.setMsgType(WxMsgType.TEXT);
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toXml() {
        StringBuilder xml = new StringBuilder(super.toXml());
        xml.append("<Content><![CDATA[").append(this.content).append("]]></Content>").append(System.lineSeparator());

        return super.getXmlStart() + System.lineSeparator() + xml.toString() + super.getXmlEnd();
    }
}
