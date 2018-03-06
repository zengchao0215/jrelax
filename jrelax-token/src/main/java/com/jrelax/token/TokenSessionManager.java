package com.jrelax.token;

/**
 * TokenSession管理器
 * Created by zengchao on 2017-03-22.
 */
public interface TokenSessionManager {
    /**
     * 移除Session
     *
     * @param session
     */
    void remove(Session session);

    /**
     * 创建一个空Session
     *
     * @return
     */
    Session createSession(String id);

    /**
     * 根据id查找session
     *
     * @param id
     * @return
     */
    Session findSession(String id);

    /**
     * 超时时间，分钟
     *
     * @return
     */
    int getTimeout();

    /**
     * 设置最大轰动时间
     *
     * @param minutes 分钟
     */
    void setTimeout(int minutes);

    /**
     * 获取活动的Session数量
     *
     * @return
     */
    int getActiveSessions();

    /**
     * 增加新session
     *
     * @param session
     */
    void add(Session session);

    void startInternal();
    void stopInternal();
}
