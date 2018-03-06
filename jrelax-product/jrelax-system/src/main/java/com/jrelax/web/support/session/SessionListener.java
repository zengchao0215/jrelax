package com.jrelax.web.support.session;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.support.ApplicationContextHelper;
import com.jrelax.core.web.model.User;
import com.jrelax.core.web.session.SessionAttributeManager;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.web.system.entity.Log;
import com.jrelax.web.system.service.LogService;
import com.jrelax.web.system.service.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session监听器
 *
 * @author ZENGCHAO
 */
@Service
public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent se) {
        //session创建
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        //session失效
        User user = WebApplicationCommon.getSessionAdminUser(se.getSession());
        if (ObjectKit.isNotNull(user)) {
            ApplicationCommon.getCacheOfLoginUser().remove(user.getUserName());
            ApplicationCommon.getCacheOfLoginSession().remove(user.getUserName());
            ApplicationCommon.getCacheOfSession().remove(se.getSession().getId());
            SessionAttributeManager.clear(se.getSession().getId());

            UserService userService = ApplicationContextHelper.getInstance().getBean(UserService.class);
            LogService logService = ApplicationContextHelper.getInstance().getBean(LogService.class);
            userService.executeBatch("update User set online=false where id='" + user.getId() + "'");

            logService.info("login-out", StringKit.format("[{0}]超时退出系统"), Log.DEFAULT_USER, new HandlerRequest(Log.DEFAULT_IP, "", ""));
        }

        //清空
        SessionAttributeManager.clear(se.getSession().getId());
    }

}
