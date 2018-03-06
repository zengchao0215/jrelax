package com.jrelax.plugins.system.datadict;

import com.jrelax.cache.IDataDict;
import com.jrelax.cache.memcached.JRelaxMemCachedManager;
import com.jrelax.cache.provider.ext.DDHashMap;
import com.jrelax.cache.provider.DefaultDataDictProvider;
import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.kit.StringKit;
import com.jrelax.web.system.entity.DataDict;
import com.jrelax.web.system.entity.DataDictItem;
import com.jrelax.web.system.service.DataDictService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.hibernate.criterion.Order;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Plugin(value = "数据字典插件（Memcached）", group = "缓存", loadOnStartup = false, order = 1)
public class DataDictCacheProviderPlugin implements IPlugin, IDataDict {
    private String prefix = "DATADICT_CACHE_";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Set<String> keys = new HashSet<>();
    @Autowired
    private DataDictService ddService;

    public boolean init() {
        if (this.sync()) {
            ApplicationCommon.setDataDict(this);
            return true;
        }
        return false;
    }

    public void destroy() {
        ApplicationCommon.setDataDict(new DefaultDataDictProvider());
        logger.info("插件注销：数据字典插件（缓存服务器版）");
    }

    public boolean sync() {
        try {
            //从数据库中加载数据
            List<DataDict> dataDictList = ddService.list_NoLazy(Order.asc("createTime"));

            for (DataDict dd : dataDictList) {
                this.add(dd.getName(), dd.getItems());
                if (dd.getItems().size() == 0)
                    continue;
                if (isMap(dd.getItems())) {//判断是Map还是List
                    Map<String, String> map = new DDHashMap<>();
                    for (DataDictItem item : dd.getItems()) {
                        map.put(item.getK(), item.getV());
                    }
                    this.add("map_" + dd.getName(), map);
                } else {
                    List<String> list = new ArrayList<String>();
                    for (DataDictItem item : dd.getItems()) {
                        list.add(item.getV());
                    }
                    this.add("list_" + dd.getName(), list);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Object get(String key) {
        return this.getValue(key);
    }

    @SuppressWarnings("unchecked")
    public List<String> getList(String key) {
        return (List<String>) this.getValue("list_" + key);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getMap(String key) {
        return (Map<String, String>) this.getValue("map_" + key);
    }

    public JSONObject getJson(String key) {
        return JSONObject.fromObject(getMap(key));
    }

    public void put(String key, Object data) {
        JRelaxMemCachedManager.getClient().add(key, data);
        if (data instanceof List) {
            this.add("list_" + key, data);
            return;
        }
        if (data instanceof Map) {
            this.add("map_" + key, data);
        }

    }

    public boolean has(String key) {
        return this.getValue(key) != null;
    }

    public void remove(String key) {
        JRelaxMemCachedManager.getClient().delete(prefix + key);
    }

    public void clear() {
        JRelaxMemCachedManager.getClient().flushAll();
    }

    private boolean isMap(List<DataDictItem> list) {
        boolean isMap = true;
        for (DataDictItem item : list) {
            if (StringKit.isEmpty(item.getK())) {
                isMap = false;
                break;
            }
        }
        return isMap;
    }

    private void add(String key, Object value) {
        JRelaxMemCachedManager.getClient().add(prefix + key, value);
        keys.add(prefix + key);
    }

    private Object getValue(String key) {
        return JRelaxMemCachedManager.getClient().get(prefix + key);
    }
}