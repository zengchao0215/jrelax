package com.jrelax.weixin.api;

import com.jrelax.weixin.WxException;
import com.jrelax.weixin.WxKit;
import com.jrelax.weixin.WxToken;
import com.jrelax.weixin.factory.WxMaterialFactory;
import com.jrelax.weixin.material.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 素材接口
 */
public class WxMaterialApi {
    public static final String URL_TEMP_UPLOAD = "https://api.weixin.qq.com/cgi-bin/media/upload";
    public static final String URL_TEMP_GET = "https://api.weixin.qq.com/cgi-bin/media/get";
    public static final String URL_UPLOAD = "https://api.weixin.qq.com/cgi-bin/material/add_material";
    public static final String URL_COUNT = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
    public static final String URL_GET = "https://api.weixin.qq.com/cgi-bin/material/get_material";
    public static final String URL_BATCH_GET = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
    public static final String URL_ADD_NEWS = "https://api.weixin.qq.com/cgi-bin/material/add_news";
    public static final String URL_UPLOAD_IMAGE = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
    public static final String URL_DELETE = "https://api.weixin.qq.com/cgi-bin/material/del_material";
    public static final String URL_UPDATE = "https://api.weixin.qq.com/cgi-bin/material/update_news";

    /**
     * 上传临时素材
     *
     * @param wxToken
     * @param material
     * @return
     */
    public static WxNormalMaterial uploadTempMaterial(WxToken wxToken, WxNormalMaterial material) {
        return upload(URL_TEMP_UPLOAD, wxToken, material);
    }

    public static void getTempMaterial(WxToken wxToken, String mediaId, String savePath) {
        JSONObject param = new JSONObject();
        param.element("access_token", wxToken.getAccessToken());
        param.element("media_id", mediaId);

        WxKit.download(wxToken, URL_TEMP_GET, param.toString(), savePath);
    }

    /**
     * 上传永久素材
     *
     * @param wxToken
     * @param material
     * @return
     */
    public static WxNormalMaterial uploadMaterial(WxToken wxToken, WxNormalMaterial material) {
        if (material.getType() == WxMaterialType.VIDEO)
            throw new WxException(-1, "视频上传请使用uploadVideoMaterial");
        return upload(URL_UPLOAD, wxToken, material);
    }

    /**
     * 上传视频素材
     *
     * @param wxToken
     * @param material
     * @return
     */
    public static WxVideoMaterial uploadVideoMaterial(WxToken wxToken, WxVideoMaterial material) {
        String url = URL_UPLOAD + "?access_token=" + wxToken.getAccessToken() + "&type=" + material.getType().toString();
        JSONObject param = new JSONObject();
        param.element("title", material.getTitle());
        param.element("introduction", material.getDescription());

        JSONObject json = WxKit.post(url, material.getFile(), "description", param.toString());

        material.setId(json.getString("media_id"));
        material.setUrl(json.getString("url"));
        material.setCreateTime(new Date());
        return material;
    }

    /**
     * 上传图片，用于图文消息中内容图片
     *
     * @param wxToken
     * @param file
     * @return
     */
    public static String uploadImage(WxToken wxToken, File file) {
        String url = URL_UPLOAD_IMAGE + "?access_token=" + wxToken.getAccessToken();
        JSONObject json = WxKit.post(url, file);

        return json.getString("url");
    }

    /**
     * 上传图文素材
     *
     * @param wxToken
     * @param material
     * @return
     */
    public static WxNewsMaterial uploadNewsMaterial(WxToken wxToken, WxNewsMaterial material) {
        JSONObject data = new JSONObject();
        JSONArray articles = new JSONArray();
        for (WxArticleMaterial article : material.getArticles()) {
            articles.add(article.toString());
        }
        data.element("articles", articles);
        JSONObject json = WxKit.post(wxToken, URL_ADD_NEWS, data.toString());
        material.setId(json.getString("media_id"));
        material.setCreateTime(new Date());
        return material;
    }

