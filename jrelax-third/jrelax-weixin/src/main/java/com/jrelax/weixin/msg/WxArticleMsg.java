package com.jrelax.weixin.msg;

import com.jrelax.weixin.media.WxArticle;

import java.util.ArrayList;
import java.util.List;

/**
 * 图文消息
 */
public class WxArticleMsg extends WxMsg {
    {
        super.setMsgType(WxMsgType.NEWS);
    }
    private List<WxArticle> articles = new ArrayList<>();

    public List<WxArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<WxArticle> articles) {
        this.articles = articles;
    }

    public WxArticleMsg addArticle(WxArticle article) {
        this.articles.add(article);
        return this;
    }

    @Override
    public String toXml() {
        StringBuilder xml = new StringBuilder(super.toXml());
        xml.append("<Music>").append(System.lineSeparator());
        for (WxArticle article : articles) {
            xml.append("<item>").append(System.lineSeparator());
            xml.append("<Title><![CDATA[").append(article.getTitle()).append("]]></Title>").append(System.lineSeparator());
            xml.append("<Description><![CDATA[").append(article.getDescription()).append("]]></Description>").append(System.lineSeparator());
            xml.append("<PicUrl><![CDATA[").append(article.getPicUrl()).append("]]></PicUrl>").append(System.lineSeparator());
            xml.append("<Url><![CDATA[").append(article.getUrl()).append("]]></Url>").append(System.lineSeparator());
            xml.append("</item>").append(System.lineSeparator());
        }
        xml.append("</Music>").append(System.lineSeparator());

        return super.getXmlStart() + System.lineSeparator() + xml.toString() + super.getXmlEnd();
    }
}
