package com.jrelax.web.system.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zengchao
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "sys_group")
@DynamicUpdate(true)
public class Group implements Serializable {

    private static final long serialVersionUID = 3509941384851901401L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id; //ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;
    @Column
    @NotNull
    private String name;//名称
    @Column
    private String descript;//描述
    @Column(name = "create_user")
    @NotNull
    private String createUser;//创建人
    @Column(name = "create_time")
    @NotNull
    private String createTime;//创建时间
    @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.REFRESH},fetch=FetchType.LAZY)
    @JoinTable(name="sys_group_role",joinColumns={@JoinColumn(name="group_id")},inverseJoinColumns={@JoinColumn(name="role_id")})
    private List<Role> roles = new ArrayList<Role>();//角色列表

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
     * 获取 所属机构
     */
    public Unit getUnit() {
        return this.unit;
    }

    /**
     * 设置 所属机构
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * 获取 名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 描述
     */
    public String getDescript() {
        return this.descript;
    }

    /**
     * 设置 描述
     */
    public void setDescript(String descript) {
        this.descript = descript;
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

    /**
     * 获取 角色列表
     * @return
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * 设置 角色列表
     * @param roles
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
