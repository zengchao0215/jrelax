package com.jrelax.token;

/**
 * Session会话
 * Created by zengchao on 2017-03-22.
 */
public interface Session {
    /**
     * Session创建时间
     * @return
     */
    public long getCreationTime();

    /**
     * 设置Session创建时间
     * @param time
     */
    public void setCreationTime(long time);

    /**
     * 获取SessionId
     * @return
     */
    public String getId();

    /**
     * 设置SessionId
     * @param id
     */
    public void setId(String id);

    /**
     * 获取本次访问时间
     * @return
     */
    public long getThisAccessedTime();

    /**
     * 获取最后访问时间
     * @return
     */
    public long getLastAccessedTime();

    /**
     * 获取空闲时间
     * @return
     */
    public long getIdleTime();

    /**
     * 获取管理器
     * @return
     */
    public TokenSessionManager getManager();

    /**
     * 设置管理器
     * @param manager
     */
    public void setManager(TokenSessionManager manager);

    /**
     * 获取最大活动时间配置
     * @return
     */
    public int getMaxInactiveInterval();

    /**
     * 设置最大活动时间
     * @param interval
     */
    public void setMaxInactiveInterval(int interval);

    /**
     * 设置是否是新Session
     * @param isNew
     */
    public void setNew(boolean isNew);

    /**
     * 获取TokenSession
     * @return
     */
    public TokenSession getSession();

    /**
     * 标记访问
     */
    public void access();

    /**
     * 标记结束访问
     */
    public void endAccess();

    /**
     * 标记过期
     */
    public void expire();

    /**
     * 回收
     */
    public void recycle();
}
