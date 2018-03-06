package com.jrelax.cache.memcached;

import com.whalin.MemCached.MemCachedClient;
import com.jrelax.kit.ObjectKit;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * MemCached模板类
 * Created by zengchao on 2016-11-21.
 */
public class MemCachedTemplate {
    private MemCachedClient client;
    private MemCachedConnectionFactory connectionFactory;

    public MemCachedTemplate(){

    }

    public MemCachedTemplate(MemCachedConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }
    public MemCachedConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(MemCachedConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * 获取缓存客户端
     * 单例模式
     * @return
     */
    public MemCachedClient getClient(){
        if(ObjectKit.isNotNull(client)){
            return client;
        }else{
            this.client = connectionFactory.getMemCachedClient();//初始化客户端链接
        }
        return client;
    }

    /**
     * 销毁
     */
    public void destroy(){
        connectionFactory.destroy();
        client = null;
    }
}
