package com.jrelax.weixin.material;

/**
 * 素材类型
 */
public enum WxMaterialType {
    IMAGE("image"),//图片
    VOICE("voice"),//语音
    VIDEO("video"),//视频
    THUMB("thumb"),//封面
    NEWS("news");

    private String name;

    WxMaterialType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
