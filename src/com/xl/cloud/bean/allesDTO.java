package com.xl.cloud.bean;

public class allesDTO {
	//type
	private String _type;
	private String caseID;
	//邮件
	private String subject;

	private String fromWho; 
	private String toWho;
	private String filedownloadurl; 
	private String date;
	private String content;
	private String attachmentname;

	//黑客数据库
	private String email;  
	private String dataSource;


	/**qq 信息*/
	private String emailPasswd;
	private String  nick;//昵称
	private String qqPasswd;
	private String qqNum;
	private String  gender;//性别 0:男,1:女
	private String  qunNum;//qq群号
	private String  createDate;//群创建日期
	private String  title;//群标题
	private String  qunText;//群描述  
	
	/** 京东 信息*/
	private String ID;
	private String telephone;
	private String realName;
	
	
	//文档
	private String esDocId;
	private String docType;
	private int fileSize;
	private String fileName;

	private String editDate;
	private String caseName;
	private String file_download_url;
	
	//图片
		private String picDirpath;
		private String picdesc;
		private String picname;
		
		public String getPicDirpath() {
			return picDirpath;
		}
		public void setPicDirpath(String picDirpath) {
			this.picDirpath = picDirpath;
		}
		public String getPicdesc() {
			return picdesc;
		}
		public void setPicdesc(String picdesc) {
			this.picdesc = picdesc;
		}
		public String getPicname() {
			return picname;
		}
		public void setPicname(String picname) {
			this.picname = picname;
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
	public String getFiledownloadurl() {
		return filedownloadurl;
	}
	public void setFiledownloadurl(String filedownloadurl) {
		this.filedownloadurl = filedownloadurl;
	}
	
	public String getAttachmentname() {
		return attachmentname;
	}
	public void setAttachmentname(String attachmentname) {
		this.attachmentname = attachmentname;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	
	public String getEsDocId() {
		return esDocId;
	}
	public void setEsDocId(String esDocId) {
		this.esDocId = esDocId;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	public String getEditDate() {
		return editDate;
	}
	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getEmailPasswd() {
		return emailPasswd;
	}
	public void setEmailPasswd(String emailPasswd) {
		this.emailPasswd = emailPasswd;
	}
	public String getQqPasswd() {
		return qqPasswd;
	}
	public void setQqPasswd(String qqPasswd) {
		this.qqPasswd = qqPasswd;
	}
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public String getFile_download_url() {
		return file_download_url;
	}
	public void setFile_download_url(String file_download_url) {
		this.file_download_url = file_download_url;
	}

	public String get_type() {
		return _type;
	}
	public void set_type(String _type) {
		this._type = _type;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getQunNum() {
		return qunNum;
	}
	public void setQunNum(String qunNum) {
		this.qunNum = qunNum;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQunText() {
		return qunText;
	}
	public void setQunText(String qunText) {
		this.qunText = qunText;
	}
	
	/** 京东 信息*/
	public void setID(String ID){
		this.ID=ID;
	}
	public String getID(){
		return this.ID;
	}
	public void setTelephone(String telephone){
		this.telephone=telephone;
	}
	public String getTelephone(){
		return this.telephone;
	}
	public void setRealName(String realName){
		this.realName=realName;
	}
	public String getRealName(){
		return this.realName;
	}
}
