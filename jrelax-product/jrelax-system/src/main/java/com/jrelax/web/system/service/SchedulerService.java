package com.jrelax.web.system.service;

import com.jrelax.job.JobManager;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author zengchao
 * @version 1.0
 * @since 1.0
 */
@Service
public class SchedulerService extends BaseService<Scheduler> {
    @Resource(name = "quartzJobManager")
    private JobManager jobManager;

    @Override
    public void save(Scheduler scheduler) {
        super.save(scheduler);

        if (scheduler.getEnabled()) {
            //添加定时任务
            try {
                jobManager.add(scheduler.getId(), Scheduler.DEFAULT_JOB_GROUP, scheduler.getId(), Scheduler.DEFAULT_TRIGGER_GROUP, Class.forName(scheduler.getJobClass()), scheduler.getCron());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Scheduler merge(Scheduler scheduler) {
        scheduler = super.merge(scheduler);

        if (scheduler.getEnabled()) {
            if (jobManager.has(scheduler.getId(), Scheduler.DEFAULT_JOB_GROUP)) {
                //更新定时任务
                jobManager.updateTrigger(scheduler.getId(), Scheduler.DEFAULT_TRIGGER_GROUP, scheduler.getCron());
            } else {
                try {
                    jobManager.add(scheduler.getId(), Scheduler.DEFAULT_JOB_GROUP, scheduler.getId(), Scheduler.DEFAULT_TRIGGER_GROUP, Class.forName(scheduler.getJobClass()), scheduler.getCron());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            jobManager.remove(scheduler.getId(), Scheduler.DEFAULT_JOB_GROUP, scheduler.getId(), Scheduler.DEFAULT_JOB_GROUP);
        }


        return scheduler;
    }

    @Override
    public void delete(Serializable id) {
        super.delete(id);

        //移除定时任务
        jobManager.remove(id.toString(), Scheduler.DEFAULT_JOB_GROUP, id.toString(), Scheduler.DEFAULT_TRIGGER_GROUP);
    }

    /**
     * 暂停任务
     *
     * @param id
     */
    @Transactional
    public void pause(String id) {
        super.executeBatch("update Scheduler set status = 2 where id = ?", id);
        jobManager.pause(id, Scheduler.DEFAULT_JOB_GROUP);
    }

    /**
     * 恢复任务
     *
     * @param id
     */
    @Transactional
    public void resume(String id) {
        super.executeBatch("update Scheduler set status = 1 where id = ?", id);
        jobManager.resume(id, Scheduler.DEFAULT_JOB_GROUP);
    }
}
