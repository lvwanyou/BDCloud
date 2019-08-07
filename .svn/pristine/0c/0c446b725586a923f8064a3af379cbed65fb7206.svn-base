package com.xl.cloud.phoneBean;

//短信 彩信 页面对象
public class Message {
	//通讯对象 (相对于本机)
	private String otherSide;
	
	//发送者
	private String sender;
	
	//类型(接受/发送   相对于本机)
	private String type;
	
	//时间
	private String time;
	
	//内容
	private String content;
	
	//图片(彩信)
	private String img="";

	public String getOtherSide() {
		return otherSide;
	}

	public void setOtherSide(String otherSide) {
		this.otherSide = otherSide;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString(){
		if(img==null){//短信输出
			return "SMS [otherSide=" + otherSide 
					+ ", sender=" + sender
					+ ", type=" + type
					+ ", content=" + content
					+ ", time=" + time + "]";
		}else {//彩信输出
			return "MMS [otherSide=" + otherSide 
					+ ", sender=" + sender
					+ ", type=" + type
					+ ", content=" + content
					+ ", img=" + img
					+ ", time=" + time + "]";
		}
	}

}
