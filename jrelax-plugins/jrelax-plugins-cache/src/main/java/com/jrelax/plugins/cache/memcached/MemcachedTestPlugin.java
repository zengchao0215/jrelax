package com.jrelax.plugins.cache.memcached;

import java.util.Timer;
import java.util.TimerTask;

import com.jrelax.cache.memcached.JRelaxMemCachedManager;
import org.slf4j.Logger;
import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import org.slf4j.LoggerFactory;

/**
 * Memcached缓存服务器测试插件
 * 定时的与缓存服务器进行心跳测试
 *
 * @author zengchao
 */
@Plugin(value = "Memcached缓存服务器心跳测试插件", group = "缓存", loadOnStartup = false)
public class MemcachedTestPlugin extends TimerTask implements IPlugin {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Timer timer = null;

    @Override
    public boolean init() {
        timer = new Timer("Memcached缓存服务器心跳测试");
        timer.schedule(this, 5000, 1000 * 60 * 30);
        return true;
    }

    @Override
    public void destroy() {
        timer.cancel();
        timer = null;
    }

    /**
     * 定时检测
     */
    @Override
    public void run() {
        JRelaxMemCachedManager.getInstance().reset();
        boolean result = JRelaxMemCachedManager.getInstance().test();
        if (result) {
            JRelaxMemCachedManager.getInstance().setAvailable(true);
        } else {
            JRelaxMemCachedManager.getInstance().setAvailable(false);
        }
        logger.info("Memcached心跳测试：" + (result ? "服务可用" : "服务不可用"));
    }
}
