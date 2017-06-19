package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;

/**
 * 系统smtp服务器
 * @author KeYuan
 * @date 2013下午10:35:52
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public class SmtpServer extends IdEntity{
	//SMTP服务器
	private String smtpServer;
	//端口
	private String post;
	//验证
	private Integer isChecked;
	//发信人邮件地址
	private String sendEmail;
	//SMTP 身份验证用户名
	private String smtpUserName;
	//SMTP 身份验证密码
	private String smtpPassword;
	public String getSmtpServer() {
		return smtpServer;
	}
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public Integer getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}
	public String getSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}
	public String getSmtpUserName() {
		return smtpUserName;
	}
	public void setSmtpUserName(String smtpUserName) {
		this.smtpUserName = smtpUserName;
	}
	public String getSmtpPassword() {
		return smtpPassword;
	}
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}
	
}
