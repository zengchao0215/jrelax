package com.jrelax.job;

import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 任务管理器
 */
@Component
public class JobManager {
    private Scheduler scheduler;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 添加定时任务
     *
     * @param jobName          任务名称
     * @param jobGroupName     任务组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发组名称
     * @param jobClass         任务类
     * @param cron             时间设置
     * @return 定时任务实例ID
     */
    @SuppressWarnings("unchecked")
    public void add(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<?> jobClass, String cron) {
        try {
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends org.quartz.Job>) jobClass).withIdentity(jobName, jobGroupName).build();

            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            scheduler.scheduleJob(jobDetail, trigger);

            if (!scheduler.isShutdown()) {
                this.startAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("定时任务创建失败：" + jobName);
        }
    }

    /**
     * 更新时间
     *
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @param cron             新的时间设置
     */
    public void updateTrigger(String triggerName, String triggerGroupName, String cron) {
        TriggerKey triggerKey = null;
        try {
            triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            if (trigger == null) {
                return;
            }

            String oldCron = trigger.getCronExpression();
            if (!oldCron.equalsIgnoreCase(cron)) {
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                trigger = (CronTrigger) triggerBuilder.build();

                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除定时任务
     *
     * @param jobName          任务名称
     * @param jobGroupName     任务组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     */
    public void remove(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动所有定时任务
     */
    public void startAll() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止所有定时任务
     */
    public void stopAll() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停所有定时任务
     */
    public void pauseAll() {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停任务
     *
     * @param jobName      触发器名称
     * @param jobGroupName 触发器组名称
     */
    public void pause(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);

            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                scheduler.pauseJob(jobKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复任务
     *
     * @param jobName      触发器名称
     * @param jobGroupName 触发器组名称
     */
    public void resume(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);

            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                scheduler.resumeJob(jobKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否存在定时任务
     * @param jobName 任务名称
     * @param jobGroupName 任务组
     * @return
     */
    public boolean has(String jobName, String jobGroupName){
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);

            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
