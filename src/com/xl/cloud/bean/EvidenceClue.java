package com.xl.cloud.bean;

public class EvidenceClue {

	private int id = -1;
	private int involvePersonID = -1;// 所属线索id
	private String evName;// 证据名称
	private String comment;// 注释
	private String evAdmin;// 管理人
	private String dirPath;// 文件夹路径
	private String evSize;// 证据大小
	private int emlCount = -1;// Eml统计
	private int docCount = -1;// Doc统计
	private int pdfCount = -1;// Pdf统计
	private int zipCount = -1;// Zip统计
	private int rarCount = -1;// Rar统计
	private String percent;			// 处理进度
	private String finished;		// 导入完成
	private String status;			// 任务是否最终完成，完成后清除workboard计时器
	private String mailbox;			// mail
	private String mailBox;
	private String dealinfo;
	private String addTime;
	private String currFlag;
	private String finishTime;
	private int indexFlag=-1    ;	// 是否已建立索引
	private int uptype = -1;    	//上传类型
	private int isdel = -1;			//是否标记为删除
	private String startSolrTime;	//添加索引时间
	private String evType;			// 数据类型
	private int dataTypes=-1;		// 证据来源
	private String UUID;
	private String uploadNum;
	private String successNum;
	private String errorNum;
	private int spersonID = -1;
	private int sunitID = -1;


	public int getSpersonID() {
		return spersonID;
	}

	public void setSpersonID(int spersonID) {
		this.spersonID = spersonID;
	}

	public int getSunitID() {
		return sunitID;
	}

	public void setSunitID(int sunitID) {
		this.sunitID = sunitID;
	}

	public String getUploadNum() {
		return uploadNum;
	}

	public void setUploadNum(String uploadNum) {
		this.uploadNum = uploadNum;
	}

	public String getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(String successNum) {
		this.successNum = successNum;
	}

	public String getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(String errorNum) {
		this.errorNum = errorNum;
	}

	public int getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(int dataTypes) {
		this.dataTypes = dataTypes;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}


	public String getEvType() {
		return evType;
	}

	public void setEvType(String evType) {
		this.evType = evType;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public int getInvolvePersonID() {
		return involvePersonID;
	}

	public void setInvolvePersonID(int involvePersonID) {
		this.involvePersonID = involvePersonID;
	}

	public String getEvName() {
		return evName;
	}

	public void setEvName(String evName) {
		this.evName = evName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEvAdmin() {
		return evAdmin;
	}

	public void setEvAdmin(String evAdmin) {
		this.evAdmin = evAdmin;
	}

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public String getEvSize() {
		return evSize;
	}

	public void setEvSize(String evSize) {
		this.evSize = evSize;
	}

	public int getEmlCount() {
		return emlCount;
	}

	public void setEmlCount(int emlCount) {
		this.emlCount = emlCount;
	}

	public int getDocCount() {
		return docCount;
	}

	public void setDocCount(int docCount) {
		this.docCount = docCount;
	}

	public int getPdfCount() {
		return pdfCount;
	}

	public void setPdfCount(int pdfCount) {
		this.pdfCount = pdfCount;
	}

	public int getZipCount() {
		return zipCount;
	}

	public void setZipCount(int zipCount) {
		this.zipCount = zipCount;
	}

	public int getRarCount() {
		return rarCount;
	}

	public void setRarCount(int rarCount) {
		this.rarCount = rarCount;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getFinished() {
		return finished;
	}

	public void setFinished(String finished) {
		this.finished = finished;
	}

	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}

	public String getMailBox() {
		return mailBox;
	}

	public String getDealinfo() {
		return dealinfo;
	}

	public void setDealinfo(String dealinfo) {
		this.dealinfo = dealinfo;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getCurrFlag() {
		return currFlag;
	}

	public void setCurrFlag(String currFlag) {
		this.currFlag = currFlag;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public int getUptype() {
		return uptype;
	}

	public void setUptype(int uptype) {
		this.uptype = uptype;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}

	public String getStartSolrTime() {
		return startSolrTime;
	}

	public void setStartSolrTime(String startSolrTime) {
		this.startSolrTime = startSolrTime;
	}
	public int getIndexFlag() {
		return indexFlag;
	}

	public void setIndexFlag(int indexFlag) {
		this.indexFlag = indexFlag;
	}
}