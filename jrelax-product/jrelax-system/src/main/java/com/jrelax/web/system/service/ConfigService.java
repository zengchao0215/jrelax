package com.jrelax.web.system.service;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.event.ApplicationEventDefined;
import com.jrelax.kit.ObjectKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.Config;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigService extends BaseService<Config> {

    public void save(String k, String v) {
        Config cfg = super.get(Condition.NEW().eq("k", k));
        if (cfg == null)
            cfg = new Config();

        cfg.setK(k);
        cfg.setV(v);
        cfg.setEnabled(true);

        super.saveOrUpdate(cfg);
    }


    public Map<String, String> getMapByGroup(String group) {
        Map<String, String> map = new HashMap<String, String>();

        List<Config> cfgList = super.list(Condition.NEW().like("k", group, MatchMode.START).eq("enabled", true));

        for (Config config : cfgList) {
            map.put(config.getK(), config.getV());
        }

        return map;
    }

    /**
     * 获取单项值
     *
     * @param k
     * @return
     */
    public String getV(String k) {
        Config cfg = super.get(Restrictions.eq("k", k));
        if (ObjectKit.isNotNull(cfg))
            return cfg.getV();
        else
            return "";
    }

    /**
     * 更新配置，如果K不存在则创建，存在则更新
     *
     * @param configs
     */
    public void update(Map<String, String> configs) {
        String[] keys = new String[]{
                "system.debug",
                "upload.remote.enabled",
                "mail.smtp.auth",
                "system.login.unique",
                "system.login.verify",
                "system.admin.title.unit",
                "system.unit.res",
                "system.unit.role",
                "system.perm.force"
        };
        //处理配置项目
        parseBooleanConfig(configs, keys);
        for (String key : configs.keySet()) {
            Config cfg = super.get(Restrictions.eq("k", key));
            if (ObjectKit.isNotNull(cfg)) {//如果存在则更新
                String value = configs.get(key);
                cfg.setV(value);

                super.merge(cfg);
            } else {//不存在就创建
                this.save(key, configs.get(key));
            }
        }

        this.flush();
        afterPropertiesSet();

        getEventManager().trigger(ApplicationEventDefined.ON_SYSTEM_CONFIG_UPDATED, this, configs);
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
     * 使配置文件生效
     */
    public void afterPropertiesSet() {
        ApplicationCommon.APP = JRelaxSystemConfigHelper.get("system.app");
        ApplicationCommon.RESPATH = JRelaxSystemConfigHelper.get("system.respath", "");
        ApplicationCommon.DEBUG = JRelaxSystemConfigHelper.getBoolean("system.debug", false);
        ApplicationCommon.SYSTEM_LOGIN_TITLE = JRelaxSystemConfigHelper.get("system.login.title");
        ApplicationCommon.SYSTEM_ADMIN_TITLE = JRelaxSystemConfigHelper.get("system.admin.title");
    }

    /**
     * 刷新配置缓存
     */
    public void flush() {
        List<Config> configs = super.list(Condition.NEW().eq("enabled", true));
        JRelaxSystemConfigHelper.getConfigMap().clear();
        for (Config config : configs) {
            JRelaxSystemConfigHelper.getConfigMap().put(config.getK(), config.getV());
        }

        this.afterPropertiesSet();
    }
}
