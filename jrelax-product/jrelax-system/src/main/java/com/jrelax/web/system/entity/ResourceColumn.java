package com.jrelax.web.system.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 资源对应的字段
 * Created by zengc on 2016-09-22.
 */
@Entity
@Table(name="sys_res_column")
@DynamicUpdate(true)
public class ResourceColumn implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 3362497855652065216L;

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="res_id")
    private Resource resource; //资源
    @Column
    @NotNull
    private String code; //编码
    @Column
    @NotNull
    private String name; //名称
    @Column(name = "create_user")
    @NotNull
    private String createUser; //创建人
    @Column(name = "create_time")
    @NotNull
    private String createTime; //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
