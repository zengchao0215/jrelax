package com.jrelax.web.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="sys_datadict_item")
@DynamicUpdate(true)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)// 开启二级缓存
public class DataDictItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5529839468467380809L;
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "did")
	private DataDict dataDict;
	@Column
	@NotNull
	private String k;
	@Column
	private String v;
	@Column
	private int location;
	@Column(name = "create_user")
	private String createUser;
	@Column(name = "create_time")
	private String createTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DataDict getDataDict() {
		return dataDict;
	}
	public void setDataDict(DataDict dataDict) {
		this.dataDict = dataDict;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
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
