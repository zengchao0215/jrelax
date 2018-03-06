package com.jrelax.web.system.service;

import com.jrelax.kit.ObjectKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.common.entity.notify.NotifyMessage;
import com.jrelax.web.common.entity.notify.message.NotifyLinkMessage;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.Notify;
import com.jrelax.web.system.entity.UserNotify;
import net.sf.json.JSONObject;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserNotifyService extends BaseService<UserNotify> {
    /**
     * 获取已读消息
     *
     * @return
     */
    public List<NotifyMessage> getReadNotifyMessage() {
        List<UserNotify> userNotifyList = this.list(Condition.NEW().eq("user", getCurrentUser()).eq("recread", true).desc("createTime"));

        return userNotitysToMessage(userNotifyList);
    }

    /**
     * 获取未读消息
     *
     * @return
     */
    public List<NotifyMessage> getUnReadNotifyMessage() {
        List<UserNotify> userNotifyList = this.list(Condition.NEW().eq("user", getCurrentUser()).eq("recread", false).desc("createTime"));

        return userNotitysToMessage(userNotifyList);
    }


    /**
     * 分页获取已读通知
     *
     * @param pageBean
     * @return
     */
    public List<NotifyMessage> getReadNotifyMessage(PageBean pageBean) {
        pageBean.addCriterion(Restrictions.eq("user", getCurrentUser()));
        pageBean.addCriterion(Restrictions.eq("recread", true));
        pageBean.addOrder(Order.desc("createTime"));
        List<UserNotify> userNotifyList = this.list(pageBean);

        return userNotitysToMessage(userNotifyList);
    }

    /**
     * 分页获取未读通知
     *
     * @param pageBean
     * @return
     */
    public List<NotifyMessage> getUnReadNotifyMessage(PageBean pageBean) {
        pageBean.addCriterion(Restrictions.eq("user", getCurrentUser()));
        pageBean.addCriterion(Restrictions.eq("recread", false));
        pageBean.addOrder(Order.desc("createTime"));
        List<UserNotify> userNotifyList = this.list(pageBean);

        return userNotitysToMessage(userNotifyList);
    }

    private List<NotifyMessage> userNotitysToMessage(List<UserNotify> userNotifyList) {
        List<NotifyMessage> list = new ArrayList<NotifyMessage>();
        for (UserNotify notify : userNotifyList) {
            NotifyMessage notifyMessage = userNotityToMessage(notify.getNotify());
            notifyMessage.setId(notify.getId());
            list.add(notifyMessage);
        }
        return list;
    }

    public NotifyMessage userNotityToMessage(Notify notify) {
        NotifyMessage notifyMessage = null;
        if (ObjectKit.isNotNull(notify.getContent())) {
            JSONObject json = JSONObject.fromObject(notify.getContent());
            if ("NotifyMessage".equals(json.getString("type"))) {
                notifyMessage = new NotifyMessage(json);
            } else if ("NotifyLinkMessage".equals(json.getString("type"))) {
                notifyMessage = new NotifyLinkMessage(json);
            }
        }
        return notifyMessage;
    }

}
