package com.xl.cloud.bean;

public class PlateDetail {
	private int id =-1;
	private String platenum;//车牌号
	private String plateType;//车牌类型
	private String plateCity;//车牌城市
	private int count=-1;
	private String caseId;
	private String evid;
	
	public String getEvid() {
		return evid;
	}
	public void setEvid(String evid) {
		this.evid = evid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlatenum() {
		return platenum;
	}
	public void setPlatenum(String platenum) {
		this.platenum = platenum;
	}
	public String getPlateType() {
		return plateType;
	}
	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}
	public String getPlateCity() {
		return plateCity;
	}
	public void setPlateCity(String plateCity) {
		this.plateCity = plateCity;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	@Override
	public String toString() {
		return "PlateDetail [id=" + id + ", platenum=" + platenum + ", plateType=" + plateType + ", plateCity="
				+ plateCity + ", count=" + count + ", caseId=" + caseId + "]";
	}
	

}
