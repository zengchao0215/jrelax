package com.jrelax.plugins.system;

import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.job.JobManager;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.system.entity.Scheduler;
import com.jrelax.web.system.service.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

@Plugin(value = "定时任务初始化插件", group = "系统", loadOnStartup = true, order = 1)
public class SchedulerInitPlugin implements IPlugin {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "quartzJobManager")
    private JobManager jobManager;
    @Resource
    private SchedulerService schedulerService;

    @Override
    public boolean init() {
        List<Scheduler> list = schedulerService.list(Condition.NEW().eq("enabled", true));

        for (Scheduler scheduler : list) {
            try {
                jobManager.add(scheduler.getId(), Scheduler.DEFAULT_JOB_GROUP, scheduler.getId(), Scheduler.DEFAULT_TRIGGER_GROUP, Class.forName(scheduler.getJobClass()), scheduler.getCron());

                //暂停
                if (scheduler.getStatus() == 2) {
                    jobManager.pause(scheduler.getId(), Scheduler.DEFAULT_JOB_GROUP);
                }
            } catch (Exception e) {
                logger.error("定时任务：" + scheduler.getName() + "初始化失败");
            }
        }
        logger.info("定时任务初始化");
        return true;
    }

    @Override
    public void destroy() {
        logger.info("定时任务销毁");
        jobManager.stopAll();
    }
}
