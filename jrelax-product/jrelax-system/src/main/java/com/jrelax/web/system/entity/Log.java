package com.jrelax.web.system.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.SimpleDateFormat;

@Entity
@Table(name="sys_log")
@DynamicUpdate(true)
public class Log implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3509941384851901401L;

	public static final String DEFAULT_USER = "Guest";
	public static final String DEFAULT_IP = "System";
	public static final String DEFAULT_CLASS = "LogService";
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	@Column
	private Integer level;
	@Column
	private String content;
	@Column
	@NotNull
	@NotEmpty
	@Length(min=2,max=50)
	private String user;
	@Column
	@Length(min=0,max=50)
	private String ip;
	@Column(name="log_time")
	@Length(min=0,max=50)
	private String time;
	@Column
	@Length(min=0,max=100)
	private String module;//模块名
	@Column
	@Length(min=0,max=50)
	private String source;//日志记录来源
	@Column
	private String browser;//浏览器
	@Column
	private String platform;//平台

	public String getTime() {
		return time;
	}
	public void setTime(java.util.Date time) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
		this.time = formater.format(time);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
}
