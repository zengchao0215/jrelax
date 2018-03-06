package com.jrelax.token;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TokenSession管理器
 * Created by zengchao on 2017-03-22.
 */
public class StandardTokenSessionManager implements TokenSessionManager {
    private Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();
    private TokenSessionIdGenerator sessionIdGenerator = new StandardTokenSessionIdGenerator();
    private volatile int activeSessions = 0;
    private int timeout = TokenSessionConfig.TIMEOUT;
    private boolean stopBreak = false;

    public void remove(Session session) {
        if (session != null) {
            this.sessions.remove(session.getId());
        }
    }

    private Session createEmptySession() {
        return new StandardTokenSession(this);
    }

    public Session createSession(String id) {
        activeSessions++;
        Session session = createEmptySession();
        session.setNew(true);
        session.setCreationTime(System.currentTimeMillis());
        if (id == null)
            id = sessionIdGenerator.generateSessionId();
        session.setId(id);
        session.setMaxInactiveInterval(this.timeout * 60);

        return session;
    }

    public Session findSession(String id) {
        return this.sessions.get(id);
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getActiveSessions() {
        return activeSessions;
    }

    public void add(Session session) {
        this.sessions.put(session.getId(), session);
    }

    public void startInternal() {
        Thread recycle = new Thread(new Runnable() {
            public void run() {
                while (!stopBreak) {
                    //休眠1秒
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    List<Session> recSession = new ArrayList<Session>();//待回收的Session
                    for (Session session : sessions.values()) {
                        if (session.getMaxInactiveInterval() * 1000 < session.getIdleTime()) {//超时
                            session.expire();//标记为过期
                            recSession.add(session);
                        }
                    }
                    //回收session
                    for (Session session : recSession) {
                        remove(session);
                        session.recycle();//回收
                    }
                    activeSessions = sessions.size();
                }
            }
        });
        recycle.setName("TokenSessionRecycleThread");
        recycle.start();
    }

    public void stopInternal() {
        this.stopBreak = true;
    }
}
