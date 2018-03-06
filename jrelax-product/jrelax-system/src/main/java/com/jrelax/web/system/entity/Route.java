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
@Table(name = "sys_route")
@DynamicUpdate(true)
public class Route implements Serializable {
    private static final long serialVersionUID = 3509941384851901401L;
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id; //ID
    @Column(name = "source_url")
    @NotNull
    private String sourceUrl; //来源URL
    @Column(name = "target_url")
    @NotNull
    private String targetUrl; //转向URL
    @Column
    @NotNull
    private boolean redirect; //是否重定向
    @Column
    @NotNull
    private boolean internal; //是否是内部链接
    @Column
    private boolean enabled; //是否启用
    @Column(name = "create_user")
    private String createUser; //创建人
    @Column(name = "create_time")
    private String createTime; //创建时间

    /**
    * 获取 ID
    */
    public String getId() {
        return this.id;
    }

    /**
    * 设置 ID
    */
    public void setId(String id) {
        this.id = id;
    }

    /**
    * 获取 来源URL
    */
    public String getSourceUrl() {
        return this.sourceUrl;
    }

    /**
    * 设置 来源URL
    */
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    /**
    * 获取 转向URL
    */
    public String getTargetUrl() {
        return this.targetUrl;
    }

    /**
    * 设置 转向URL
    */
    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    /**
    * 获取 是否重定向
    */
    public boolean isRedirect() {
        return this.redirect;
    }

    /**
    * 设置 是否重定向
    */
    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    /**
    * 获取 是否是内部链接
    */
    public boolean isInternal() {
        return this.internal;
    }

    /**
    * 设置 是否是内部链接
    */
    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    /**
    * 获取 是否启用
    */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
    * 设置 是否启用
    */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
    * 获取 创建人
    */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
    * 设置 创建人
    */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
    * 获取 创建时间
    */
    public String getCreateTime() {
        return this.createTime;
    }

    /**
    * 设置 创建时间
    */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
