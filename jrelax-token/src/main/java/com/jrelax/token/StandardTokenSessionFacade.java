package com.jrelax.token;

import javax.servlet.ServletContext;
import java.util.Enumeration;

/**
 * TokenSession包装
 * Created by zengchao on 2017-03-22.
 */
public class StandardTokenSessionFacade implements TokenSession {
    private TokenSession session;

    public StandardTokenSessionFacade(TokenSession session) {
        this.session = session;
    }

    public long getCreationTime() {
        return session.getCreationTime();
    }

    public String getId() {
        return session.getId();
    }

    public long getLastAccessedTime() {
        return session.getLastAccessedTime();
    }

    public ServletContext getServletContext() {
        return session.getServletContext();
    }

    public void setMaxInactiveInterval(int i) {
        session.setMaxInactiveInterval(i);
    }

    public int getMaxInactiveInterval() {
        return session.getMaxInactiveInterval();
    }

    public Object getAttribute(String s) {
        return session.getAttribute(s);
    }

    public Enumeration<String> getAttributeNames() {
        return session.getAttributeNames();
    }

    public void setAttribute(String s, Object o) {
        session.setAttribute(s, o);
    }

    public void removeAttribute(String s) {
        session.removeAttribute(s);
    }

    public void invalidate() {
        session.invalidate();
    }

    public boolean isNew() {
        return session.isNew();
    }

    @Override
    public void setTokenAuth(Object value) {
        session.setTokenAuth(value);
    }

    @Override
    public Object getTokenAuth() {
        return session.getTokenAuth();
    }
}
