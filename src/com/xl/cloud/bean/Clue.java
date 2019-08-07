package com.xl.cloud.bean;

public class Clue {
	
	private int id=-1;
	private String clueName;//案件名称
	private String userName;//所属用户
	private String createdTime;//创建时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClueName() {
		return clueName;
	}
	public void setClueName(String clueName) {
		this.clueName = clueName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
}
