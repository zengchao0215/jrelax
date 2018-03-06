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
@Table(name = "sys_scheduler_log")
@DynamicUpdate(true)
public class SchedulerLog implements Serializable {

    private static final long serialVersionUID = 3509941384851901401L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id; //
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduler_id")
    private Scheduler scheduler;
    @Column
    @NotNull
    private Integer status;//状态
    @Column
    private String msg;//消息
    @Column(name = "exec_time")
    @NotNull
    private java.util.Date execTime;//执行时间
    @Column
    @NotNull
    private String times;//运行时长

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
     * 获取 关联任务
     */
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    /**
     * 设置 关联任务
     */
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 获取 状态
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取 消息
     */
    public String getMsg() {
        return this.msg;
    }

    /**
     * 设置 消息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取 执行时间
     */
    public java.util.Date getExecTime() {
        return this.execTime;
    }

    /**
     * 设置 执行时间
     */
    public void setExecTime(java.util.Date execTime) {
        this.execTime = execTime;
    }

    /**
     * 获取 运行时长
     */
    public String getTimes() {
        return this.times;
    }

    /**
     * 设置 运行时长
     */
    public void setTimes(String times) {
        this.times = times;
    }
}
