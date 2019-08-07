package com.xl.cloud.bean;

public class EmailNode2LinkDTO {
	
	private String fromWho; //发件人
	private String toWho; //收件人
	private int value;	//次数
	private String esID; //esID
	
	public String getEsID() {
		return esID;
	}
	public void setEsID(String esID) {
		this.esID = esID;
	}
	public String getFromWho() {
		return fromWho;
	}
	public void setFromWho(String fromWho) {
		this.fromWho = fromWho;
	}
	public String getToWho() {
		return toWho;
	}
	public void setToWho(String toWho) {
		this.toWho = toWho;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
	

	

	
}
