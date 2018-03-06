package com.jrelax.web.system.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author nethsoft
 * @version 1.0
 * @since 1.0
 */

@Entity
@Table(name = "sys_user_email")
@DynamicUpdate(true)
public class UserEmail implements Serializable{
	
	private static final long serialVersionUID = 3509941384851901401L;
	
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "mail_type")
	private String mailType;
	@Column(name = "mail_host")
	private String mailHost;
	@Column(name = "mail_port")
	private Integer mailPort;
	@Column(name = "ssl_enable")
	private Boolean sslEnable = false;
	@Column(name = "mail_user")
	private String mailUser;
	@Column(name = "mail_pass")
	private String mailPass;
	
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getUserId(){
		return this.userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	public String getMailType(){
		return this.mailType;
	}
	public void setMailType(String mailType){
		this.mailType = mailType;
	}
	public String getMailHost(){
		return this.mailHost;
	}
	public void setMailHost(String mailHost){
		this.mailHost = mailHost;
	}
	public Integer getMailPort(){
		return this.mailPort;
	}
	public void setMailPort(Integer mailPort){
		this.mailPort = mailPort;
	}
	public Boolean getSslEnable(){
		return this.sslEnable;
	}
	public void setSslEnable(Boolean sslEnable){
		this.sslEnable = sslEnable;
	}
	public String getMailUser(){
		return this.mailUser;
	}
	public void setMailUser(String mailUser){
		this.mailUser = mailUser;
	}
	public String getMailPass(){
		return this.mailPass;
	}
	public void setMailPass(String mailPass){
		this.mailPass = mailPass;
	}
}
