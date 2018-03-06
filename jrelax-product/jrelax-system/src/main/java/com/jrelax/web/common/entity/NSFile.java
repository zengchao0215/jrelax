package com.jrelax.web.common.entity;

public class NSFile {
	private String name;
	private String icon;
	private String path;
	private int type;
	private String size;
	private String modifyTime;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 1 文件 ，2 文件夹
	 * @return
	 */
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(long size){
		String sSize = size+"";
		if(size < 1024)
			sSize += "B";
		else if(size < 1024*1024)
			sSize = (size / 1024) + "KB";
		else if(size < 1024*1024*1024)
			sSize = (size/(1024 * 1024)) + "MB";
		else
			sSize = (size/(1024*1024*1024)) + "GB";
		
		this.size = sSize;
	}
	public void setSize(String size) {
		
		this.size = size;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
}
