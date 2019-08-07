package com.xl.cloud.phoneBean;

//支付宝陌生人、好友页面对象
public class AliContacts {
	//备注名称
	private String memoName;
	
	//昵称
	private String nickName;
	
	//全名
	private String fullName;
	
	//认证状态
	private String authStatus;
	
	//账号
	private String acc;
	
	//会员等级
	private String memberLevel;
	
	//地区
	private String area;
	
	//签名
	private String signature;
	
	//手机联系人
	private String contacts;
	
	//手机号
	private String telephone;
	
	//头像
	private String avatar;
	
	//md5
	private String md5;

	public String getMemoName() {
		return memoName;
	}

	public void setMemoName(String memoName) {
		this.memoName = memoName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	@Override
	public String toString(){
		return "AliContacts [memoName=" + memoName 
				+ ", nickName=" + nickName
				+ ", fullName=" + fullName
				+ ", authStatus=" + authStatus
				+ ", acc=" + acc
				+ ", memberLevel=" + memberLevel
				+ ", area=" + area
				+ ", signature=" + signature
				+ ", contacts=" + contacts
				+ ", telephone=" + telephone
				+ ", avatar=" + avatar
				+ ", md5=" + md5 + "]";
	}
}
