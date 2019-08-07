package com.xl.cloud.bean;

public class EmailDTO {
	private String esID;
	private String subject;
	private String fromWho;
	private String toWho;
	private String downloadUrl;
	private String date;
	private String content;
	private String favoriteUser;  //收藏人
	private String favoriteTime;  //收藏时间
	private String emailType;     //邮件类型
	private String caseNameEml;
	private String attachmentname; //附件
	private String caseName;
	private String caseID;
	private String ip;
	private String multiarea;
	
	public String getMultiarea() {
		return multiarea;
	}
	public void setMultiarea(String multiarea) {
		this.multiarea = multiarea;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	private int caseid;
	private int read;
	private int star;
	
	public String getEsID() {
		return esID;
	}
	public void setEsID(String esID) {
		this.esID = esID;
	}
	
	public String getAttachmentname() {
		return attachmentname;
	}
	public void setAttachmentname(String attachmentname) {
		this.attachmentname = attachmentname;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getFavoriteUser() {
		return favoriteUser;
	}
	public void setFavoriteUser(String favoriteUser) {
		this.favoriteUser = favoriteUser;
	}
	public String getFavoriteTime() {
		return favoriteTime;
	}
	public void setFavoriteTime(String favoriteTime) {
		this.favoriteTime = favoriteTime;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	public String getCaseNameEml() {
		return caseNameEml;
	}
	public void setCaseNameEml(String caseNameEml) {
		this.caseNameEml = caseNameEml;
	}
	public int getCaseid() {
		return caseid;
	}
	public void setCaseid(int caseid) {
		this.caseid = caseid;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	
}