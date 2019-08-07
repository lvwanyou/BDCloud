package com.xl.cloud.phoneBean;

//浏览器Cookies页面对象
public class Cookies {
	//名称
	private String name;
	
	//修改时间
	private String modifiedDate;
	
	//最后访问时间
	private String lastTime;
	
	//文件名称
	private String fileName;
	
	//用户名
	private String userName;
	
	//点击次数
	private String clickTimes;
	
	//Cookies文件路径
	private String cookiesPath;
	
	//预览路径
	private String previewPath;
	
	//域名
	private String domainName;
	
	//路径
	private String path;
	
	//md5加密
	private String md5;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClickTimes() {
		return clickTimes;
	}

	public void setClickTimes(String clickTimes) {
		this.clickTimes = clickTimes;
	}

	public String getCookiesPath() {
		return cookiesPath;
	}

	public void setCookiesPath(String cookiesPath) {
		this.cookiesPath = cookiesPath;
	}

	public String getPreviewPath() {
		return previewPath;
	}

	public void setPreviewPath(String previewPath) {
		this.previewPath = previewPath;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	@Override
	public String toString(){
		return "Cookies [name=" + name 
				+ ", modifiedDate=" + modifiedDate
				+ ", lastTime=" + lastTime
				+ ", fileName=" + fileName
				+ ", userName=" + userName
				+ ", clickTimes=" + clickTimes
				+ ", cookiesPath=" + cookiesPath
				+ ", previewPath=" + previewPath
				+ ", domainName=" + domainName
				+ ", path=" + path
				+ ", md5=" + md5 + "]";
	}
}
