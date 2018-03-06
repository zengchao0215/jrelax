package com.jrelax.web.common.entity.notify;

import com.jrelax.kit.DateKit;
import com.jrelax.kit.StringKit;
import com.jrelax.web.common.entity.notify.action.NotifyLinkAction;
import com.jrelax.web.common.entity.notify.action.NotifyScriptAction;
import com.jrelax.web.system.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotifyMessage implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -9201813289329541578L;
    protected String id;
    private String businessType;//业务类型
    protected String businessId;//业务ID
    protected String logo = "ti-bell";// Logo图标
    protected String logoBackgroundClass = "bg-warning";//logo 背景样式
    protected String title;//标题
    protected String content;//内容
    protected String createTime;//创建时间
    protected String createUser;//创建人
    protected String readTime;// 阅读时间
    protected List<User> recipients = new ArrayList<User>();//接收人
    protected List<NotifyAction> actions = new ArrayList<NotifyAction>();// 动作列表

    private String html;
    private String timespan;

    public NotifyMessage() {

    }

    public NotifyMessage(JSONObject json) {
        this.logo = json.get("logo") + "";
        this.logoBackgroundClass = json.get("logoBackgroundClass") + "";
        this.title = json.get("title") + "";
        this.content = json.get("content") + "";
        this.createUser = json.get("createUser") + "";
        this.createTime = json.get("createTime") + "";
        this.businessId = json.get("businessId") + "";

        JSONArray actionArray = json.getJSONArray("actions");
        for (int i = 0; i < actionArray.size(); i++) {
            JSONObject action = actionArray.getJSONObject(i);
            if ("NotifyAction".equals(action.getString("type"))) {
                this.addAction(new NotifyAction(action));
            } else if ("NotifyLinkAction".equals(action.getString("type"))) {
                this.addAction(new NotifyLinkAction(action));
            } else if ("NotifyScriptAction".equals(action.getString("type"))) {
                this.addAction(new NotifyScriptAction(action));
            }
        }
        render();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoBackgroundClass() {
        return logoBackgroundClass;
    }

    public void setLogoBackgroundClass(String logoBackgroundClass) {
        this.logoBackgroundClass = logoBackgroundClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public List<User> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<User> recipients) {
        this.recipients = recipients;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTimespan() {
        return timespan;
    }

    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }

    /**
     * 增加接受者ID
     *
     * @param user 用户
     */
    public void addRecipient(User user) {
        this.recipients.add(user);
    }

    /**
     * 增加接受者ID
     *
     * @param userId 用户ID
     */
    public void addRecipient(String userId) {
        User user = new User();
        user.setId(userId);
        this.recipients.add(user);
    }

    public List<NotifyAction> getActions() {
        return actions;
    }

    public void setActions(List<NotifyAction> actions) {
        this.actions = actions;
    }

    /**
     * 增加动作
     *
     * @param action
     */
    public void addAction(NotifyAction action) {
        this.actions.add(action);
    }

    /**
     * 生成HTML
     *
     * @return
     */
    public String html() {
        StringBuilder html = new StringBuilder("<a href=\"javascript:;\">");
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

    /**
     * 计算时间间隔
     *
     * @return
     */
    protected String getTimeSpan() {
        String desc = "0分钟前";
        if (!StringKit.isEmpty(this.createTime)) {
            //计算时间间隔
            Date date = DateKit.parse(this.createTime, DateKit.YYYY_MM_DD_HH_MM_SS);//创建时间

            Long timespan = System.currentTimeMillis() - date.getTime();

            Long sec = timespan / 1000;
            if (sec < 60)//计算秒
                desc = sec.intValue() + "秒前";
            else {
                Long min = sec / 60;
                if (min < 60)
                    desc = min.intValue() + "分钟前";
                else {//计算小时
                    Long hour = min / 60;
                    if (hour < 24)
                        desc = hour.intValue() + "小时前";
                    else {
                        Long day = hour / 24;
                        desc = day.intValue() + "天前";
                    }
                }
            }
        }
        return desc;
    }

    protected void render() {
        this.html = html();
        this.timespan = getTimeSpan();
    }

    public JSONObject toJSON() {
        JSONObject jsonNotify = new JSONObject();

        jsonNotify.put("type", this.getClass().getSimpleName());
        jsonNotify.put("id", this.getId());
        jsonNotify.put("businessId", this.getBusinessId());
        jsonNotify.put("logo", this.getLogo());
        jsonNotify.put("logoBackgroundClass", this.getLogoBackgroundClass());
        jsonNotify.put("title", this.getTitle());
        jsonNotify.put("content", this.getContent());
        jsonNotify.put("createUser", this.getCreateUser());
        jsonNotify.put("createTime", this.getCreateTime());

        return jsonNotify;
    }
}
