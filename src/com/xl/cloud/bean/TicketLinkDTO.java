package com.xl.cloud.bean;

public class TicketLinkDTO {
	private String localNum;
	private String dialNumber;
	private String dialName;	//对方名字
	private String position;	//地址ID
	private String startTime;
	private String callDuration;
	private String method;
	private String positionName;	//详细地址名
	private String positionName_sim;	//简化地址名
	private int number;
	private String lat;  //经度
	private String lon;  //纬度
	
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getPositionName_sim() {
		return positionName_sim;
	}
	public void setPositionName_sim(String positionName_sim) {
		this.positionName_sim = positionName_sim;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getCallDuration() {
		return callDuration;
	}
	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	


	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDialName() {
		return dialName;
	}
	public void setDialName(String dialName) {
		this.dialName = dialName;
	}
	public String getLocalNum() {
		return localNum;
	}
	public void setLocalNum(String localNum) {
		this.localNum = localNum;
	}
	public String getDialNumber() {
		return dialNumber;
	}
	public void setDialNumber(String dialNumber) {
		this.dialNumber = dialNumber;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
