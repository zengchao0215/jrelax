package com.jrelax.weixin.msg.req;

import com.jrelax.kit.StringKit;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 微信消息基类
 */
public abstract class WxReqMsg {
    private String toUserName;
    private String fromUserName;
    private Date createTime;
    private WxReqMsgType msgType;
    private String msgId;

    public WxReqMsg() {
    }

    public WxReqMsg(String toUserName, String fromUserName, Date createTime, WxReqMsgType msgType, String msgId) {
        this.toUserName = toUserName;
        this.fromUserName = fromUserName;
        this.createTime = createTime;
        this.msgType = msgType;
        this.msgId = msgId;
    }

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

    public WxReqMsgType getMsgType() {
        return msgType;
    }

    protected void setMsgType(WxReqMsgType msgType) {
        this.msgType = msgType;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * 转换为xml字符串
     * @return
     */
    public String toXml() {
        StringBuilder xml = new StringBuilder("<xml>" + System.lineSeparator());
        Field[] fields = FieldUtils.getAllFields(this.getClass());

        for (Field field : fields) {
            String name = field.getName();
            name = StringKit.firstUpperCase(name);
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (value instanceof Date) {
                    value = ((Date) value).getTime();
                }
                xml.append("<").append(name).append("><![CDATA[").append(value).append("]]></").append(name).append(">").append(System.lineSeparator());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        xml.append("</xml>");
        return xml.toString();
    }

    /**
     * 转换为JSON字符串
     * @return
     */
    public String toJson(){
        JSONObject json = new JSONObject();
        Field[] fields = FieldUtils.getAllFields(this.getClass());

        for (Field field : fields) {
            String name = field.getName();
            name = StringKit.firstUpperCase(name);
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (value instanceof Date) {
                    value = ((Date) value).getTime();
                }
                json.element(name, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return json.toString();
    }

    @Override
    public String toString() {
        return this.toXml();
    }

    public <T extends WxReqMsg> T toMsg(Class<T> clazz){
        return (T) this;
    }
}
