package com.jrelax.web.common.entity.notify.message;

import com.jrelax.web.common.entity.notify.ModalSize;
import com.jrelax.web.common.entity.notify.NotifyAction;
import com.jrelax.web.common.entity.notify.NotifyMessage;
import com.jrelax.web.common.entity.notify.ShowMode;
import net.sf.json.JSONObject;

public class NotifyLinkMessage extends NotifyMessage {

    /**
     *
     */
    private static final long serialVersionUID = -4216461630559090103L;

    public NotifyLinkMessage() {

    }

    public NotifyLinkMessage(JSONObject json) {
        super(json);
        this.url = json.get("url") + "";
        this.showMode = ShowMode.valueOf(json.getString("showMode"));
        this.modalSize = ModalSize.valueOf(json.getString("modalSize"));

        render();
    }

    private String url;
    private ShowMode showMode = ShowMode.Page;
    private ModalSize modalSize = ModalSize.Normal;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ShowMode getShowMode() {
        return showMode;
    }

    public void setShowMode(ShowMode showMode) {
        this.showMode = showMode;
    }

    public ModalSize getModalSize() {
        return modalSize;
    }

    public void setModalSize(ModalSize modalSize) {
        this.modalSize = modalSize;
    }

    @Override
    public String html() {
        StringBuilder html = new StringBuilder();
        if (this.showMode == ShowMode.Page) {
            html.append("<a class='notify-link' href=\"").append(this.url).append("\">");
        } else if (this.showMode == ShowMode.NewPage) {
            html.append("<a href=\"").append(this.url).append("\" target=\"_blank\">");
        } else if (this.showMode == ShowMode.Modal) {
            html.append("<a href=\"javascript:ns.view.showModal('").append(this.url).append("', {size: '").append(this.modalSize.toString()).append("'});\">");
        } else {
            html.append("<a href=\"javascript:;\">");
        }
        html.append("<div class=\"pull-left mt5 mr15\">");
        html.append("<div class=\"circle-icon ").append(this.logoBackgroundClass).append("\">");
        html.append("<i class=\"").append(this.logo).append("\"></i>");//logo
        html.append("</div>");
        html.append("</div>");
        html.append("<div class=\"m-body\">");
        html.append("<div class=\"\">");
        html.append("<small><b>").append(this.title).append("</b></small>");//标题
        for (NotifyAction action : this.actions)
            html.append(action.html());
        html.append("</div>");
        html.append("<div>");//内容
        html.append("<span>").append(this.content).append("</span>");
        html.append("</div>");
        html.append("<span class=\"time small\">").append(getTimeSpan()).append("</span>");
        html.append("</div>");
        html.append(" </a>");

        return html.toString();
    }

    @Override
    protected void render() {
        super.render();
        super.setHtml(html());
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.element("url", this.getUrl());
        json.element("showMode", this.getShowMode());
        json.element("modalSize", this.getModalSize());
        return json;
    }
}
