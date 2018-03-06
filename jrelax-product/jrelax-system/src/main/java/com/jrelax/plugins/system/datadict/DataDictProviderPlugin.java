package com.jrelax.plugins.system.datadict;

import com.jrelax.cache.IDataDict;
import com.jrelax.cache.provider.ext.DDHashMap;
import com.jrelax.cache.provider.DefaultDataDictProvider;
import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.kit.DateKit;
import com.jrelax.kit.StringKit;
import com.jrelax.web.system.entity.DataDict;
import com.jrelax.web.system.entity.DataDictItem;
import com.jrelax.web.system.service.DataDictItemService;
import com.jrelax.web.system.service.DataDictService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.hibernate.criterion.Order;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Plugin(value = "数据字典插件", group = "系统", loadOnStartup = true, order = 1)
public class DataDictProviderPlugin implements IPlugin, IDataDict {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DataDictService ddService;
    @Autowired
    private DataDictItemService ddiService;

    private Map<String, Map<String, String>> _map;
    private Map<String, List<String>> _list;
    private Map<String, Object> _value;

    public boolean init() {
        if (this.sync()) {
            ApplicationCommon.setDataDict(this);
            logger.info("数据字典插件加载完成");
            return true;
        }
        return false;
    }

    public void destroy() {
        _map.clear();
        _list.clear();
        _value.clear();
        ApplicationCommon.setDataDict(new DefaultDataDictProvider());
        logger.info("数据字典插件已注销");
    }

    public void before(Method method, Object[] params, Object obj) {

    }

    public void afterReturning(Object returnValue, Method method,
                               Object[] params, Object obj) {

    }

    public boolean sync() {
        try {
            _map = new DDHashMap<String, Map<String, String>>();
            _list = new DDHashMap<String, List<String>>();
            _value = new DDHashMap<String, Object>();

            //从数据库中加载数据
            List<DataDict> dataDictList = ddService.list_NoLazy(Order.asc("createTime"));

            for (DataDict dd : dataDictList) {
                _value.put(dd.getName(), dd.getItems());
                if (dd.getItems().size() == 0)
                    continue;
                if (isMap(dd.getItems())) {//判断是Map还是List
                    Map<String, String> map = new DDHashMap<String, String>();
                    for (DataDictItem item : dd.getItems()) {
                        map.put(item.getK(), item.getV());
                    }
                    _map.put(dd.getName(), map);
                } else {
                    List<String> list = new ArrayList<String>();
                    for (DataDictItem item : dd.getItems()) {
                        list.add(item.getV());
                    }
                    _list.put(dd.getName(), list);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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

    public Object get(String key) {
        return _value.get(key);
    }

    public List<String> getList(String key) {
        return _list.get(key);
    }

    public Map<String, String> getMap(String key) {
        return _map.get(key);
    }

    public JSONObject getJson(String key) {
        return JSONObject.fromObject(getMap(key));
    }

    @SuppressWarnings("unchecked")
    public void put(String key, Object data) {
        _value.put(key, data);
        if (data instanceof List) {
            List<String> list = (List<String>) data;
            _list.put(key, list);
            //持久化到数据库中
            DataDict dd = new DataDict();
            dd.setName(key);
            dd.setCreateUser("DataDictProviderPlugin");
            dd.setCreateTime(DateKit.now());
            ddService.save(dd);

            for (int i = 0; i < list.size(); i++) {
                DataDictItem item = new DataDictItem();

                item.setDataDict(dd);
                item.setK("");
                item.setV(list.get(i));
                item.setLocation(i + 1);
                item.setCreateUser("DataDictProviderPlugin");
                item.setCreateTime(DateKit.now());

                dd.getItems().add(item);
            }

            return;
        }
        if (data instanceof Map) {
            Map<String, String> map = (Map<String, String>) data;
            _map.put(key, map);
            //持久化到数据库中
            int i = 1;
            DataDict dd = new DataDict();
            dd.setName(key);
            dd.setCreateUser("DataDictProviderPlugin");
            dd.setCreateTime(DateKit.now());
            ddService.save(dd);
            for (String k : map.keySet()) {
                DataDictItem item = new DataDictItem();

                item.setDataDict(dd);
                item.setK(k);
                item.setV(map.get(k));
                item.setLocation(i + 1);
                item.setCreateUser("DataDictProviderPlugin");
                item.setCreateTime(DateKit.now());

                ddiService.save(item);
                i++;
            }
            return;
        }
        //对于单个值
        DataDict dd = new DataDict();
        dd.setName(key);
        DataDictItem item = new DataDictItem();
        item.setK("");
        item.setV(String.valueOf(data));
        item.setLocation(1);
        item.setCreateUser("DataDictProviderPlugin");
        item.setCreateTime(DateKit.now());

        dd.getItems().add(item);
        ddService.save(dd);
    }

    public boolean has(String key) {
        return _value.containsKey(key) || _list.containsKey(key) || _map.containsKey(key);
    }

    public void remove(String key) {
        _value.remove(key);
        _list.remove(key);
        _map.remove(key);
    }

    public void clear() {
        _value.clear();
        _list.clear();
        _map.clear();
    }
}