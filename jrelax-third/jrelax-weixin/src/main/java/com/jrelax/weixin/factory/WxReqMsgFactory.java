package com.jrelax.weixin.factory;

import com.jrelax.weixin.msg.req.*;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * 微信消息工厂
 */
public class WxReqMsgFactory {

    /**
     * 创建通用消息
     *
     * @param clazz
     * @param toUserName
     * @param fromUserName
     * @param msgId
     * @param <T>
     * @return
     */
    public static <T extends WxReqMsg> T createMsg(Class<T> clazz, String toUserName, String fromUserName, String msgId) {
        try {
            T msg = clazz.newInstance();
            msg.setToUserName(toUserName);
            msg.setFromUserName(fromUserName);
            msg.setCreateTime(new Date());
            msg.setMsgId(msgId);

            return msg;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 从JSON中创建msg
     *
     * @param json
     * @return
     */
    public static WxReqMsg createFromJSON(JSONObject json) {
        WxReqMsg msg = null;
        String type = json.getString("MsgType");
        WxReqMsgType msgType = WxReqMsgType.valueOf2(type);
        switch (msgType) {
            case TEXT:
                WxReqTextMsg textMsg = new WxReqTextMsg();
                textMsg.setContent(json.getString("Content"));

                msg = textMsg;
                break;
            case IMAGE:
                WxReqImageMsg imageMsg = new WxReqImageMsg();
                imageMsg.setMediaId(json.getString("MediaId"));
                imageMsg.setPicUrl(json.getString("PicUrl"));

                msg = imageMsg;
                break;
            case VOICE:
                WxReqVoiceMsg voiceMsg = new WxReqVoiceMsg();
                voiceMsg.setMediaId(json.getString("MediaId"));
                voiceMsg.setFormat(json.getString("Format"));
                voiceMsg.setRecognition(json.getString("Recognition"));

                msg = voiceMsg;
                break;
            case VIDEO:
                WxReqVideoMsg videoMsg = new WxReqVideoMsg();
                videoMsg.setMediaId(json.getString("MediaId"));
                videoMsg.setThumbMediaId(json.getString("ThumbMediaId"));

                msg = videoMsg;
                break;
            case SHORT_VIDEO:
                WxReqShortVideoMsg shortVideoMsg = new WxReqShortVideoMsg();
                shortVideoMsg.setMediaId(json.getString("MediaId"));
                shortVideoMsg.setThumbMediaId(json.getString("ThumbMediaId"));

                msg = shortVideoMsg;
                break;
            case LOCATION:
                WxReqLocationMsg locationMsg = new WxReqLocationMsg();
                locationMsg.setLon(Double.parseDouble(json.getString("Location_X")));
                locationMsg.setLat(Double.parseDouble(json.getString("Location_Y")));
                locationMsg.setScale(Double.parseDouble(json.getString("Scale")));
                locationMsg.setLabel(json.getString("Label"));

                msg = locationMsg;
                break;
            case LINK:
                WxReqLinkMsg wxLinkMsg = new WxReqLinkMsg();
                wxLinkMsg.setTitle(json.getString("Title"));
                wxLinkMsg.setDescription(json.getString("Description"));
                wxLinkMsg.setUrl(json.getString("Url"));

                msg = wxLinkMsg;
                break;
        }
        msg.setToUserName(json.getString("ToUserName"));
        msg.setFromUserName(json.getString("FromUserName"));
        msg.setMsgId(json.getString("MsgId"));
        long createTime = Long.parseLong(json.getString("CreateTime"));
        Date date = new Date(createTime * 1000);
        msg.setCreateTime(date);

        return msg;
    }
}
