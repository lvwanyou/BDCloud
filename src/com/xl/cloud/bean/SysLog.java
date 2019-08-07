package com.xl.cloud.bean;

public class SysLog {

	private int id = -1;
	private String content;//日志内容
	private String logTime;//产生时间
	private String username;//所属用户
	private String type;//日志类型	1.更新日志 2.操作日志 3.系统日志
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
