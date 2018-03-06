package com.jrelax.weixin.material;

/**
 * 视频素材
 */
public class WxVideoMaterial extends WxNormalMaterial {
    {
        super.setType(WxMaterialType.VIDEO);
    }

    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
