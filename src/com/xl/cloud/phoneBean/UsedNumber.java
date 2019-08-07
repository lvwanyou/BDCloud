package com.xl.cloud.phoneBean;

//系统日志中 使用过的号码 页面对象
public class UsedNumber {
	//号码
	private String number;
	
	//ICCID
	private String iccid;
	
	//最后更新时间
	private String lastUpdateTime;
	
	//md5加密
	private String md5;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
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
	public String toString(){
		return "UsedNumber [number=" + number 
				+ ", iccid=" + iccid
				+ ", lastUpdateTime=" + lastUpdateTime
				+ ", md5=" + md5 + "]";
	}
}
