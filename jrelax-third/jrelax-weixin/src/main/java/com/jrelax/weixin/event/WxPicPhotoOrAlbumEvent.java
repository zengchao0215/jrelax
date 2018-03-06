package com.jrelax.weixin.event;

/**
 * 微信相册选择
 */
public class WxPicPhotoOrAlbumEvent extends WxPicEvent {
    {
        super.setEventType(WxEventType.PIC_PHOTO_OR_ALBUM);
    }
}
