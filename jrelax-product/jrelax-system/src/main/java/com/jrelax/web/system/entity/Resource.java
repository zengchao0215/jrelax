package com.jrelax.web.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 *
 * @author zengchao
 */
@Entity
@Table(name = "sys_res")
@DynamicUpdate(true)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)// 开启二级缓存
public class Resource implements Serializable, com.jrelax.core.web.model.Resource {

    /**
     *
     */
    private static final long serialVersionUID = -3364043806717534304L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private String url;
    @Column
    private String icon;
    @Column
    private int location;
    @Column
    private boolean enabled;
    @Column
    private String descript;
    @Column(name = "has_children")
    private boolean hasChildren;
    @Column(name = "parent_id")
    private String parentId;
    @Column
    private boolean beta;//是否是新功能
    @Column
    private boolean display;//是否显示此菜单
    @Column(name = "new_window")
    private boolean newWindow;//是否新窗口打开
    @Column(name = "create_time")
    private String createTime;
    @ManyToMany(mappedBy = "resources", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<Role>();
    @OneToMany(mappedBy = "resource", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ResourceColumn> columns = new ArrayList<>();
    @OneToMany(mappedBy = "resource", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ResourceButton> buttons = new ArrayList<ResourceButton>();

    @Transient
    private List<ResourceColumn> privilegeColumns = new ArrayList<>();//有权限的字段
    @Transient
    private List<ResourceButton> privilegeButtons = new ArrayList<>();//有权限的按钮

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public boolean hasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isBeta() {
        return beta;
    }

    public void setBeta(boolean beta) {
        this.beta = beta;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isNewWindow() {
        return newWindow;
    }

    public void setNewWindow(boolean newWindow) {
        this.newWindow = newWindow;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<ResourceButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<ResourceButton> buttons) {
        this.buttons = buttons;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<ResourceColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<ResourceColumn> columns) {
        this.columns = columns;
    }

    public List<ResourceButton> getPrivilegeButtons() {
        return privilegeButtons;
    }

    public void setPrivilegeButtons(List<ResourceButton> privilegeButtons) {
        this.privilegeButtons = privilegeButtons;
    }

    public List<ResourceColumn> getPrivilegeColumns() {
        return privilegeColumns;
    }

    public void setPrivilegeColumns(List<ResourceColumn> privilegeColumns) {
        this.privilegeColumns = privilegeColumns;
    }
}
