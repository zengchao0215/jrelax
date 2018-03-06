package com.jrelax.web.system.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @author nethsoft
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "sys_ip_address")
@DynamicUpdate(true)
public class IpAddress implements Serializable {
    private static final long serialVersionUID = 3509941384851901401L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id; //ID
    @Column(name = "start_ip")
    private String startIp; //起始IP
    @Column(name = "end_ip")
    private String endIp; //截至IP
    @Column
    private String country; //国家地区
    @Column
    private String local; //位置

    /**
    * 获取 ID
    */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置 ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
    * 获取 起始IP
    */
    public String getStartIp() {
        return this.startIp;
    }

    /**
     * 设置 起始IP
     */
    public void setStartIp(String startIp) {
        this.startIp = startIp;
    }

    /**
    * 获取 截至IP
    */
    public String getEndIp() {
        return this.endIp;
    }

    /**
     * 设置 截至IP
     */
    public void setEndIp(String endIp) {
        this.endIp = endIp;
    }

    /**
    * 获取 国家地区
    */
    public String getCountry() {
        return this.country;
    }

    /**
     * 设置 国家地区
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
    * 获取 位置
    */
    public String getLocal() {
        return this.local;
    }

    /**
     * 设置 位置
     */
    public void setLocal(String local) {
        this.local = local;
    }
}
