package com.xl.cloud.bean;

public class EmailNodeDTO2 {
	
	private String name; //姓名/邮箱
	private String caseName;//案件名称
	private int isSuspect;//是否是嫌疑人
	private int category;//案件类别
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public int getIsSuspect() {
		return isSuspect;
	}
	public void setIsSuspect(int isSuspect) {
		this.isSuspect = isSuspect;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
	
	
}
