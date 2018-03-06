package com.jrelax.weixin.material;

import net.sf.json.JSONObject;

/**
 * 图文
 */
public class WxArticleMaterial {
    private String title;//标题
    private String thumbMediaId;//封面
    private String author;//作者
    private String digest;//摘要
    private boolean showCoverPic;//是否显示封面
    private String content;//内容
    private String sourceUrl;//原文地址
    private String url;//文章地址
    private boolean needOpenComment = false;//是否打开评论
    private boolean onlyFansCanComment = true;//是否必须是粉丝才可评论

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public boolean isShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(boolean showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.element("title", this.title);
        json.element("thumb_media_id", this.thumbMediaId);
        json.element("author", this.author);
        json.element("digest", this.digest);
        json.element("show_cover_pic", this.showCoverPic ? 1 : 0);
        json.element("need_open_comment", this.needOpenComment ? 1 : 0);
        json.element("only_fans_can_comment", this.onlyFansCanComment ? 1 : 0);
        json.element("content", this.content);
        json.element("content_source_url", this.sourceUrl);
        json.element("url", this.url);
        return json.toString();
    }

    @Override
    public String toString() {
        return this.toJson();
    }
}
