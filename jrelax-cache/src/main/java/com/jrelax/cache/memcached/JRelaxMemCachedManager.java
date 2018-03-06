package com.jrelax.cache.memcached;

import com.whalin.MemCached.MemCachedClient;
import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.kit.ObjectKit;

/**
 * MemCached缓存客户端管理器
 * Created by zengchao on 2016-11-21.
 */
public class JRelaxMemCachedManager {
    private static JRelaxMemCachedManager instance = null;
    private MemCachedClient client;
    private boolean available = false;//是否可用

    private void init(){
        MemCachedPoolConfig poolConfig = new MemCachedPoolConfig();
        poolConfig.setInitConn(Integer.parseInt(JRelaxSystemConfigHelper.get("cache.memcached.initConn", "10")));
        poolConfig.setMinConn(Integer.parseInt(JRelaxSystemConfigHelper.get("cache.memcached.minConn", "10")));
        poolConfig.setMaxConn(Integer.parseInt(JRelaxSystemConfigHelper.get("cache.memcached.maxConn", "10")));
        MemCachedConnectionFactory connectionFactory = new MemCachedConnectionFactory(poolConfig);
        connectionFactory.setServers(new String[]{JRelaxSystemConfigHelper.get("cache.memcached.url", "localhost") + ":" + JRelaxSystemConfigHelper.get("port", "11211")});
        connectionFactory.setWeights(new Integer[]{Integer.parseInt(JRelaxSystemConfigHelper.get("cache.memcached.weight", "3"))});
        connectionFactory.setMaxIdel(Integer.parseInt(JRelaxSystemConfigHelper.get("cache.memcached.maxIdle", "3600000")));
        connectionFactory.setMaintSleep(Integer.parseInt(JRelaxSystemConfigHelper.get("cache.memcached.maintSleep", "60")));

        MemCachedTemplate template = new MemCachedTemplate(connectionFactory);
        client = template.getClient();
    }

    public static JRelaxMemCachedManager getInstance(){
        if(instance == null){
            instance = new JRelaxMemCachedManager();
            instance.init();
        }
        return instance;
    }
    /**
     * 获取缓存客户端
     *
     * @return
     */
    public static MemCachedClient getClient() {
        return getInstance().client;
    }

    /**
     * 判断缓存 是否可用
     * @return
     */
    public boolean isAvailable() {
        return available && (ObjectKit.isNull(client.get("refreshing")) || !(Boolean) client.get("refreshing"));
    }
    /**
     * 设置服务是否可用
     * @param available 是否可用
     */
    public void setAvailable(boolean available){
        this.available = available;
    }
    /**
     * 心跳测试
     * @return
     */
    public boolean test(){
        return client.set("test", true);
    }

    /**
     * 重新初始化
     * @return
     */
    public boolean reset(){
        setAvailable(false);
        client = null;
        init();
        return true;
    }
}
