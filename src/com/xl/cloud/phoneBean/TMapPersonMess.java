package com.xl.cloud.phoneBean;

//腾讯地图  个人信息对象
public class TMapPersonMess {
	
	//用户ID
	private String userId;
	
	//用户名
	private String userName;
	
	//昵称
	private String nickName;
	
	//生日
	private String birth;
	
	//性别
	private String sex;
	
	//头像
	private String avatar;
	
	//邮箱
	private String mail;
	
	//电话
	private String telephone;
	
	//所在地
	private String add;
	
	//签名
	private String signature;
	
	//新浪微博ID
	private String sinaId;
	
	//新浪微博昵称
	private String sinaName;
	
	//qq号码
	private String qq;
	
	//qq昵称
	private String qqName;
	
	//淘宝ID
	private String tbId;
	
	//淘宝昵称
	private String tbName;
	
	//联通WO+ID
	private String CUId;
	
	//最近更新时间
	private String updateTime;
	
	//md5加密
	private String md5;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSinaId() {
		return sinaId;
	}

	public void setSinaId(String sinaId) {
		this.sinaId = sinaId;
	}

	public String getSinaName() {
		return sinaName;
	}

	public void setSinaName(String sinaName) {
		this.sinaName = sinaName;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getQqName() {
		return qqName;
	}

	public void setQqName(String qqName) {
		this.qqName = qqName;
	}

	public String getTbId() {
		return tbId;
	}

	public void setTbId(String tbId) {
		this.tbId = tbId;
	}

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public String getCUId() {
		return CUId;
	}

	public void setCUId(String cUId) {
		CUId = cUId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	@Override
	public String toString(){
		return "TableInTable [userId=" + userId 
				+ ", userName=" + userName
				+ ", nickName=" + nickName
				+ ", birth=" + birth
				+ ", sex=" + sex
				+ ", avatar=" + avatar
				+ ", mail=" + mail
				+ ", telephone=" + telephone
				+ ", add=" + add
				+ ", signature=" + signature
				+ ", sinaId=" + sinaId
				+ ", sinaName=" + sinaName
				+ ", qq=" + qq
				+ ", qqName=" + qqName
				+ ", tbId=" + tbId
				+ ", tbName=" + tbName
				+ ", CUId=" + CUId
				+ ", updateTime=" + updateTime
				+ ", md5=" + md5 + "]";
	}
}