    private static WxNormalMaterial upload(String strUrl, WxToken wxToken, WxNormalMaterial material) {
        String url = strUrl + "?access_token=" + wxToken.getAccessToken() + "&type=" + material.getType().toString();
        JSONObject json = WxKit.post(url, material.getFile());
        return WxMaterialFactory.createFromJson(json);
    }

    /**
     * 获取永久素材数量
     *
     * @param wxToken
     * @return
     */
    public static JSONObject getMaterialCount(WxToken wxToken) {
        return WxKit.request(wxToken, URL_COUNT, null);
    }

    /**
     * 获取永久素材列表
     *
     * @param wxToken
     * @param materialType 素材类型
     * @param start        起始，0开始
     * @param count        获取数量
     * @return
     */
    public static List<WxMaterial> listMaterial(WxToken wxToken, WxMaterialType materialType, int start, int count) {
        List<WxMaterial> list = new ArrayList<>();
        JSONObject param = new JSONObject();
        param.element("type", materialType.toString());
        param.element("offset", start);
        param.element("count", count);

        JSONObject json = WxKit.post(wxToken, URL_BATCH_GET, param.toString());
        JSONArray items = json.getJSONArray("item");
        for (int i = 0; i < items.size(); i++) {
            JSONObject item = items.getJSONObject(i);
            if (materialType == WxMaterialType.NEWS) {//图文消息
                list.add(WxMaterialFactory.createNewsFromJson(item));
            } else {//素材
                list.add(WxMaterialFactory.createFromJson(item));
            }
        }
        return list;
    }

    /**
     * 下载素材
     *
     * @param wxToken
     * @param mediaId
     * @param savePath
     */
    public static void getMaterial(WxToken wxToken, String mediaId, String savePath) {
        List<WxMaterial> list = new ArrayList<>();
        JSONObject param = new JSONObject();
        param.element("media_id", mediaId);

        WxKit.download(wxToken, URL_GET, param.toString(), savePath);
    }

    /**
     * 获取视频素材
     *
     * @param wxToken
     * @param mediaId
     */
    public static WxVideoMaterial getVideoMaterial(WxToken wxToken, String mediaId) {
        JSONObject param = new JSONObject();
        param.element("media_id", mediaId);

        JSONObject json = WxKit.post(wxToken, URL_GET, param.toString());

        if (json.has("down_url")) {
            WxVideoMaterial videoMaterial = new WxVideoMaterial();

            videoMaterial.setTitle(json.getString("title"));
            videoMaterial.setDescription(json.getString("description"));
            videoMaterial.setUrl(json.getString("down_url"));
            videoMaterial.setId(mediaId);

            return videoMaterial;
        }
        return null;
    }

    /**
     * 获取图文素材
     *
     * @param wxToken
     * @param mediaId
     * @return
     */
    public static WxNewsMaterial getNewsMaterial(WxToken wxToken, String mediaId) {
        JSONObject param = new JSONObject();
        param.element("media_id", mediaId);

        JSONObject json = WxKit.post(wxToken, URL_GET, param.toString());

        JSONObject data = new JSONObject();
        data.element("media_id", mediaId);
        data.element("content", json);
        data.element("create_time", json.get("create_time"));
        data.element("update_time", json.get("update_time"));
        return WxMaterialFactory.createNewsFromJson(data);
    }

    /**
     * 删除素材
     *
     * @param wxToken
     * @param mediaId
     * @return
     */
    public static boolean deleteMaterial(WxToken wxToken, String mediaId) {
        JSONObject param = new JSONObject();
        param.element("media_id", mediaId);

        JSONObject json = WxKit.post(wxToken, URL_DELETE, param.toString());
        return WxKit.isSuccess(json);
    }

    /**
     * 修改图文素材
     * @param wxToken
     * @param mediaId
     * @param index 文章顺序，0开始
     * @param article 文章新内容
     * @return
     */
    public static boolean updateNewsMaterial(WxToken wxToken, String mediaId, int index, WxArticleMaterial article){
        JSONObject param = new JSONObject();
        param.element("media_id", mediaId);
        param.element("index", index);
        param.element("articles", article.toJson());

        JSONObject json = WxKit.post(wxToken, URL_UPDATE, param.toString());
        return WxKit.isSuccess(json);
    }
}
