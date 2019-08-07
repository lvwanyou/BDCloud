package com.xl.cloud.bean;

public class RoleTable {
	private int id =-1;
	private String partterName;//角色名称
	private String createName;//创建人
	private String createTime;//创建时间
	private String partterDes;//描述
	private String sectionid ;//科室id
	private String departid ;//科室id
	private String partterShuju;
	private String partterShou;
	public int getId() {
		return id;
	}
	public String getPartterName() {
		return partterName;
	}
	public void setPartterName(String partterName) {
		this.partterName = partterName;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPartterDes() {
		return partterDes;
	}
	public void setPartterDes(String partterDes) {
		this.partterDes = partterDes;
	}
	public String getSectionid() {
		return sectionid;
	}
	public void setSectionid(String sectionid) {
		this.sectionid = sectionid;
	}
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid = departid;
	}
	public String getPartterShuju() {
		return partterShuju;
	}
	public void setPartterShuju(String partterShuju) {
		this.partterShuju = partterShuju;
	}
	public String getPartterShou() {
		return partterShou;
	}
	public void setPartterShou(String partterShou) {
		this.partterShou = partterShou;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "RoleTable [id=" + id + ", partterName=" + partterName + ", createName=" + createName + ", createTime=" + createTime + ", partterDes=" + partterDes + ", sectionid=" + sectionid
				+ ", departid=" + departid + ", partterShuju=" + partterShuju + ", partterShou=" + partterShou + "]";
	}
	
}
