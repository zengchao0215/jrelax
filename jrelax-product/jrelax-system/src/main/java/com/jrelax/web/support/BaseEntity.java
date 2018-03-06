package com.jrelax.web.support;

import com.jrelax.core.web.model.Model;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 实体基类
 * Created by zengchao on 2016-11-29.
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable, Model {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
