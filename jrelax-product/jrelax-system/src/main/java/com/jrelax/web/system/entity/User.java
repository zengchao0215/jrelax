package com.jrelax.web.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sys_user")
@DynamicUpdate(true)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)// 开启二级缓存
public class User implements Serializable, com.jrelax.core.web.model.User {
    /**
     *
     */
    private static final long serialVersionUID = -2308233050003124998L;
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;
    @Column(name = "user_name")
    @NotNull
    private String userName;
    @Column
    @NotNull
    private String password;
    @Column(name = "real_name")
    private String realName;
    @Column
    private String mobile;
    @Column
    private String email;
    @Column
    private String qq;
    @Column(name = "page_style")
    private String pageStyle;
    @Column
    private String layout;
    @Column
    private boolean enabled;
    @Column
    private boolean online;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "last_login_time")
    private String lastLoginTime;
    @Column(name = "last_login_ip")
    private String lastLoginIp;
    @Column(name = "last_login_type")
    private int lastLoginType;
    @Column(name = "head_image")
    private String headImage;//头像图片，用于人脸识别
    @Transient
    private boolean isSystemAdmin = false;//是否是系统管理员

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<Role>();//角色列表

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_unit", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "unit_id")})
    private List<Unit> units = new ArrayList<Unit>();//机构列表

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_group_user", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<Group> groups = new ArrayList<Group>();//用户组列表
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserQuickMenu> quickMenus = new ArrayList<>();//快捷菜单列表
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserConfig> configs = new ArrayList<>();//用户配置项

    @Transient
    private List<Resource> resources = new ArrayList<Resource>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPageStyle() {
        return pageStyle;
    }

    public void setPageStyle(String pageStyle) {
        this.pageStyle = pageStyle;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public int getLastLoginType() {
        return lastLoginType;
    }

    public void setLastLoginType(int lastLoginType) {
        this.lastLoginType = lastLoginType;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public boolean isSystemAdmin() {
        return isSystemAdmin;
    }

    public void setSystemAdmin(boolean isSystemAdmin) {
        this.isSystemAdmin = isSystemAdmin;
    }

    public String toString() {
        return this.userName + ":" + this.realName;
    }

    /**
     * 获取默认机构
     *
     * @return
     */
    public Unit getDefaultUnit() {
        Unit defaultUnit = null;
        for(Unit u : this.getUnits()){
            if(u.isEnabled()){
                defaultUnit = u;
                break;
            }
        }
        return defaultUnit;
    }

    /**
     * 是否属于某个机构
     *
     * @param unitId 机构ID
     * @return
     */
    public boolean hasUnit(String unitId) {
        for (Unit unit : this.units) {
            if (unit.getId().equals(unitId))
                return true;
        }
        return false;
    }

    public List<UserQuickMenu> getQuickMenus() {
        return quickMenus;
    }

    public void setQuickMenus(List<UserQuickMenu> quickMenus) {
        this.quickMenus = quickMenus;
    }

    public List<UserConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<UserConfig> configs) {
        this.configs = configs;
    }
}
