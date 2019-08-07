package com.xl.cloud.bean;

public class Department {
	private int id = -1;
	private String departmentNum;
	private String departmentName;
	private String remarks;
	private String found;//创建人
	private String foundtime;//创建时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDepartmentNum() {
		return departmentNum;
	}
	public void setDepartmentNum(String departmentNum) {
		this.departmentNum = departmentNum;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFound() {
		return found;
	}
	public void setFound(String found) {
		this.found = found;
	}
	public String getFoundtime() {
		return foundtime;
	}
	public void setFoundtime(String foundtime) {
		this.foundtime = foundtime;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentNum=" + departmentNum + ", departmentName=" + departmentName
				+ ", remarks=" + remarks + ", found=" + found + ", foundtime=" + foundtime + "]";
	}
	

}
