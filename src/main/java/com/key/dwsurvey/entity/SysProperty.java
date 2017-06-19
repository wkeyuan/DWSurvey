package com.key.dwsurvey.entity;

public class SysProperty {
	
	//站点名称 
	private String siteName;
	//LOGO地址
	private String logoPath;
	//管理员邮箱
	private String adminEmail;
	//管理员QQ
	private String adminQQ;
	//管理员QQ
	private String adminTelephone;
	//网站备案信息代码
	private String icpCode;
	//网站开关
	private Integer state=1;
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAdminQQ() {
		return adminQQ;
	}
	public void setAdminQQ(String adminQQ) {
		this.adminQQ = adminQQ;
	}
	public String getIcpCode() {
		return icpCode;
	}
	public void setIcpCode(String icpCode) {
		this.icpCode = icpCode;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getAdminTelephone() {
		return adminTelephone;
	}
	public void setAdminTelephone(String adminTelephone) {
		this.adminTelephone = adminTelephone;
	}
	
}
