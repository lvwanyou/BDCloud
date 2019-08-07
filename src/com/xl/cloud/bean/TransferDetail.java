package com.xl.cloud.bean;

public class TransferDetail {
	private int id =-1;
	private String transfernum;//电汇
	private int count=-1;
	private String caseId;
	private String evid;
	
	public String getEvid() {
		return evid;
	}
	public void setEvid(String evid) {
		this.evid = evid;
	}
	@Override
	public String toString() {
		return "TransferDetail [id=" + id + ", transfernum=" + transfernum + ", count=" + count + ", caseId=" + caseId
				+ "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTransfernum() {
		return transfernum;
	}
	public void setTransfernum(String transfernum) {
		this.transfernum = transfernum;
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
	

}
