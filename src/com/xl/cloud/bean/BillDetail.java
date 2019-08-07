package com.xl.cloud.bean;

public class BillDetail {
	private int id =-1;
	private String billnum;//发票号
	private int count=-1;
	private String caseId;
	private String evid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBillnum() {
		return billnum;
	}
	public void setBillnum(String billnum) {
		this.billnum = billnum;
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
	public String getEvid() {
		return evid;
	}
	public void setEvid(String evid) {
		this.evid = evid;
	}
	@Override
	public String toString() {
		return "BillDetail [id=" + id + ", billnum=" + billnum + ", count=" + count + ", caseId=" + caseId + ", evid="
				+ evid + "]";
	}
	
}
