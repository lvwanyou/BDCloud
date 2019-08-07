package com.xl.cloud.bean;

public class CreditDetail {
	
	private int id =-1;
	private String creditnum;//信用证号
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

	public String getCreditnum() {
		return creditnum;
	}
	public void setCreditnum(String creditnum) {
		this.creditnum = creditnum;
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
		return "CreditDetail [id=" + id + ", creditnum=" + creditnum + ", count=" + count + ", caseId=" + caseId + "]";
	}


}
