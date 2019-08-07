package com.xl.cloud.phoneBean;

//应用程序地理位置页面对象
public class AppLocation {
	//应用名称
	private String appName;
	
	//用户ID
	private String userId;
	
	//用户名
	private String userName;
	
	//纬度
	private String latitude;
	
	//经度
	private String longitude;
	
	//地点
	private String location;
	
	//详细地址
	private String add;
	
	//时间
	private String time;
	
	//分享对象ID
	private String shareObjId;
	
	//来源账号名称
	private String srcAccId;
	
	//md5加密
	private String md5;
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getShareObjId() {
		return shareObjId;
	}

	public void setShareObjId(String shareObjId) {
		this.shareObjId = shareObjId;
	}

	public String getSrcAccId() {
		return srcAccId;
	}

	public void setSrcAccId(String srcAccId) {
		this.srcAccId = srcAccId;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Override
	public String toString(){
		return "AppLocation [appName=" + appName 
				+ ", userId=" + userId
				+ ", userName=" + userName
				+ ", latitude=" + latitude
				+ ", longitude=" + longitude
				+ ", location=" + location
				+ ", add=" + add
				+ ", time=" + time
				+ ", shareObjId=" + shareObjId
				+ ", srcAccId=" + srcAccId
				+ ", md5=" + md5 + "]";
	}
}
