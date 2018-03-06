package com.jrelax.web.system.service;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.session.SessionAttributeManager;
import com.jrelax.kit.DateKit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.User;
import com.jrelax.web.system.entity.UserConfig;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserConfigService extends BaseService<UserConfig> {

    /**
     * 保存配置
     *
     * @param k
     * @param v
     */
    public void save(String k, String v) {
        UserConfig cfg = super.get(Condition.NEW().eq("k", k));
        if (cfg == null)
            cfg = new UserConfig();

        cfg.setK(k);
        cfg.setV(v);
        cfg.setEnabled(true);
        cfg.setUser(getCurrentUser());
        cfg.setCreateTime(getCurrentTime());

        super.saveOrUpdate(cfg);
    }

    /**
     * 保存或更新配置
     *
     * @param user
     * @param key
     * @param value
     * @return
     */
    public void save(User user, String key, String value) {
        UserConfig config = super.get(Condition.NEW().eq("user", user).eq("k", key));
        if (config == null) {
            config = new UserConfig();
            config.setUser(user);
            config.setCreateTime(DateKit.now());
            config.setEnabled(true);
        }

        config.setK(key);
        config.setV(value);

        super.saveOrUpdate(config);
    }

    public void update(Map<String, String> configs) {
        String[] keys = new String[]{
        };
        //处理配置项目
        parseBooleanConfig(configs, keys);
        for (String key : configs.keySet()) {
            UserConfig cfg = super.get(Restrictions.eq("k", key));
            if (ObjectKit.isNotNull(cfg)) {//如果存在则更新
                String value = configs.get(key);
                cfg.setV(value);

                super.merge(cfg);
            } else {//不存在就创建
                this.save(key, configs.get(key));
            }
        }

        this.flush();
    }

    private void parseBooleanConfig(Map<String, String> configMap, String[] keys) {
        for (String key : keys) {
            if (!configMap.containsKey(key))
                configMap.put(key, "false");
            else
                configMap.put(key, "true");
        }
    }

    /**
     * 删除用户配置
     *
     * @param user
     * @param key
     */
    public void delete(User user, String key) {
        UserConfig config = super.get(Condition.NEW().eq("user", user).eq("k", key));
        if (config != null) super.delete(config);
    }

    /**
     * 刷新用户配置，从数据库中重新读取
     */
    public void flush() {
        User user = getCurrentUser();
        if (user == null) return;
        List<UserConfig> configList = super.list(Condition.NEW().eq("user", user).eq("enabled", true));
        Map<String, String> configMap = new HashMap<>();
        for (UserConfig config : configList) {
            configMap.put(config.getK(), config.getV());
        }
        SessionAttributeManager.put(ApplicationCommon.CACHE_USER_CONFIG, configMap);
    }
}
