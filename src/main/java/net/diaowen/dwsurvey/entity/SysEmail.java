package net.diaowen.dwsurvey.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.diaowen.common.base.entity.IdEntity;
/**
 * 系统邮件
 * @author keyuan
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_sys_email")
public class SysEmail extends IdEntity{

	//STMP服务器
	private String stmpServer;
	//POST
	private String post;
	//是否验证
	private Integer isCheck=1;
	//发送邮箱
	private String sendEmail;
	//邮箱用户
	private String stmpUser;
	//邮箱密码
	private String stmpPwd;
	//
	private Date createDate=new Date();
	//state
	private Integer state;
	
	public String getStmpServer() {
		return stmpServer;
	}

	public void setStmpServer(String stmpServer) {
		this.stmpServer = stmpServer;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	public String getStmpUser() {
		return stmpUser;
	}

	public void setStmpUser(String stmpUser) {
		this.stmpUser = stmpUser;
	}

	public String getStmpPwd() {
		return stmpPwd;
	}

	public void setStmpPwd(String stmpPwd) {
		this.stmpPwd = stmpPwd;
	}

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}
