package com.jrelax.web.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="sys_version")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)// 开启二级缓存
public class Version implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2936194616764169091L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column
	private String version;
	@Column
	private String build;
	@Column(name = "update_time")
	private String updateTime;
	@Column(name = "update_server")
	private String updateServer;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getBuild() {
		return build;
	}
	public void setBuild(String build) {
		this.build = build;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateServer() {
		return updateServer;
	}
	public void setUpdateServer(String updateServer) {
		this.updateServer = updateServer;
	}
}
