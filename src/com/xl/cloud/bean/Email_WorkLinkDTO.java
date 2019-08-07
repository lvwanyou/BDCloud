package com.xl.cloud.bean;

public class Email_WorkLinkDTO {
	private String formWho;
	private String emailNum;
	private String startTime;
	private String toWho;
	private String date;
	private String read;
	private String subject;

	
	public String getToWho() {
		return toWho;
	}
	public void setToWho(String toWho) {
		this.toWho = toWho;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFormWho() {
		return formWho;
	}
	public void setFormWho(String formWho) {
		this.formWho = formWho;
	}
	public String getEmailNum() {
		return emailNum;
	}
	public void setEmailNum(String emailNum) {
		this.emailNum = emailNum;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
}
