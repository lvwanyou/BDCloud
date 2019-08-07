package com.xl.cloud.phoneBean;

//浏览器书签页面对象
public class Bookmarks {
	//标题
	private String title;
	
	//网址
	private String url;
	
	//日期
	private String date;
	
	//md5
	private String md5;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	@Override
	public String toString(){
		return "Bookmarks [title=" + title 
				+ ", url=" + url
				+ ", date=" + date
				+ ", md5=" + md5 + "]";
	}
}
