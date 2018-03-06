package com.jrelax.web.system.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zengchao
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "sys_scheduler")
@DynamicUpdate(true)
public class Scheduler implements Serializable {

    private static final long serialVersionUID = 3509941384851901401L;
    public final static String DEFAULT_JOB_GROUP = "JRELAX-SCHEDULER";
    public final static String DEFAULT_TRIGGER_GROUP = "JRELAX-TRIGGER";

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id; //
    @Column
    @NotNull
    private String name;//定时任务名称
    @Column
    @NotNull
    private String cron;//任务执行时间
    @Column(name = "job_class")
    @NotNull
    private String jobClass;//任务执行类
    @Column
    @NotNull
    private int status = 1;//任务状态
    @Column
    @NotNull
    private Boolean enabled = true;//是否启用
    @Column
    private String remarks;//备注
    @Column(name = "last_result")
    private String lastResult;//最后一次执行结果
    @Column(name = "last_exec_time")
    private java.util.Date lastExecTime;//最后一次执行时间
    @Column(name = "last_run_times")
    private String lastRunTimes;//最后一次运行耗时

    /**
     * 获取
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 定时任务名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置 定时任务名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 任务执行时间
     */
    public String getCron() {
        return this.cron;
    }

    /**
     * 设置 任务执行时间
     */
    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    /**
     * 获取 是否启用
     */
    public Boolean getEnabled() {
        return this.enabled;
    }

    /**
     * 设置 是否启用
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取 备注
     */
    public String getRemarks() {
        return this.remarks;
    }

    /**
     * 设置 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取 最后一次执行结果
     */
    public String getLastResult() {
        return this.lastResult;
    }

    /**
     * 设置 最后一次执行结果
     */
    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }

    /**
     * 获取 最后一次执行时间
     */
    public java.util.Date getLastExecTime() {
        return this.lastExecTime;
    }

    /**
     * 设置 最后一次执行时间
     */
    public void setLastExecTime(java.util.Date lastExecTime) {
        this.lastExecTime = lastExecTime;
    }

    /**
     * 获取 最后一次运行耗时
     */
    public String getLastRunTimes() {
        return this.lastRunTimes;
    }

    /**
     * 设置 最后一次运行耗时
     */
    public void setLastRunTimes(String lastRunTimes) {
        this.lastRunTimes = lastRunTimes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
