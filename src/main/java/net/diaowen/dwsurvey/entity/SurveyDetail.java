package net.diaowen.dwsurvey.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.diaowen.common.base.entity.IdEntity;

/**
 * 问卷配置的详细信息
 *
 * @author keyuan
 * <p>
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name = "t_survey_detail")
public class SurveyDetail extends IdEntity {
    /**
     * 该问卷配置所对应的surveyDirectory（问卷或问卷目录）的ID
     */
    private String dirId;
    /**
     * 设置问卷有效性的限制, 1(默认) 表示不限制, 2 表示实验cookie技术限制, 3 采用来源IP检测限制, 4 限制每台电脑或手机只能答一次
     */
    private Integer effective = 1;
    /**
     * 设置问卷有效性的间隔时间
     */
    private Integer effectiveTime = 5;
    /**
     * 设置是否每个IP只能回答一次该问卷, 1 表示是, 0 表示否
     */
    private Integer effectiveIp = 0;
    /**
     * 设置防刷新是否启用, 1 表示启用, 0 表示不启用
     */
    private Integer refresh = 1;
    /**
     * 表示防刷新的次数,启用防刷新情况下,允许的最大刷新次数
     */
    private Integer refreshNum = 3;
    /**
     * 设置问卷调查的规则 —— 1 表示公开问卷， 2 表示私有问卷， 3 表示问卷需要通过令牌密码来访问
     */
    private Integer rule = 1;
    /**
     * 问卷的令牌密码,默认为"令牌"
     */
    private String ruleCode = "令牌";
    /**
     * 设置问卷的结束方式 —— 1 表示手动结束问卷收集, 2 表示根据收集的截止时间结束问卷收集, 3 表示根据收集到的份数结束问卷的收集
     */
    private Integer endType = 1;
    /**
     * 问卷的结束时间
     */
    private Date endTime;
    /**
     * 问卷截止收集所需收集到的问卷份数
     */
    private Integer endNum = 1000;
    /**
     * 问卷说明
     */
    private String surveyNote;
    /**
     * 是否根据收集到的问卷份数结束收集
     */
    private Integer ynEndNum = 0;
    /**
     * 是否根据设定的结束时间结束问卷的收集
     */
    private Integer ynEndTime = 0;
    /**
     * 问卷的题目总数
     */
    private Integer surveyQuNum = 0;
    /**
     * 至少需要回答的问卷题目数
     */
    private Integer anItemLeastNum = 0;
    /**
     * 最多可以回答的问卷题目数
     */
    private Integer anItemMostNum = 0;
    /**
     * 设置是否只有邮件邀请唯一链接的受访者可回答, 1 为启用, 0 为不启用
     */
    private Integer mailOnly = 0;
    /**
     * 问卷显示分享
     */
    private Integer showShareSurvey = 1;
    /**
     * 是否显示问卷填写记录信息
     */
    private Integer showAnswerDa = 0;

    /**
     * dirId属性的GET方法
     *
     * @return dirId
     */
    public String getDirId() {
        return dirId;
    }

    /**
     * dirId属性的Set方法
     *
     * @param dirId dirId
     */
    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    /**
     * surveyNote属性的GET方法
     *
     * @return surveyNote
     */
    public String getSurveyNote() {
        return surveyNote;
    }

    /**
     * surveyNote属性的Set方法
     *
     * @param surveyNote surveyNote
     */
    public void setSurveyNote(String surveyNote) {
        this.surveyNote = surveyNote;
    }

    /**
     * effective属性的GET方法
     *
     * @return effective
     */
    public Integer getEffective() {
        return effective;
    }

    /**
     * effective属性的Set方法
     *
     * @param effective effective
     */
    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    /**
     * effectiveTime属性的GET方法
     *
     * @return effectiveTime
     */
    public Integer getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * effectiveTime属性的Set方法
     *
     * @param effectiveTime effectiveTime
     */
    public void setEffectiveTime(Integer effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    /**
     * refresh属性的GET方法
     *
     * @return refresh
     */
    public Integer getRefresh() {
        return refresh;
    }

    /**
     * refresh属性的Set方法
     *
     * @param refresh refresh
     */
    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }

    /**
     * refreshNum属性的GET方法
     *
     * @return refreshNum refreshNum
     */
    public Integer getRefreshNum() {
        return refreshNum;
    }

    /**
     * refreshNum属性的Set方法
     *
     * @param refreshNum refreshNum
     */
    public void setRefreshNum(Integer refreshNum) {
        this.refreshNum = refreshNum;
    }

    /**
     * rule属性的GET方法
     *
     * @return rule
     */
    public Integer getRule() {
        return rule;
    }

    /**
     * rule属性的Set方法
     *
     * @param rule rule
     */
    public void setRule(Integer rule) {
        this.rule = rule;
    }

    /**
     * ruleCode属性的GET方法
     *
     * @return ruleCode
     */
    public String getRuleCode() {
        return ruleCode;
    }

    /**
     * ruleCode属性的Set方法
     *
     * @param ruleCode ruleCode
     */
    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    /**
     * endType属性的GET方法
     *
     * @return endType
     */
    public Integer getEndType() {
        return endType;
    }

    /**
     * endType属性的Set方法
     *
     * @param endType endType
     */
    public void setEndType(Integer endType) {
        this.endType = endType;
    }

    /**
     * endTime属性的GET方法
     *
     * @return endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * endTime属性的Set方法
     *
     * @param endTime endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * endNum属性的GET方法
     *
     * @return endNum
     */
    public Integer getEndNum() {
        return endNum;
    }

    /**
     * endNum属性的Set方法
     *
     * @param endNum endNum
     */
    public void setEndNum(Integer endNum) {
        this.endNum = endNum;
    }

    /**
     * anItemLeastNum属性的GET方法
     *
     * @return anItemLeastNum
     */
    public Integer getAnItemLeastNum() {
        return anItemLeastNum;
    }

    /**
     * anItemLeastNum属性的Set方法
     *
     * @param anItemLeastNum anItemLeastNum
     */
    public void setAnItemLeastNum(Integer anItemLeastNum) {
        this.anItemLeastNum = anItemLeastNum;
    }

    /**
     * anItemMostNum属性的GET方法
     *
     * @return anItemMostNum
     */
    public Integer getAnItemMostNum() {
        return anItemMostNum;
    }

    /**
     * anItemMostNum属性的Set方法
     *
     * @param anItemMostNum anItemMostNum
     */
    public void setAnItemMostNum(Integer anItemMostNum) {
        this.anItemMostNum = anItemMostNum;
    }

    /**
     * surveyQuNum属性的GET方法
     *
     * @return surveyQuNum
     */
    public Integer getSurveyQuNum() {
        return surveyQuNum;
    }

    /**
     * surveyQuNum属性的Set方法
     *
     * @param surveyQuNum surveyQuNum
     */
    public void setSurveyQuNum(Integer surveyQuNum) {
        this.surveyQuNum = surveyQuNum;
    }

    /**
     * mailOnly属性的GET方法
     *
     * @return mailOnly
     */
    public Integer getMailOnly() {
        return mailOnly;
    }

    /**
     * mailOnly属性的Set方法
     *
     * @param mailOnly mailOnly
     */
    public void setMailOnly(Integer mailOnly) {
        this.mailOnly = mailOnly;
    }

    /**
     * effectiveIp属性的GET方法
     *
     * @return effectiveIp
     */
    public Integer getEffectiveIp() {
        return effectiveIp;
    }

    /**
     * effectiveIp属性的Set方法
     *
     * @param effectiveIp effectiveIp
     */
    public void setEffectiveIp(Integer effectiveIp) {
        this.effectiveIp = effectiveIp;
    }

    /**
     * ynEndNum属性的GET方法
     *
     * @return ynEndNum
     */
    public Integer getYnEndNum() {
        return ynEndNum;
    }

    /**
     * ynEndNum属性的Set方法
     *
     * @param ynEndNum ynEndNum
     */
    public void setYnEndNum(Integer ynEndNum) {
        this.ynEndNum = ynEndNum;
    }

    /**
     * ynEndTime属性的GET方法
     *
     * @return ynEndTime
     */
    public Integer getYnEndTime() {
        return ynEndTime;
    }

    /**
     * ynEndTime属性的Set方法
     *
     * @param ynEndTime ynEndTime
     */
    public void setYnEndTime(Integer ynEndTime) {
        this.ynEndTime = ynEndTime;
    }

    /**
     * showShareSurvey属性的GET方法
     *
     * @return showShareSurvey
     */
    public Integer getShowShareSurvey() {
        return showShareSurvey;
    }

    /**
     * showShareSurvey属性的Set方法
     *
     * @param showShareSurvey showShareSurvey
     */
    public void setShowShareSurvey(Integer showShareSurvey) {
        this.showShareSurvey = showShareSurvey;
    }

    /**
     * showAnswerDa属性的GET方法
     *
     * @return showAnswerDa
     */
    public Integer getShowAnswerDa() {
        return showAnswerDa;
    }

    /**
     * showAnswerDa属性的Set方法
     *
     * @param showAnswerDa showAnswerDa
     */
    public void setShowAnswerDa(Integer showAnswerDa) {
        this.showAnswerDa = showAnswerDa;
    }

}
