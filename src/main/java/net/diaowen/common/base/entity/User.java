package net.diaowen.common.base.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


import net.diaowen.common.plugs.mapper.CollectionMapper;

/**
 *
 * @author KeYuan
 * @date 2013下午10:02:00
 *
 */
@Entity
@Table(name = "t_user")
public class User extends IdEntity {
	private String loginName;
	private String shaPassword;
	//用户名
	private String name;//
	//邮箱
	private String email;
	//出生年月
	private Date birthday;
	//最高学历
	private Integer eduQuali;
	//性别
	private Integer sex;
	//
	private String avatar;

	//2激活 1未激活 0不可用
	private Integer status=1;// 账号状态
	private Integer version = 1;//1 默认 2测试
	private Date createTime = new Date();
	private String createBy = "";
	private Date lastLoginTime;
	private String cellphone;

	//激活账号CODE
	private String activationCode;
	//找回密码code   ""或null表示没有激活找回密码功能
	private String findPwdCode;
	//找回密码最后期限  默认设置一天之内
	private Date findPwdLastDate;

	//加点盐
	private String salt;
	// 是否显示 0不显示
	private Integer visibility = 1;

//	private Date tokenTime;
	private String shaPasswordTemp;

	private String wxOpenId;
	private String sessionId;

	// Hibernate自动维护的Version字段
	// @Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(nullable = false, unique = true)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getShaPassword() {
		return shaPassword;
	}

	public void setShaPassword(String shaPassword) {
		this.shaPassword = shaPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}


	/*public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}*/

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getEduQuali() {
		return eduQuali;
	}

	public void setEduQuali(Integer eduQuali) {
		this.eduQuali = eduQuali;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getFindPwdCode() {
		return findPwdCode;
	}

	public void setFindPwdCode(String findPwdCode) {
		this.findPwdCode = findPwdCode;
	}

	public Date getFindPwdLastDate() {
		return findPwdLastDate;
	}

	public void setFindPwdLastDate(Date findPwdLastDate) {
		this.findPwdLastDate = findPwdLastDate;
	}

	public Integer getVisibility() {
		return visibility;
	}

	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getShaPasswordTemp() {
		return shaPasswordTemp;
	}

	public void setShaPasswordTemp(String shaPasswordTemp) {
		this.shaPasswordTemp = shaPasswordTemp;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	private String plainPassword;
	@Transient
	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	private String pwd;

	@Transient
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	private String roleId;

	private String findPwdUrl="";
	@Transient
	public String getFindPwdUrl() {
		return findPwdUrl;
	}

	public void setFindPwdUrl(String findPwdUrl) {
		this.findPwdUrl = findPwdUrl;
	}

	private String wwwooo;

	public String getWwwooo() {
		return wwwooo;
	}

	public void setWwwooo(String wwwooo) {
		this.wwwooo = wwwooo;
	}


}
