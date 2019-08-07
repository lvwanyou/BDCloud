package com.xl.cloud.phoneBean;

public class Phone {

	//用户名
	private String name;
	
	//通讯录页面详细信息的url
	private String url;
	
	//用户手机号
	private String telphone;
	
	//通讯录手机号是否删除
	private String deleteFlag;
	
	//通讯录手机号创建时间
	private String createTime;
	
	//通讯录更新时间
	private String lastUpdateTime;
	
	//md5加密
	private String md5;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	@Override
	public String toString() {
		return "Phone [name=" + name + ", telphone="
				+ telphone + ", deleteFlag=" + deleteFlag + ", createTime="
				+ createTime + ", lastUpdateTime=" + lastUpdateTime + ", md5="
				+ md5 + "]";
	}
	
	
}
