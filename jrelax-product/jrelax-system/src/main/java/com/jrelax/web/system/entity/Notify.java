package com.jrelax.web.system.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 通知
 *
 * @author zengchao
 */
@Entity
@Table(name = "sys_notify")
@DynamicUpdate(true)
public class Notify implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5688076092018314963L;
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;
    @Column
    private String type;
    @Column
    private String content;
    @Column(name = "create_user")
    private String createUser;//创建人
    @Column(name = "create_time")
    private String createTime;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
