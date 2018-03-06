package com.jrelax.job;

import com.jrelax.event.ApplicationEventManager;
import com.jrelax.event.ApplicationEventDefined;
import org.quartz.JobExecutionContext;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务接口
 */
public abstract class Job implements org.quartz.Job {
    @Resource
    private ApplicationEventManager applicationEventManager;

    @Override
    @Transactional
    public void execute(JobExecutionContext jobExecutionContext) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("jobClass", this.getClass().getName());
        paramsMap.put("startTime", new Date());

        long t1 = System.currentTimeMillis();
        try {
            this.run(jobExecutionContext);

            paramsMap.put("status", 0);
            paramsMap.put("msg", "SUCCESS");
        } catch (Exception e) {
            paramsMap.put("status", 1);
            paramsMap.put("msg", e.getMessage());
        } finally {
            long t2 = System.currentTimeMillis();
            paramsMap.put("times", t2 - t1);
        }

        applicationEventManager.trigger(ApplicationEventDefined.ON_SCHEDULER_EXECUTED, this, paramsMap);
    }

    public abstract void run(JobExecutionContext jobExecutionContext);
}
