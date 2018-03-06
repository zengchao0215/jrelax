package com.jrelax.cache.redis;

import com.jrelax.config.JRelaxSystemConfigHelper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis管理器
 * Created by zengchao on 2016-11-21.
 */
public class JRelaxRedisManager {
    private static JRelaxRedisManager instance;
    private JedisConnectionFactory connectionFactory;
    private RedisTemplate<String, Object> template;

    /**
     * 初始化
     */
    private void init() {
        //连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(JRelaxSystemConfigHelper.getInt("cache.redis.maxTotal", JedisPoolConfig.DEFAULT_MAX_TOTAL));
        poolConfig.setMaxIdle(JRelaxSystemConfigHelper.getInt("cache.redis.maxIdle", JedisPoolConfig.DEFAULT_MAX_IDLE));
        poolConfig.setMinIdle(JRelaxSystemConfigHelper.getInt("cache.redis.minIdle", JedisPoolConfig.DEFAULT_MIN_IDLE));
        poolConfig.setTestOnBorrow(JRelaxSystemConfigHelper.getBoolean("cache.redis.testOnBorrow", true));
        poolConfig.setTestOnReturn(JRelaxSystemConfigHelper.getBoolean("cache.redis.testOnReturn", true));

        //创建连接
        connectionFactory = new JedisConnectionFactory(poolConfig);
        connectionFactory.setHostName(JRelaxSystemConfigHelper.get("cache.redis.hostname", "localhost"));
        connectionFactory.setPort(JRelaxSystemConfigHelper.getInt("cache.redis.port", 6379));


        template = new RedisTemplate<>();
    }

    /**
     * 获取模板
     * @return
     */
    public static RedisTemplate<String, Object> getTemplate(){
        if(instance == null){
            instance = new JRelaxRedisManager();
            instance.init();
        }
        return instance.template;
    }

    /**
     * 销毁
     */
    public void destroy(){
        if(connectionFactory != null)
            connectionFactory.destroy();
        instance = null;
    }

    public static ValueOperations<String, Object> getValueStore(){
        return getTemplate().opsForValue();
    }

    public static HashOperations<String, String, Object> getHashStore(){
        return getTemplate().opsForHash();
    }

    public static ListOperations<String, Object> getListStore(){
        return getTemplate().opsForList();
    }

    public static SetOperations<String, Object> getSetStore(){
        return getTemplate().opsForSet();
    }

    public static ZSetOperations<String, Object> getZSetStore(){
        return getTemplate().opsForZSet();
    }

    public static ClusterOperations<String, Object> getClusterStore(){
        return getTemplate().opsForCluster();
    }

    public static HyperLogLogOperations<String, Object> getHyperLogLogStore(){
        return getTemplate().opsForHyperLogLog();
    }

    public String getPing(){
        return (String) getTemplate().execute((RedisCallback<Object>) RedisConnectionCommands::ping);
    }
}
