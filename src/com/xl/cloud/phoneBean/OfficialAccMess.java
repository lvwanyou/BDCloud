package com.xl.cloud.phoneBean;

//微信公众号消息
public class OfficialAccMess {
	//公众号
	private String officialAcc;
	
	//标题
	private String title;
	
	//图片
	private String pic;
	
	//描述
	private String description;
	
	//链接
	private String url;
	
	//发布时间
	private String time;
	
	//md5加密
	private String md5;
	
	public String getOfficialAcc() {
		return officialAcc;
	}

	public void setOfficialAcc(String officialAcc) {
		this.officialAcc = officialAcc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Override
	public String toString(){
		return "WeChatMessage [officialAcc=" + officialAcc 
				+ ", title=" + title
				+ ", pic=" + pic
				+ ", description=" + description
				+ ", url=" + url
				+ ", time=" + time 
				+ ", md5=" + md5 + "]";
	}
}
