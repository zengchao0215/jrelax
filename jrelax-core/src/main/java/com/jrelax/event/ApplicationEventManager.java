package com.jrelax.event;

import com.jrelax.core.support.ApplicationContextHelper;
import com.jrelax.event.annotation.ApplicationEvent;
import com.jrelax.kit.ClassKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 事件管理器
 * Created by zengchao on 2017-03-09.
 */
public class ApplicationEventManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, List<IApplicationEvent>> eventMap = new HashMap<>();

    public Map<String, List<IApplicationEvent>> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<String, List<IApplicationEvent>> eventMap) {
        this.eventMap = eventMap;
    }

    /**
     * 绑定事件
     *
     * @param eventName 事件名称
     * @param event     事件处理程序
     */
    public synchronized void bind(String eventName, IApplicationEvent event) {
        List<IApplicationEvent> eventList = eventMap.get(eventName);
        if (eventList == null) eventList = new ArrayList<>();
        eventList.add(event);
        eventMap.put(eventName, eventList);

        logger.debug("Bind Event : " + eventName);
    }

    /**
     * 解绑事件
     *
     * @param eventName 事件名称
     * @param event     事件处理程序
     */
    public synchronized void unbind(String eventName, IApplicationEvent event) {
        List<IApplicationEvent> eventList = eventMap.get(eventName);
        if (eventList == null) return;
        eventList.remove(event);
        logger.debug("UnBind Event : " + eventName);
    }

    /**
     * 触发事件
     *
     * @param eventName 事件名称
     * @param source    来源
     * @param params    参数
     */
    public synchronized void trigger(String eventName, Object source, Object params) {
        List<IApplicationEvent> eventList = eventMap.get(eventName);
        if (eventList == null) return;
        for (IApplicationEvent event : eventList) {
            try {
                event.onTrigger(source, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化
     * 扫描系统中注解形式的事件
     */
    public void init() {
        Set<Class<?>> classes = ClassKit.getClassesByAnnotation("com.jrelax", ApplicationEvent.class);
        for (Class<?> cls : classes) {
            Object instance = null;
            Map<String, ?> beanMap = ApplicationContextHelper.getInstance().getBeansOfType(cls);
            if (beanMap.size() == 0) {
                instance = ApplicationContextHelper.getInstance().createBean(cls);
            } else {
                for (Map.Entry<String, ?> map : beanMap.entrySet()) {
                    instance = map.getValue();

                    if (instance != null) break;
                }
            }
            if (instance instanceof IApplicationEvent) {
                ApplicationEvent event = cls.getAnnotation(ApplicationEvent.class);

                this.bind(event.value(), (IApplicationEvent) instance);
            }
        }

        logger.info("系统事件注册数量：" + this.eventMap.size());
    }
}
