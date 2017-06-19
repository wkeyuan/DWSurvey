package com.key.dwsurvey.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.key.common.base.entity.IdEntity;

/**
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_survey_mail_invite")
public class SurveyMailInvite extends IdEntity{
	//所对应的联系
	private String surveyId;
	private String userId;
	
	private String subject;
	//问卷名称
	private String dwSurveyName;
	//问卷答卷地址
	private String dwSurveyLink;
	//发件人名称 
	private String dwSendUserName;
	//发件人邮箱
	private String dwSendUserMail;
	
	private Date createDate=new Date();
	
	
	private String sendcloudMsgId;
	//审核 0未审核  1审核通过
	private Integer audit;
	//状态 0未发送 1正在发送 2发送完成 3发送失败  4发送异常
	private Integer status=0;
	private String errorMsg;
	
	//总收件人数
	private Integer inboxNum; 
	//已经发送的数
	private Integer sendNum;
	//发送中成功的数
	private Integer successNum;
	//发送中失败的数
	private Integer failNum;
	
	public String getSurveyId() {
	    return surveyId;
	}
	public void setSurveyId(String surveyId) {
	    this.surveyId = surveyId;
	}
	public String getUserId() {
	    return userId;
	}
	public void setUserId(String userId) {
	    this.userId = userId;
	}
	public String getDwSurveyName() {
	    return dwSurveyName;
	}
	public void setDwSurveyName(String dwSurveyName) {
	    this.dwSurveyName = dwSurveyName;
	}
	public String getDwSurveyLink() {
	    return dwSurveyLink;
	}
	public void setDwSurveyLink(String dwSurveyLink) {
	    this.dwSurveyLink = dwSurveyLink;
	}
	public String getDwSendUserName() {
	    return dwSendUserName;
	}
	public void setDwSendUserName(String dwSendUserName) {
	    this.dwSendUserName = dwSendUserName;
	}
	public String getDwSendUserMail() {
	    return dwSendUserMail;
	}
	public void setDwSendUserMail(String dwSendUserMail) {
	    this.dwSendUserMail = dwSendUserMail;
	}
	public Date getCreateDate() {
	    return createDate;
	}
	public void setCreateDate(Date createDate) {
	    this.createDate = createDate;
	}
	public Integer getAudit() {
	    return audit;
	}
	public void setAudit(Integer audit) {
	    this.audit = audit;
	}
	public Integer getStatus() {
	    return status;
	}
	public void setStatus(Integer status) {
	    this.status = status;
	}
	public String getSubject() {
	    return subject;
	}
	public void setSubject(String subject) {
	    this.subject = subject;
	}
	public Integer getInboxNum() {
	    return inboxNum;
	}
	public void setInboxNum(Integer inboxNum) {
	    this.inboxNum = inboxNum;
	}
	public Integer getSendNum() {
	    return sendNum;
	}
	public void setSendNum(Integer sendNum) {
	    this.sendNum = sendNum;
	}
	public Integer getSuccessNum() {
	    return successNum;
	}
	public void setSuccessNum(Integer successNum) {
	    this.successNum = successNum;
	}
	public Integer getFailNum() {
	    return failNum;
	}
	public void setFailNum(Integer failNum) {
	    this.failNum = failNum;
	}
	public String getSendcloudMsgId() {
		return sendcloudMsgId;
	}
	public void setSendcloudMsgId(String sendcloudMsgId) {
		this.sendcloudMsgId = sendcloudMsgId;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
