package com.jrelax.web.system.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="sys_sms")
@DynamicUpdate(true)
public class SMS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5698300132002932391L;
	
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	@Column
	private String content;//短信内容

	@Column
	private String receiver;//接受者
	@Column(name = "send_time")
	private String sendTime;//发送时间
	@Column
	private int state;//状态
	@Column
	private String remarks;//备注
	@Column
	private int weight;//权重
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="create_user")
	private User createUser;//创建人
	@Column(name = "create_time")
	private String createTime;//创建时间

	public SMS(){}
	public SMS(String receiver, String content){
		this.receiver = receiver;
		this.content = content;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public User getCreateUser() {
		return createUser;
	}
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
