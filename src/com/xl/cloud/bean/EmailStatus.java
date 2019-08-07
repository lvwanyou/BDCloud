package com.xl.cloud.bean;

public class EmailStatus {
	private int id=-1;
	private int readflag=-1;
	private int star=-1;
	private String url;
	private int caseid=-1;
	private String favoriteUser;
	private String favoriteTime;  //收藏时间
	public int getCaseid() {
		return caseid;
	}
	public void setCaseid(int caseid) {
		this.caseid = caseid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public int getReadflag() {
		return readflag;
	}
	public void setReadflag(int readflag) {
		this.readflag = readflag;
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

	
	

}
