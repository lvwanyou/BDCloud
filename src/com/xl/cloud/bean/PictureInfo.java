package com.xl.cloud.bean;

import java.util.Date;

public class PictureInfo {

	private int id = -1;
	private int caseID = -1;// 所属案件id
	private String dirName;// 文件名称
	private Date dirDate;// 文件创建时间
	private String evUUID ;//数据的UUID
	private int imgStarFlag  = -1;
	public String getEvUUID() { 
		return evUUID;
	}
	public void setEvUUID(String evUUID) {
		this.evUUID = evUUID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCaseID() {
		return caseID;
	}
	public void setCaseID(int caseID) {
		this.caseID = caseID;
	}
	public String getDirName() {
		return dirName;
	}
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	public Date getDirDate() {
		return dirDate;
	}
	public void setDirDate(Date dirDate) {
		this.dirDate = dirDate;
	}
	public int getImgStarFlag() {
		return imgStarFlag;
	}
	public void setImgStarFlag(int imgStarFlag) {
		this.imgStarFlag = imgStarFlag;
	}

	

}
