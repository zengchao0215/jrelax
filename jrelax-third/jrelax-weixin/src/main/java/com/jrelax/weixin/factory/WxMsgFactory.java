package com.jrelax.weixin.factory;

import com.jrelax.weixin.event.WxEvent;
import com.jrelax.weixin.msg.WxMsg;
import com.jrelax.weixin.msg.req.WxReqMsg;

import java.util.Date;

public class WxMsgFactory {
    /**
     * 将发来的消息转换为发送的消息
     * 设置FromUserName、ToUserName
     *
     * @param clazz
     * @param reqMsg
     * @param <T>
     * @return
     */
    public static <T extends WxMsg> T createFromReqMsg(Class<T> clazz, WxReqMsg reqMsg) {
        try {
            T msg = clazz.newInstance();
            msg.setToUserName(reqMsg.getFromUserName());
            msg.setFromUserName(reqMsg.getToUserName());
            msg.setCreateTime(new Date());

            return msg;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends WxMsg> T createFromEvent(Class<T> clazz, WxEvent event) {
        try {
            T msg = clazz.newInstance();
            msg.setToUserName(event.getFromUserName());
            msg.setFromUserName(event.getToUserName());
            msg.setCreateTime(new Date());

            return msg;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
