package com.xl.cloud.bean;

public class FixedDetail {
	private int id =-1;
	private String fixednum;//固话号
	private String fixedCity;//所属地
	private String fixedProvince;//省份
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
	public String getFixednum() {
		return fixednum;
	}
	public void setFixednum(String fixednum) {
		this.fixednum = fixednum;
	}
	public String getFixedCity() {
		return fixedCity;
	}
	public void setFixedCity(String fixedCity) {
		this.fixedCity = fixedCity;
	}
	public String getFixedProvince() {
		return fixedProvince;
	}
	public void setFixedProvince(String fixedProvince) {
		this.fixedProvince = fixedProvince;
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
		return "FixedDetail [id=" + id + ", fixednum=" + fixednum + ", fixedCity=" + fixedCity + ", fixedProvince="
				+ fixedProvince + ", count=" + count + ", caseId=" + caseId + "]";
	}

	

}
