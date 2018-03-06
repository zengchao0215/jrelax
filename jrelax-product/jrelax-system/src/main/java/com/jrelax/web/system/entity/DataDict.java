package com.jrelax.web.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="sys_datadict")
@DynamicUpdate(true)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)// 开启二级缓存
public class DataDict implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5529839468467380809L;
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	@Column
	private String category;
	@Column
	@NotNull
	private String name;
	@Column
	private String remarks;
	@Column(name = "create_user")
	private String createUser;
	@Column(name = "create_time")
	private String createTime;
	
	@OneToMany(mappedBy="dataDict", fetch=FetchType.LAZY , orphanRemoval=true)
	@OrderBy("location asc")
	private List<DataDictItem> items = new ArrayList<DataDictItem>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public List<DataDictItem> getItems() {
		return items;
	}
	public void setItems(List<DataDictItem> items) {
		this.items = items;
	}
}
