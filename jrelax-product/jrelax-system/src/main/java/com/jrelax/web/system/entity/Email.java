package com.jrelax.web.system.entity;

import com.jrelax.kit.ObjectKit;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件
 * @author zengchao
 *
 */
@Entity
@Table(name="sys_email")
@DynamicUpdate(true)
public class Email implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1314679675997041363L;
	
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	@Column(name="recipients")
	private String _recipients;
	@Transient
	private List<String> recipients = new ArrayList<String>();//接收人列表
	@Column
	private String title;//主题
	@Column
	private String subTitle;//副标题
	@Column
	private String content;//正文
	@Column
	private String sendTime;//发送时间
	@Column
	private int state = 1;//状态
	@Column
	private String remarks;//备注
	@Column
	private int weight = 1;//权重
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="create_user")
	private User createUser;//创建人
	@Column(name = "create_time")
	private String createTime;//创建时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	protected String get_recipients() {
		return _recipients;
	}
	protected void set_recipients(String _recipients) {
		this._recipients = _recipients;
	}
	public List<String> getRecipients() {
		if(ObjectKit.isNotNull(_recipients)){
			String[] address = _recipients.split(",");
			for(String ad : address){
				recipients.add(ad);
			}
		}
		return recipients;
	}
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
		initForm();
	}
	/**
	 * 增加接受者
	 * @param address
	 */
	public void addRecipients(String address){
		this.recipients.add(address);
		initForm();
	}
	private void initForm(){
		_recipients = "";
		for(String recipient : recipients){
			_recipients += ","+recipient;
		}
		if(_recipients.length()>0)
			_recipients = _recipients.substring(1);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
