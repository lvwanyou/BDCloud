package com.xl.cloud.phoneBean;

//短信息
public class ShortMessage {
	//发件人
	private String sender;
	
	//收件人
	private String addressee;
	
	//发送时间
	private String sendTime;
	
	//接收时间
	private String acceptTime;
	
	//发送内容
	private String sendContent;
	
	//接收内容
	private String acceptContent;
	
	//原始/删除
	private String isDelete;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public String getAcceptContent() {
		return acceptContent;
	}

	public void setAcceptContent(String acceptContent) {
		this.acceptContent = acceptContent;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "ShortMessage [sender=" + sender + ", addressee=" + addressee
				+ ", sendTime=" + sendTime + ", acceptTime=" + acceptTime
				+ ", sendContent=" + sendContent + ", acceptContent="
				+ acceptContent + ", isDelete=" + isDelete + "]";
	}
	
	
}
