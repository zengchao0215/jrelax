package com.jrelax.weixin.handle;

import com.jrelax.weixin.WxLog;
import com.jrelax.weixin.event.WxEvent;
import com.jrelax.weixin.factory.WxEventFactory;
import com.jrelax.weixin.factory.WxReqMsgFactory;
import com.jrelax.weixin.msg.WxMsg;
import com.jrelax.weixin.msg.req.WxReqMsg;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信消息和事件处理程序
 * 由于微信推送消息和事件时不发送appId，无法支持多公众号的Handle程序
 */
public class WxHandler {
    private List<WxMessageHandle> messageHandles = new ArrayList<>();
    private List<WxEventHandle> eventHandles = new ArrayList<>();

    public List<WxMessageHandle> getMessageHandles() {
        return messageHandles;
    }

    public void setMessageHandles(List<WxMessageHandle> messageHandles) {
        this.messageHandles = messageHandles;
    }

    public WxHandler addMessageHandle(WxMessageHandle messageHandle){
        this.messageHandles.add(messageHandle);
        return this;
    }

    public List<WxEventHandle> getEventHandles() {
        return eventHandles;
    }

    public void setEventHandles(List<WxEventHandle> eventHandles) {
        this.eventHandles = eventHandles;
    }

    public WxHandler addEventHandle(WxEventHandle eventHandle){
        this.eventHandles.add(eventHandle);
        return this;
    }

    public String processRequest(JSONObject json){
        try {
            String type = json.getString("MsgType");
            if ("event".equals(type)) {
                WxEvent event = WxEventFactory.createFromJSON(json);
                if (event != null) {
                    WxLog.info("收到事件：" + json);
                    //事件处理
                    List<WxEventHandle> eventHandles = this.getEventHandles();
                    WxMsg wxMsg = null;
                    for (WxEventHandle handle : eventHandles) {
                        if (handle.supported(event)) {
                            wxMsg = handle.handle(event);
                            if (wxMsg != null) {
                                break;
                            }
                        }
                    }
                    if (wxMsg != null)
                        return wxMsg.toXml();
                } else {
                    WxLog.info("事件解析失败");
                }
            } else {
                WxReqMsg msg = WxReqMsgFactory.createFromJSON(json);
                if (msg != null) {
                    WxLog.info("收到消息：" + json);
                    //消息处理
                    List<WxMessageHandle> messageHandles = this.getMessageHandles();
                    WxMsg wxMsg = null;
                    for (WxMessageHandle handle : messageHandles) {
                        if (handle.supported(msg)) {
                            wxMsg = handle.handle(msg);
                            if (wxMsg != null) {//如果有一个程序返回了非null的WxMsg对象，则之后的处理程序均不再执行
                                break;
                            }
                        }
                    }
                    if (wxMsg != null)
                        return wxMsg.toXml();
                } else {
                    WxLog.info("消息解析失败");
                }
            }
        } catch (Exception e) {
            WxLog.info("推送消息解析异常：" + e.getMessage());
        }
        return "success";
    }
}
