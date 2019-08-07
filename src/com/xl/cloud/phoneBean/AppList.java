package com.xl.cloud.phoneBean;

//程序列表页面对象
public class AppList {
	//名称
	private String name;
	
	//包名
	private String packageName;
	
	//版本
	private String version;
	
	//类型
	private String type;
	
	//大小
	private String size;
	
	//更新日期
	private String updateDate;
	
	//安装路径
	private String installPath;
	
	//权限
	private String permission;
	
	//图标
	private String icon;
	
	//md5加密
	private String md5;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getInstallPath() {
		return installPath;
	}

	public void setInstallPath(String installPath) {
		this.installPath = installPath;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	@Override
	public String toString(){
		return "AppList [name=" + name 
				+ ", packageName=" + packageName
				+ ", version=" + version
				+ ", type=" + type
				+ ", size=" + size
				+ ", updateDate=" + updateDate
				+ ", installPath=" + installPath
				+ ", permission=" + permission
				+ ", icon=" + icon
				+ ", md5=" + md5 + "]";
	}
}
