package com.jrelax.weixin.handle;

import com.jrelax.weixin.msg.WxMsg;
import com.jrelax.weixin.msg.req.WxReqMsg;

/**
 * 消息处理程序
 */
public interface WxMessageHandle {
    /**
     * 消息处理
     * @param reqMsg
     * @return
     */
    WxMsg handle(WxReqMsg reqMsg);

    /**
     * 判断是否支持处理消息
     * @param reqMsg
     * @return 是否可以处理
     */
    boolean supported(WxReqMsg reqMsg);
}
