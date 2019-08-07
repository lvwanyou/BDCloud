package com.xl.cloud.bean;

public class WechatDetail {
	private int id =-1;
	private String wechatnum;//微信号
	private int count=-1;
	private String caseId;
	private String evid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWechatnum() {
		return wechatnum;
	}
	public void setWechatnum(String wechatnum) {
		this.wechatnum = wechatnum;
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
		return "WechatDetail [id=" + id + ", wechatnum=" + wechatnum + ", count=" + count + ", caseId=" + caseId
				+ ", evid=" + evid + "]";
	}
	
	

}
