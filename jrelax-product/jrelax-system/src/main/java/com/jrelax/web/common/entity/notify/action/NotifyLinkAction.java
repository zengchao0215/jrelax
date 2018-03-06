package com.jrelax.web.common.entity.notify.action;

import com.jrelax.web.common.entity.notify.ModalSize;
import com.jrelax.web.common.entity.notify.NotifyAction;
import com.jrelax.web.common.entity.notify.ShowMode;
import net.sf.json.JSONObject;

public class NotifyLinkAction extends NotifyAction {
    /**
     *
     */
    private static final long serialVersionUID = -3130677042933534027L;

    public NotifyLinkAction() {

    }

    public NotifyLinkAction(JSONObject json) {
        super(json);
        this.url = json.get("url") + "";
        this.showMode = ShowMode.valueOf(json.get("showMode") + "");
        System.out.println(json);
        this.modalSize = ModalSize.valueOf(json.get("modalSize") + "");
    }

    private String url;
    private ShowMode showMode = ShowMode.Page;// page modal
    private ModalSize modalSize = ModalSize.Large;

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
        String html = "";
        if (this.showMode == ShowMode.Page) {
            html += "<a class='notify-link' href=\"" + this.url + "\">";
        } else if (this.showMode == ShowMode.NewPage) {
            html += "<a href=\"" + this.url + "\" target=\"_blank\">";
        } else if (this.showMode == ShowMode.Modal) {
            html += "<a href=\"javascript:ns.view.showModal('" + this.url + "',{size:'" + this.modalSize.toString() + "'});\">";
        } else {
            html += "<a href=\"javascript:;\">";
        }
        html += "<span class=\"label " + this.backgroudClass + " pull-right mr5\"> " + this.name + " </span>";
        html += "</a>";

        return html;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonAction = super.toJSON();

        jsonAction.put("url", this.getUrl());
        jsonAction.put("showMode", this.getShowMode());
        jsonAction.put("modalSize", this.getModalSize());

        return jsonAction;
    }
}
