package com.jrelax.token;

import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 标准TokenSession
 * Created by zengchao on 2017-03-22.
 */
public class StandardTokenSession implements TokenSession, Session, Serializable {
    private long creationTime = 0L;
    private long thisAccessedTime = 0L;
    private long lastAccessedTime = 0L;
    private int maxInactiveInterval = -1;
    private boolean isNew = false;
    private String id = "";
    private TokenSession facade = null;
    private TokenSessionManager manager = null;
    private ConcurrentMap<String, Object> attributes = new ConcurrentHashMap<String, Object>();
    private transient volatile boolean expiring = false;

    public StandardTokenSession(TokenSessionManager manager) {
        this.manager = manager;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long time) {
        this.creationTime = time;
        this.lastAccessedTime = time;
        this.thisAccessedTime = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getThisAccessedTime() {
        return thisAccessedTime;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public long getIdleTime() {
        return System.currentTimeMillis() - thisAccessedTime;
    }

    public TokenSessionManager getManager() {
        return manager;
    }

    public void setManager(TokenSessionManager manager) {
        this.manager = manager;
    }

    public ServletContext getServletContext() {
        return null;
    }

    public void setMaxInactiveInterval(int i) {
        maxInactiveInterval = i;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public TokenSession getSession() {
        if (facade == null) {
            facade = new StandardTokenSessionFacade(this);
        }
        return facade;
    }

    public void access() {
        thisAccessedTime = System.currentTimeMillis();
    }

    public void endAccess() {
        isNew = false;
        this.thisAccessedTime = System.currentTimeMillis();
        this.lastAccessedTime = this.thisAccessedTime;
    }

    public void expire() {
        expiring = true;
        if(manager == null) return;
        manager.remove(this);
        expiring = false;
    }

    /**
     * 回收
     */
    public void recycle() {
        attributes.clear();
        creationTime = 0L;
        expiring = false;
        id = null;
        lastAccessedTime = 0L;
        maxInactiveInterval = -1;
        isNew = false;
        manager = null;
    }

    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public Object getAttribute(String name) {
        if (name == null) return null;
        return attributes.get(name);
    }

    public Enumeration<String> getAttributeNames() {
        Set<String> names = new HashSet<String>();
        names.addAll(attributes.keySet());
        return Collections.enumeration(names);
    }

    public void setAttribute(String name, Object value) {
        if (value == null)
            removeAttribute(name);
        if (name != null)
            attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        if (name != null)
            attributes.remove(name);
    }

    public void invalidate() {
        expire();
    }

    public boolean isNew() {
        return isNew;
    }

    @Override
    public void setTokenAuth(Object value) {
        setAttribute(TokenSessionConfig.TOKEN_USER, value);
    }

    @Override
    public Object getTokenAuth() {
        return getAttribute(TokenSessionConfig.TOKEN_USER);
    }
}
