package net.diaowen.dwsurvey.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.diaowen.common.base.entity.IdEntity;

/**
 * 问卷填写邀请的邮件服务，属性包含邮件服务所涉及的相关信息
 *
 * <p>
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name = "t_survey_mail_invite")
public class SurveyMailInvite extends IdEntity {
    /**
     * 该邮件邀请所对应的问卷ID
     */
    private String surveyId;
    /**
     * 用户的ID
     */
    private String userId;
    /**
     * 邮件的主题
     */
    private String subject;
    /**
     * 邀请对方填写的问卷的名称
     */
    private String dwSurveyName;
    /**
     * 邀请对方填写的问卷的填写地址
     */
    private String dwSurveyLink;
    /**
     * 发件人名称
     */
    private String dwSendUserName;
    /**
     * 发件人的邮箱
     */
    private String dwSendUserMail;
    /**
     * 创建的日期
     */
    private Date createDate = new Date();
    /**
     * 邮件信息的ID
     */
    private String sendcloudMsgId;
    /**
     * 审核状态, 0 表示未审核, 1 表示审核通过
     */
    private Integer audit;
    /**
     * 邮件的发送状态, 0 表示未发送, 1 表示正在发送, 2 表示发送完成, 3 表示发送失败, 4 表示发送异常
     */
    private Integer status = 0;
    /**
     * 发送出错的错误信息
     */
    private String errorMsg;
    /**
     * 该邮件消息的收件总人数
     */
    private Integer inboxNum;
    /**
     * 已发送的邮件数
     */
    private Integer sendNum;
    /**
     * 已发送的邮件中已成功的邮件数
     */
    private Integer successNum;
    /**
     * 已发送的邮件中失败的邮件数
     */
    private Integer failNum;

    /**
     * surveyId属性的Get方法
     *
     * @return surveyId
     */
    public String getSurveyId() {
        return surveyId;
    }

    /**
     * surveyId属性的Set方法
     *
     * @param surveyId surveyId
     */
    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * userId属性的Get方法
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * userId属性的Set方法
     *
     * @param userId userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * dwSurveyName属性的Get方法
     *
     * @return dwSurveyName
     */
    public String getDwSurveyName() {
        return dwSurveyName;
    }

    /**
     * dwSurveyName属性的Set方法
     *
     * @param dwSurveyName dwSurveyName
     */
    public void setDwSurveyName(String dwSurveyName) {
        this.dwSurveyName = dwSurveyName;
    }

    /**
     * dwSurveyLink属性的Get方法
     *
     * @return dwSurveyLink
     */
    public String getDwSurveyLink() {
        return dwSurveyLink;
    }

    /**
     * dwSurveyLink属性的Set方法
     *
     * @param dwSurveyLink dwSurveyLink
     */
    public void setDwSurveyLink(String dwSurveyLink) {
        this.dwSurveyLink = dwSurveyLink;
    }

    /**
     * dwSendUserName属性的Get方法
     *
     * @return dwSendUserName
     */
    public String getDwSendUserName() {
        return dwSendUserName;
    }

    /**
     * dwSendUserName属性的Set方法
     *
     * @param dwSendUserName dwSendUserName
     */
    public void setDwSendUserName(String dwSendUserName) {
        this.dwSendUserName = dwSendUserName;
    }

    /**
     * dwSendUserMail属性的Get方法
     *
     * @return dwSendUserMail
     */
    public String getDwSendUserMail() {
        return dwSendUserMail;
    }

    /**
     * dwSendUserMail属性的Set方法
     *
     * @param dwSendUserMail dwSendUserMail
     */
    public void setDwSendUserMail(String dwSendUserMail) {
        this.dwSendUserMail = dwSendUserMail;
    }

    /**
     * createDate属性的Get方法
     *
     * @return createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * createDate属性的Set方法
     *
     * @param createDate createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * audit属性的Get方法
     *
     * @return audit
     */
    public Integer getAudit() {
        return audit;
    }

    /**
     * audit属性的Set方法
     *
     * @param audit audit
     */
    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    /**
     * status属性的Get方法
     *
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * status属性的Set方法
     *
     * @param status status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * subject属性的Get方法
     *
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * subject属性的Set方法
     *
     * @param subject subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * inboxNum属性的Get方法
     *
     * @return inboxNum
     */
    public Integer getInboxNum() {
        return inboxNum;
    }

    /**
     * inboxNum属性的Set方法
     *
     * @param inboxNum inboxNum
     */
    public void setInboxNum(Integer inboxNum) {
        this.inboxNum = inboxNum;
    }

    /**
     * sendNum属性的Get方法
     *
     * @return sendNum
     */
    public Integer getSendNum() {
        return sendNum;
    }

    /**
     * sendNum属性的Set方法
     *
     * @param sendNum sendNum
     */
    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    /**
     * successNum属性的Get方法
     *
     * @return successNum
     */
    public Integer getSuccessNum() {
        return successNum;
    }

    /**
     * successNum属性的Set方法
     *
     * @param successNum successNum
     */
    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    /**
     * failNum属性的Get方法
     *
     * @return failNum
     */
    public Integer getFailNum() {
        return failNum;
    }

    /**
     * failNum属性的Set方法
     *
     * @param failNum failNum
     */
    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }

    /**
     * sendcloudMsgId属性的Get方法
     *
     * @return sendcloudMsgId
     */
    public String getSendcloudMsgId() {
        return sendcloudMsgId;
    }

    /**
     * sendcloudMsgId属性的Set方法
     *
     * @param sendcloudMsgId sendcloudMsgId
     */
    public void setSendcloudMsgId(String sendcloudMsgId) {
        this.sendcloudMsgId = sendcloudMsgId;
    }

    /**
     * errorMsg属性的Get方法
     *
     * @return errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * errorMsg属性的Set方法
     *
     * @param errorMsg errorMsg
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
