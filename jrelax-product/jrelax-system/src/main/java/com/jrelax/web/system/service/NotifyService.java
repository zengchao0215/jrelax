package com.jrelax.web.system.service;

import com.jrelax.kit.ObjectKit;
import com.jrelax.web.common.entity.notify.NotifyAction;
import com.jrelax.web.common.entity.notify.NotifyMessage;
import com.jrelax.web.common.entity.notify.action.NotifyLinkAction;
import com.jrelax.web.common.entity.notify.action.NotifyScriptAction;
import com.jrelax.web.common.entity.notify.message.NotifyLinkMessage;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.Notify;
import com.jrelax.web.system.entity.User;
import com.jrelax.web.system.entity.UserNotify;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
public class NotifyService extends BaseService<Notify> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserNotifyService userNotifyService;

    /**
     * 保存通知到数据库
     * 转换为JSON后存储
     *
     * @param notifyMessage
     */
    public void save(NotifyMessage notifyMessage) {
        //校验
        if (notifyMessage.getRecipients().size() == 0) {
            logger.debug("通知没有接收者");
            return;
        }
        //设置默认值
        if (ObjectKit.isNull(notifyMessage.getCreateTime()))
            notifyMessage.setCreateTime(getCurrentTime());
        if (ObjectKit.isNull(notifyMessage.getCreateUser()))
            notifyMessage.setCreateUser(getCurrentUser().getRealName());

        Notify notify = new Notify();
        notify.setType(notifyMessage.getBusinessType());
        notify.setCreateUser(notifyMessage.getCreateUser());
        notify.setCreateTime(notifyMessage.getCreateTime());
        JSONObject jsonNotify = notifyMessage.toJSON();

        //操作动作
        JSONArray actionArray = new JSONArray();
        for (NotifyAction action : notifyMessage.getActions()) {
            actionArray.add(action.toJSON());
        }
        jsonNotify.element("actions", actionArray);

        notify.setContent(jsonNotify.toString());

        //保存通知到数据库
        this.save(notify);

        //向消息接受者发送通知
        if (notifyMessage.getRecipients().size() > 0) {
            for (User user : notifyMessage.getRecipients()) {
                UserNotify userNotify = new UserNotify();
                userNotify.setNotify(notify);
                userNotify.setUser(user);
                userNotify.setRecread(false);
                userNotify.setCreateTime(getCurrentTime());
                userNotify.setBusinessId(notifyMessage.getBusinessId());

                userNotifyService.save(userNotify);
            }
        }
    }

    public NotifyMessage getNotifyById(Serializable id) {
        NotifyMessage notifyMessage = null;
        Notify notify = this.getById(id);
        if (ObjectKit.isNotNull(notify)) {
            notifyMessage = userNotifyService.userNotityToMessage(notify);
        }
        return notifyMessage;
    }

    /**
     * 通过业务字段，将通知设为已读！
     *
     * @param businessId
     */
    @Transactional
    public void readNotifyMessage(String businessId) {
        this.executeBatch("update UserNotify set recread=true where businessId='" + businessId + "' and user.id='" + getCurrentUser().getId() + "'");
    }
}
