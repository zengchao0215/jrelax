package com.jrelax.weixin.factory;

import com.jrelax.weixin.event.*;
import net.sf.json.JSONObject;

import java.util.Date;

public class WxEventFactory {
    public static WxEvent createFromJSON(JSONObject json) {
        WxEvent event = null;
        String type = json.getString("Event");
        WxEventType eventType = WxEventType.valueOf2(type);
        switch (eventType){
            case CLICK:
                WxClickEvent clickEvent = new WxClickEvent();
                clickEvent.setEventKey(json.getString("EventKey"));

                event = clickEvent;
                break;
            case UNSUBSCRIBE:
                WxUnSubscribeEvent unSubscribeEvent = new WxUnSubscribeEvent();
                unSubscribeEvent.setEventKey(json.getString("EventKey"));

                event = unSubscribeEvent;
                break;
            case SUBSCRIBE:
                if(json.has("Ticket")){//区分是不是扫码事件
                    WxScanEvent scanEvent = new WxScanEvent();
                    scanEvent.setSubscribe(false);
                    scanEvent.setEventKey(json.getString("EventKey"));
                    scanEvent.setTicket(json.getString("Ticket"));

                    event = scanEvent;
                }else {
                    WxSubscribeEvent subscribeEvent = new WxSubscribeEvent();
                    subscribeEvent.setEventKey(json.getString("EventKey"));

                    event = subscribeEvent;
                }
                break;
            case SCAN:
                WxScanEvent scanEvent = new WxScanEvent();
                scanEvent.setEventKey(json.getString("EventKey"));
                scanEvent.setTicket(json.getString("Ticket"));

                event = scanEvent;
                break;
            case SCAN_CODE_PUSH:
                WxScanCodePushEvent scanCodePushEvent = new WxScanCodePushEvent();
                scanCodePushEvent.setEventKey(json.getString("EventKey"));
                scanCodePushEvent.setScanType(json.getString("ScanType"));
                scanCodePushEvent.setScanResult(json.getString("ScanResult"));

                event = scanCodePushEvent;
                break;
            case SCAN_CODE_WAITMSG:
                WxScanCodeWaitMsgEvent scanCodeWaitMsgEvent = new WxScanCodeWaitMsgEvent();
                scanCodeWaitMsgEvent.setEventKey(json.getString("EventKey"));
                scanCodeWaitMsgEvent.setScanType(json.getString("ScanType"));
                scanCodeWaitMsgEvent.setScanResult(json.getString("ScanResult"));

                event = scanCodeWaitMsgEvent;
                break;
            case PIC_PHOTO_OR_ALBUM:
                WxPicPhotoOrAlbumEvent picPhotoOrAlbumEvent = new WxPicPhotoOrAlbumEvent();
                picPhotoOrAlbumEvent.setEventKey(json.getString("EventKey"));
                picPhotoOrAlbumEvent.setCount(Integer.parseInt(json.getString("Count")));
                picPhotoOrAlbumEvent.setPicList(json.getString("PicList"));

                event = picPhotoOrAlbumEvent;
                break;
            case PIC_SYS_PHOTO:
                WxPicSysPhotoEvent picSysPhotoEvent = new WxPicSysPhotoEvent();
                picSysPhotoEvent.setEventKey(json.getString("EventKey"));
                picSysPhotoEvent.setCount(Integer.parseInt(json.getString("Count")));
                picSysPhotoEvent.setPicList(json.getString("PicList"));

                event = picSysPhotoEvent;
                break;
            case PIC_WEIXIN:
                WxPicWeiXinEvent picWeiXinEvent = new WxPicWeiXinEvent();
                picWeiXinEvent.setEventKey(json.getString("EventKey"));
                picWeiXinEvent.setCount(Integer.parseInt(json.getString("Count")));
                picWeiXinEvent.setPicList(json.getString("PicList"));

                event = picWeiXinEvent;
                break;
            case LOCATION:
                WxLocationEvent locationEvent = new WxLocationEvent();
                locationEvent.setLat(Double.parseDouble(json.getString("Latitude")));
                locationEvent.setLon(Double.parseDouble(json.getString("Longitude")));
                locationEvent.setPrecision(Double.parseDouble(json.getString("Precision")));

                event = locationEvent;
                break;
            case LOCATION_SELECT:
                WxLocationSelectEvent locationSelectEvent = new WxLocationSelectEvent();
                locationSelectEvent.setEventKey(json.getString("EventKey"));
                locationSelectEvent.setLon(Double.parseDouble(json.getString("Location_X")));
                locationSelectEvent.setLat(Double.parseDouble(json.getString("Location_Y")));
                locationSelectEvent.setScale(Double.parseDouble(json.getString("Scale")));
                locationSelectEvent.setLabel(json.getString("Label"));
                locationSelectEvent.setPoiname(json.getString("Poiname"));

                event = locationSelectEvent;
                break;
            case VIEW:
                WxViewEvent viewEvent = new WxViewEvent();
                viewEvent.setEventKey(json.getString("EventKey"));
                viewEvent.setMenuId(json.getString("MenuId"));

                event = viewEvent;
                break;
        }
        event.setToUserName(json.getString("ToUserName"));
        event.setFromUserName(json.getString("FromUserName"));
        long createTime = Long.parseLong(json.getString("CreateTime"));
        Date date = new Date(createTime * 1000);
        event.setCreateTime(date);


        return event;
    }
}
