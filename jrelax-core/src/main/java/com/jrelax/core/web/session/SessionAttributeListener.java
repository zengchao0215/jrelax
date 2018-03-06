package com.jrelax.core.web.session;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.model.User;
import com.jrelax.kit.ObjectKit;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * session属性编辑器
 *
 * @author ZENGCHAO
 */
public class SessionAttributeListener implements HttpSessionAttributeListener {

    public void attributeAdded(HttpSessionBindingEvent se) {
        if (ApplicationCommon.SESSION_ADMIN.equals(se.getName())) {
            if (ObjectKit.isNotNull(se.getValue())) {
                User user = (User) se.getValue();
                ApplicationCommon.getCacheOfLoginUser().add(user.getUserName());
                ApplicationCommon.getCacheOfSession().put(se.getSession().getId(), se.getSession());
            }

        }
    }

    public void attributeRemoved(HttpSessionBindingEvent se) {
        if (ApplicationCommon.SESSION_ADMIN.equals(se.getName())) {
            if (ObjectKit.isNotNull(se.getValue())) {
                User user = (User) se.getValue();
                ApplicationCommon.getCacheOfLoginUser().remove(user.getUserName());
                ApplicationCommon.getCacheOfSession().remove(se.getSession().getId());
            }
            SessionAttributeManager.clear(se.getSession().getId());
        }
    }

    public void attributeReplaced(HttpSessionBindingEvent se) {
        if (ApplicationCommon.SESSION_ADMIN.equals(se.getName())) {
            if (ObjectKit.isNotNull(se.getValue())) {
                User user = (User) se.getValue();
                ApplicationCommon.getCacheOfLoginUser().remove(user.getUserName());
                ApplicationCommon.getCacheOfLoginUser().add(user.getUserName());
                ApplicationCommon.getCacheOfSession().put(se.getSession().getId(), se.getSession());
                ApplicationCommon.getCacheOfSession().remove(se.getSession().getId());
            }
            SessionAttributeManager.clear(se.getSession().getId());
        }
    }

}
