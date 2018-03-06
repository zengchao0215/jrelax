package com.jrelax.cache.memcached;

/**
 * MemCached 连接池配置
 * Created by zengchao on 2016-11-21.
 */
public class MemCachedPoolConfig {
    public static final int DEFAULT_INIT_CONN = 8;
    public static final int DEFAULT_MAX_CONN = 8;
    public static final int DEFAULT_MIN_CONN = 0;
    private int initConn = DEFAULT_INIT_CONN;//初始化连接数
    private int maxConn = DEFAULT_MAX_CONN;//最大连接数
    private int minConn = DEFAULT_MIN_CONN;//最小连接数

    public int getInitConn() {
        return initConn;
    }

    public void setInitConn(int initConn) {
        this.initConn = initConn;
    }

    public int getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }

    public int getMinConn() {
        return minConn;
    }

    public void setMinConn(int minConn) {
        this.minConn = minConn;
    }
}
