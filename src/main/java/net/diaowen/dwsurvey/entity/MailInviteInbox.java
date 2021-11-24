package net.diaowen.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.diaowen.common.base.entity.IdEntity;

/**
 * 答案 是非题结果保存表
 *
 * @author keyuan
 * @date 2012-10-21下午9:26:10
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_mail_invite_inbox")
public class MailInviteInbox extends IdEntity{
    
    private String surveyMailInviteId;
    private String userId;
    
    private String usContactsId;
    private String email;
    private String name;
    
    //sendclound返回的任务id
    private String sendcloudId;
    //0未发送 1已提交 2请求＝投递 3发送 4打开 5点击 
    //100发送失败
    //201取消订阅 202软退信 203垃圾举报 204无效邮件
    private Integer status=0;
    
    public String getSurveyMailInviteId() {
        return surveyMailInviteId;
    }
    public void setSurveyMailInviteId(String surveyMailInviteId) {
        this.surveyMailInviteId = surveyMailInviteId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUsContactsId() {
        return usContactsId;
    }
    public void setUsContactsId(String usContactsId) {
        this.usContactsId = usContactsId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
	public String getSendcloudId() {
		return sendcloudId;
	}
	public void setSendcloudId(String sendcloudId) {
		this.sendcloudId = sendcloudId;
	}
	
    
}
