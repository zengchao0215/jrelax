package com.jrelax.weixin.material;

import java.util.Date;

/**
 * 素材
 */
public class WxMaterial {
    private String id;//素材id，上传到微信服务器后产生
    private WxMaterialType type;//文件类型
    private Date createTime;//创建时间

    public WxMaterialType getType() {
        return type;
    }

    public void setType(WxMaterialType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
