package com.jrelax.web.system.support;

import com.jrelax.event.ApplicationEventDefined;
import com.jrelax.event.IApplicationEvent;
import com.jrelax.event.annotation.ApplicationEvent;
import com.jrelax.web.system.service.SchedulerLogService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@ApplicationEvent(ApplicationEventDefined.ON_SCHEDULER_EXECUTED)
public class SchedulerEventTrigger implements IApplicationEvent {
    @Resource
    private SchedulerLogService schedulerLogService;

    @Override
    public void onTrigger(Object source, Object params) {
        Map<String, Object> paramsMap = (Map<String, Object>) params;

        schedulerLogService.save((String) paramsMap.get("jobClass"), (int) paramsMap.get("status"), (String) paramsMap.get("msg"), (Date) paramsMap.get("startTime"), (long) paramsMap.get("times"));
    }
}
