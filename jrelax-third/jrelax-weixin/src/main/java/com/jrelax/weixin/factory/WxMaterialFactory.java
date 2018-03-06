package com.jrelax.weixin.factory;

import com.jrelax.weixin.material.WxArticleMaterial;
import com.jrelax.weixin.material.WxMaterialType;
import com.jrelax.weixin.material.WxNewsMaterial;
import com.jrelax.weixin.material.WxNormalMaterial;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Date;

public class WxMaterialFactory {

    /**
     * 通过微信服务器相应的json数据来创建素材对象
     *
     * @param json
     * @return
     */
    public static WxNormalMaterial createFromJson(JSONObject json) {
        WxNormalMaterial material = new WxNormalMaterial();
        if (json.has("name")) {
            String name = json.getString("name");
            material.setName(name);

            //根据名称判断素材类型
            if (name.endsWith(".mp4")) {
                material.setType(WxMaterialType.VIDEO);
            } else if (name.endsWith(".amr") || name.endsWith(".mp3")) {
                material.setType(WxMaterialType.VOICE);
            } else {
                material.setType(WxMaterialType.IMAGE);
            }
        }
        if (json.has("media_id"))
            material.setId(json.getString("media_id"));
        else if (json.has("thumb_media_id")) {
            material.setId(json.getString("thumb_media_id"));
            material.setType(WxMaterialType.THUMB);
        }
        if (json.has("created_at"))
            material.setCreateTime(new Date(json.getLong("created_at") * 1000));
        else if (json.has("update_time"))
            material.setCreateTime(new Date(json.getLong("update_time") * 1000));
        else
            material.setCreateTime(new Date());
        if (json.has("url"))
            material.setUrl(json.getString("url"));
        return material;
    }

    /**
     * 创建图文消息
     *
     * @param json
     * @return
     */
    public static WxArticleMaterial createArticleFromJson(JSONObject json) {
        WxArticleMaterial article = new WxArticleMaterial();

        article.setTitle(json.getString("title"));
        article.setThumbMediaId(json.getString("thumb_media_id"));
        article.setShowCoverPic(json.getInt("show_cover_pic") == 1);
       //article.setShowCoverPic(Integer.parseInt(json.getString("need_open_comment ")) == 1);
       //article.setShowCoverPic(Integer.parseInt(json.getString("only_fans_can_comment ")) == 1);
        article.setAuthor(json.getString("author"));
        article.setDigest(json.getString("digest"));
        article.setContent(json.getString("content"));
        article.setSourceUrl(json.getString("content_source_url"));
        article.setUrl(json.getString("url"));

        return article;
    }

    /**
     * 创建图文素材
     *
     * @param json
     * @return
     */
    public static WxNewsMaterial createNewsFromJson(JSONObject json) {
        WxNewsMaterial material = new WxNewsMaterial();

        material.setId(json.getString("media_id"));
        JSONArray items = json.getJSONObject("content").getJSONArray("news_item");
        for (int i = 0; i < items.size(); i++) {
            JSONObject item = items.getJSONObject(i);

            material.addArticle(createArticleFromJson(item));
        }
        if(json.has("create_time")){
            material.setCreateTime(new Date(json.getLong("create_time") * 1000));
        }
        if(json.has("update_time")){
            material.setUpdateTime(new Date(json.getLong("update_time") * 1000));
        }

        //如果没有create_time字段，则使用update_time
        if(material.getCreateTime() == null){
            material.setCreateTime(material.getUpdateTime());
        }
        return material;
    }
}
