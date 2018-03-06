package com.jrelax.weixin.material;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 图文素材
 */
public class WxNewsMaterial extends WxMaterial {
    {
        super.setType(WxMaterialType.NEWS);
    }

    private List<WxArticleMaterial> articles = new ArrayList<>();
    private Date updateTime;//修改时间

    public List<WxArticleMaterial> getArticles() {
        return articles;
    }

    public void setArticles(List<WxArticleMaterial> articles) {
        this.articles = articles;
    }

    public WxNewsMaterial addArticle(WxArticleMaterial article){
        this.articles.add(article);

        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setType(WxMaterialType type) {
        super.setType(WxMaterialType.NEWS);
    }
}
