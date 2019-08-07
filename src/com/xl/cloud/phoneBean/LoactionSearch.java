package com.xl.cloud.phoneBean;

//地图信息 腾讯地图 搜索位置页面对象
public class LoactionSearch {
	//位置ID
	private String addId;
	
	//时间
	private String time;
	
	//位置名称
	private String addname;
	
	//地址
	private String add;
	
	//经度
	private String longitude;
	
	//纬度
	private String latitude;
	
	//备注
	private String remarks;
	
	//md5加密
	private String md5;

	public String getAddId() {
		return addId;
	}

	public void setAddId(String addId) {
		this.addId = addId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	@Override
	public String toString(){
		return "LoactionSearch [addId=" + addId 
				+ ", time=" + time
				+ ", addname=" + addname
				+ ", add=" + add
				+ ", longitude=" + longitude
				+ ", latitude=" + latitude
				+ ", remarks=" + remarks
				+ ", md5=" + md5 + "]";
	}
	
}