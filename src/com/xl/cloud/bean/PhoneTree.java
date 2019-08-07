package com.xl.cloud.bean;

public class PhoneTree {
	private int id=-1;
	private String phone;//手机唯一标识
	private String name;//节点名称
	private String type;//节点对应estype
	private String parent;//父亲节点名称
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}

	
	
	

}
