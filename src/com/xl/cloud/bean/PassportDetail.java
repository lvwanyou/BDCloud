package com.xl.cloud.bean;

public class PassportDetail {	
	private int id =-1;
	private String passportnum;//护照号
	private int count =-1;
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
	public String getPassportnum() {
		return passportnum;
	}
	public void setPassportnum(String passportnum) {
		this.passportnum = passportnum;
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
		return "PassportDetail [id=" + id + ", passportnum=" + passportnum + ", count=" + count + ", caseId=" + caseId
				+ "]";
	}
	

}
