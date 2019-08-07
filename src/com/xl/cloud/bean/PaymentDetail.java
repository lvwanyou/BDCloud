package com.xl.cloud.bean;

public class PaymentDetail {
	private int id =-1;
	private String paymentnum;//支付宝号
	private int count=-1;
	private String caseId;
	private String evid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPaymentnum() {
		return paymentnum;
	}
	public void setPaymentnum(String paymentnum) {
		this.paymentnum = paymentnum;
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
		return "paymentDetail [id=" + id + ", paymentnum=" + paymentnum + ", count=" + count + ", caseId=" + caseId
				+ ", evid=" + evid + "]";
	}
	
	
}
