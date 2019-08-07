package com.xl.cloud.phoneBean;

public class MailBox {

	//机主邮箱昵称
	private String emailNick;
	
	//机主邮箱密码
	private String emailPassword;
	
	//邮箱地址
	private String email;
	
	//联系人名称
	private String name;
	
	//md5
	private String md5;
	
	//收件人
	private String acceptEmail;
	
	//发件人
	private String sendEmail;
	
	//主题
	private String emailTheme;
	
	//发送时间
	private String sendTime;
	
	//邮件状态(已读/未读)
	private String isRead;
	
	
	
	public String getEmailNick() {
		return emailNick;
	}

	public void setEmailNick(String emailNick) {
		this.emailNick = emailNick;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	

	public String getAcceptEmail() {
		return acceptEmail;
	}

	public void setAcceptEmail(String acceptEmail) {
		this.acceptEmail = acceptEmail;
	}

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public String getEmailTheme() {
		return emailTheme;
	}

	public void setEmailTheme(String emailTheme) {
		this.emailTheme = emailTheme;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	@Override
	public String toString() {
		return "MailBox [emailNick=" + emailNick + ", emailPassword="
				+ emailPassword + ", email=" + email + ", name=" + name
				+ ", md5=" + md5 + ", acceptEmail=" + acceptEmail
				+ ", sendEmail=" + sendEmail + ", emailTheme=" + emailTheme
				+ ", sendTime=" + sendTime + ", isRead=" + isRead + "]";
	}

}
