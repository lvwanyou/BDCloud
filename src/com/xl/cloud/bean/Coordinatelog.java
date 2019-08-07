package com.xl.cloud.bean;

public class Coordinatelog {

	private int id=-1;
	private String caseName;//案件名称
	private String caseNum;//案件编号
	private String caseType;//类型
	private String section; //科室
	private String department; //部门
	private String grantName;
	private String grantedName;
	private String grantedPri;
	private String grantedSec;
	private String grantedDep;
	private String operateTime;
	private String type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCaseNum() {
		return caseNum;
	}
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}

	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getGrantName() {
		return grantName;
	}
	public void setGrantName(String grantName) {
		this.grantName = grantName;
	}
	public String getGrantedName() {
		return grantedName;
	}
	public void setGrantedName(String grantedName) {
		this.grantedName = grantedName;
	}
	public String getGrantedPri() {
		return grantedPri;
	}
	public void setGrantedPri(String grantedPri) {
		this.grantedPri = grantedPri;
	}
	public String getGrantedSec() {
		return grantedSec;
	}
	public void setGrantedSec(String grantedSec) {
		this.grantedSec = grantedSec;
	}
	public String getGrantedDep() {
		return grantedDep;
	}
	public void setGrantedDep(String grantedDep) {
		this.grantedDep = grantedDep;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
