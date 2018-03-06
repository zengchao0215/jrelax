package com.jrelax.event;

/**
 * 系统事件定义
 * Created by zengchao on 2017/6/9.
 */
public class ApplicationEventDefined {
    /**
     * 登陆前
     * user 用户对象
     */
    public static final String ON_BEFORE_LOGIN = "onBeforeLogin";
    /**
     * 登陆后
     * session 会话
     * user 用户对象
     * time 登陆时间
     */
    public static final String ON_AFTER_LOGIN = "onAfterLogin";

    /**
     * 用户创建完成
     * user 用户对象
     */
    public static final String ON_USER_CREATED = "onUserCreated";

    /**
     * 角色创建完成
     */
    public static final String ON_ROLE_CREATED = "onRoleCreated";

    /**
     * 机构创建完成
     */
    public static final String ON_UNIT_CREATED = "onUnitCreated";

    /**
     * 用户组创建完成
     */
    public static final String ON_GROUP_CREATED = "onGroupCreated";

    /**
     * 资源创建完成
     */
    public static final String ON_RESOURCE_CREATED = "onResourceCreated";

    /**
     * 系统配置更新后
     */
    public static final String ON_SYSTEM_CONFIG_UPDATED = "onSystemConfigUpdated";


    /**
     * 定时任务执行后
     */
    public static final String ON_SCHEDULER_EXECUTED = "onSchedulerExecuted";
}
