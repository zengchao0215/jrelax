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
@Table(name = "sys_black_list")
@DynamicUpdate(true)
public class BlackList implements Serializable {
    private static final long serialVersionUID = 3509941384851901401L;
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id; //ID
    @Column
    @NotNull
    private String rules; //规则
    @Column
    private String remarks; //描述
    @Column
    @NotNull
    private boolean enabled; //是否启用
    @Column(name = "create_time")
    @NotNull
    private String createTime; //创建人
    @Column(name = "create_user")
    @NotNull
    private String createUser; //创建时间

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
    * 获取 规则
    */
    public String getRules() {
        return this.rules;
    }

    /**
    * 设置 规则
    */
    public void setRules(String rules) {
        this.rules = rules;
    }

    /**
    * 获取 描述
    */
    public String getRemarks() {
        return this.remarks;
    }

    /**
    * 设置 描述
    */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
    public String getCreateTime() {
        return this.createTime;
    }

    /**
    * 设置 创建人
    */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
    * 获取 创建时间
    */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
    * 设置 创建时间
    */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
