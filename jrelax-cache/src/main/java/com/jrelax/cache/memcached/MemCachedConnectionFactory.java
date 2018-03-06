package com.jrelax.cache.memcached;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import com.jrelax.config.JRelaxSystemConfigHelper;

/**
 * MemCache工厂类
 * Created by zengchao on 2016-11-21.
 */
public class MemCachedConnectionFactory {
    private String[] servers;//主机地址
    private Integer[] weights;//权重
    private MemCachedPoolConfig poolConfig;//连接池配置
    private int maxIdel = 6000;//最长等待时间
    private int maintSleep = 60;//休眠时间
    SockIOPool pool = null;//获取连接池实例

    public MemCachedConnectionFactory(){
        this.poolConfig = new MemCachedPoolConfig();
    }

    public MemCachedConnectionFactory(MemCachedPoolConfig poolConfig){
        this.poolConfig = poolConfig;
    }

    public String[] getServers() {
        return servers;
    }

    public void setServers(String[] servers) {
        this.servers = servers;
    }

    public Integer[] getWeights() {
        return weights;
    }

    public void setWeights(Integer[] weights) {
        this.weights = weights;
    }

    public int getMaxIdel() {
        return maxIdel;
    }

    public void setMaxIdel(int maxIdel) {
        this.maxIdel = maxIdel;
    }

    public int getMaintSleep() {
        return maintSleep;
    }

    public void setMaintSleep(int maintSleep) {
        this.maintSleep = maintSleep;
    }

    /**
     * 获取客户端实例
     * @return
     */
    public MemCachedClient getMemCachedClient(){
        MemCachedClient client = new MemCachedClient();
        //获取连接池实例
        pool = SockIOPool.getInstance();

        // 设置服务器信息
        pool.setServers(servers);
        pool.setWeights(weights);

        // 设置初始化连接数、最小连接数、最大连接数、最大处理时间
        pool.setInitConn(this.poolConfig.getInitConn());
        pool.setMinConn(this.poolConfig.getMinConn());
        pool.setMaxConn(this.poolConfig.getMaxConn());
        pool.setMaxIdle(maxIdel);

        //设置连接池守护线程的睡眠时间
        pool.setMaintSleep(maintSleep);

        //设置TCP参数，连接超时
        pool.setNagle(false);
        pool.setSocketTO(60);
        pool.setSocketConnectTO(0);

        // 初始化连接池
        pool.initialize();

        return client;
    }

    /**
     * 销毁连接
     */
    public void destroy(){
        if(pool != null) pool.shutDown();
    }
}
