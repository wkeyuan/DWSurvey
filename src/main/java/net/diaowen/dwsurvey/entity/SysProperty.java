package net.diaowen.dwsurvey.entity;

import net.diaowen.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 配置
 * @author keyuan
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_sys_property")
public class SysProperty extends IdEntity {

	//站点名称
	private String siteName;
	// 站点名称，对应的URL地址
	private String siteUrl;
	//网站备案信息代码
	private String siteIcp;
	//管理员邮箱
	private String siteMail;
	//管理员名称
	private String siteAdminName;
	private String sitePhone;
	//LOGO地址
	private String logoImageUrl;
	private boolean footerHide;
	private String footerContent = "Powered by DWSurvey";

	// 新版本代码
	private String newVersionInfo;
	// 所有新版本中最高更新级别
	private String maxUpLevel;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getSiteIcp() {
		return siteIcp;
	}

	public void setSiteIcp(String siteIcp) {
		this.siteIcp = siteIcp;
	}

	public String getSiteMail() {
		return siteMail;
	}

	public void setSiteMail(String siteMail) {
		this.siteMail = siteMail;
	}

	public String getSiteAdminName() {
		return siteAdminName;
	}

	public void setSiteAdminName(String siteAdminName) {
		this.siteAdminName = siteAdminName;
	}

	public String getSitePhone() {
		return sitePhone;
	}

	public void setSitePhone(String sitePhone) {
		this.sitePhone = sitePhone;
	}

	public String getLogoImageUrl() {
		return logoImageUrl;
	}

	public void setLogoImageUrl(String logoImageUrl) {
		this.logoImageUrl = logoImageUrl;
	}

	public boolean isFooterHide() {
		return footerHide;
	}

	public void setFooterHide(boolean footerHide) {
		this.footerHide = footerHide;
	}

	public String getFooterContent() {
		return footerContent;
	}

	public void setFooterContent(String footerContent) {
		this.footerContent = footerContent;
	}

	public String getNewVersionInfo() {
		return newVersionInfo;
	}

	public void setNewVersionInfo(String newVersionInfo) {
		this.newVersionInfo = newVersionInfo;
	}

	public String getMaxUpLevel() {
		return maxUpLevel;
	}

	public void setMaxUpLevel(String maxUpLevel) {
		this.maxUpLevel = maxUpLevel;
	}
}
