package com.xl.cloud.phoneBean;

//拍照位置信息页面对象
public class PhotoLocation {
	//名称
	private String name;
	
	//大小
	private String size;
	
	//类型
	private String type;
	
	//日期
	private String date;
	
	//路径
	private String path;
	
	//经度
	private String longitude;
	
	//纬度
	private String latitude;
	
	//md5加密
	private String md5;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	@Override
	public String toString(){
		return "PhotoLocation [name=" + name 
				+ ", size=" + size
				+ ", type=" + type
				+ ", date=" + date
				+ ", path=" + path
				+ ", longitude=" + longitude
				+ ", latitude=" + latitude
				+ ", md5=" + md5 + "]";
	}
}
