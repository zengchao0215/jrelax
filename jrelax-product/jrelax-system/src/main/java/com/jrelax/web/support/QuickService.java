package com.jrelax.web.support;

import com.jrelax.core.support.ApplicationContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 快捷Service基类，无需创建类来集成BaseService
 * 直接使用
 * Created by zengchao on 2017-05-15.
 */
public class QuickService {
    private static Logger logger = LoggerFactory.getLogger(QuickService.class);
    private static Map<Class, QuickService> baseServiceMap = new HashMap<>();
    @Resource
    private BaseService<?> baseService;

    private BaseService<?> getBaseService() {
        return baseService;
    }

    private void setBaseService(BaseService<?> baseService) {
        this.baseService = baseService;
    }

    public static BaseService<?> get(Class entity) {
        QuickService simpleBaseService = baseServiceMap.get(entity);
        if (simpleBaseService == null) {
            logger.debug("Create QuickService Instance");
            simpleBaseService = ApplicationContextHelper.getInstance().createBean(QuickService.class);
            BaseService service = ApplicationContextHelper.getInstance().createBean(BaseService.class);
            service.setEntityClass(entity);
            simpleBaseService.setBaseService(service);
            //缓存
            baseServiceMap.put(entity, simpleBaseService);
        }
        return simpleBaseService.getBaseService();
    }
}
