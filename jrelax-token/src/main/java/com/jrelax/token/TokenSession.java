package com.jrelax.token;

import javax.servlet.ServletContext;
import java.util.Enumeration;

/**
 * Token会话
 * Created by zengchao on 2017-03-22.
 */
public interface TokenSession {
    long getCreationTime();

    String getId();

    long getLastAccessedTime();

    ServletContext getServletContext();

    void setMaxInactiveInterval(int interval);

    int getMaxInactiveInterval();

    Object getAttribute(String attributeName);

    Enumeration<String> getAttributeNames();

    void setAttribute(String attributeName, Object attributeValue);

    void removeAttribute(String attributeName);

    void invalidate();

    boolean isNew();

    void setTokenAuth(Object value);

    Object getTokenAuth();
}
