package com.xl.cloud.phoneBean;

public class CallLog {

	//用户名
	private String name;
	
	//用户电话
	private String telPhone;
	
	//呼叫状态(接入、拨出、未接到)
	private String callStatus;
	
	//呼叫时间
	private String callTime;
	
	//呼叫时长
	private String duration;
	
	//md5加密
	private String md5;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getCallStatus() {
		return callStatus;
	}
	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}
	public String getCallTime() {
		return callTime;
	}
	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	@Override
	public String toString() {
		return "CallLog [name=" + name + ", telPhone=" + telPhone
				+ ", callStatus=" + callStatus 
				+ ", callTime=" + callTime + ", duration=" + duration
				+ ", md5=" + md5 + "]";
	}
	
	
}
