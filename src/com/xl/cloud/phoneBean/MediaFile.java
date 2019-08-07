package com.xl.cloud.phoneBean;

//手机内的媒体文件（图片 视频）页面对象
public class MediaFile {
	//保存路径 （对应于图片的缩略图  或 视频的本地保存路径）
	private String file="";
	
	//文件名
	private String fileName;
	
	//大小
	private String size;
	
	//修改时间
	private String modifiedTime;
	
	//创建时间
	private String createTime;
	
	//路径
	private String filePath;
	
	//是否删除
	private String deleteflag;
	
	//本机拍摄(图片)
	private String localShoot;
	
	//文件md5
	private String fileMd5;
	
	//自拍（图片）
	private String selfTimer;
	
	//md5加密
	private String md5;
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getLocalShoot() {
		return localShoot;
	}

	public void setLocalShoot(String localShoot) {
		this.localShoot = localShoot;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public String getSelfTimer() {
		return selfTimer;
	}

	public void setSelfTimer(String selfTimer) {
		this.selfTimer = selfTimer;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Override
	public String toString(){
		if(localShoot!=null){
			return "Picture [file=" + file 
					+ ", fileName=" + fileName
					+ ", size=" + size
					+ ", modifiedTime=" + modifiedTime
					+ ", createTime=" + createTime
					+ ", filePath=" + filePath
					+ ", deleteflag=" + deleteflag
					+ ", localShoot=" + localShoot
					+ ", fileMd5=" + fileMd5
					+ ", selfTimer=" + selfTimer
					+ ", md5=" + md5 + "]";
		}
		else {
			return "Video [file=" + file 
					+ ", fileName=" + fileName
					+ ", size=" + size
					+ ", modifiedTime=" + modifiedTime
					+ ", createTime=" + createTime
					+ ", filePath=" + filePath
					+ ", deleteflag=" + deleteflag
					+ ", fileMd5=" + fileMd5
					+ ", md5=" + md5 + "]";
		}
	}
}
