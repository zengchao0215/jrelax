package com.jrelax.web.system.service;

import com.jrelax.orm.query.Condition;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.Scheduler;
import com.jrelax.web.system.entity.SchedulerLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zengchao
 * @version 1.0
 * @since 1.0
 */
@Service
public class SchedulerLogService extends BaseService<SchedulerLog> {
    @Resource
    private SchedulerService schedulerService;

    /**
     * 保存日志
     *
     * @param jobClass  执行类
     * @param status    状态
     * @param msg       消息
     * @param startTime 启动时间
     * @param times     执行耗时
     */
    public void save(String jobClass, int status, String msg, Date startTime, long times) {
        Scheduler scheduler = schedulerService.get(Condition.NEW().eq("jobClass", jobClass));
        if (scheduler != null) {
            SchedulerLog log = new SchedulerLog();
            log.setScheduler(scheduler);
            log.setStatus(status);
            log.setMsg(msg);
            log.setExecTime(startTime);
            log.setTimes(times + "ms");

            super.save(log);
        }
    }
}
