package com.key.dwsurvey.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.key.common.base.entity.IdEntity;

/**
 * 单个问卷的全局统计信息
 * @author KeYuan
 * @date 2013下午6:48:25
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_survey_stats")
public class SurveyStats extends IdEntity{
	
	//所属的surveyId
	private String surveyId;
	
	/**************************************************统计信息 ************************************************/
	/** 时间信息 **/
	//第一条回答数据时间
	private Date firstAnswer;
	//最后一条回答数据时间
	private Date lastAnswer;
	//回复样本总数  包括所有回复数据
	private Integer answerNum=0;
	//回复最少用时  秒
	private Integer anMinTime=0;
	//回复平均用时 秒
	private Integer anAvgTime=0;
	
	/** 数据--完成情况    是否全部题都回答  **/
	//未完成的数据
	private  Integer unCompleteNum=0; 
	//完成的数据 
	private Integer completeNum=0;
	
	/** 数据--有效情况   根据设计问卷时指定的必填项 **/
	//无效数据
	private Integer unEffectiveNum=0;
	//有效数据
	private Integer 	effectiveNum=0;
	
	/** 数据--审核情况  **/
	//未处理
	private Integer unHandleNum=0;
	//通过
	private Integer handlePassNum=0;
	//未通过
	private Integer handleUnPassNum=0;
	
	/** 不同来源数据 **/
	//网调数据
	private Integer onlineNum=0;
	//录入数据
	private Integer inputNum=0;
	//移动数据
	private Integer mobileNum=0;
	//导入数据
	private Integer importNum=0;
	
	//标识是否是最新数据  0不是 1是
	private Integer isNewData=0;
	
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public Date getFirstAnswer() {
		return firstAnswer;
	}
	public void setFirstAnswer(Date firstAnswer) {
		this.firstAnswer = firstAnswer;
	}
	public Date getLastAnswer() {
		return lastAnswer;
	}
	public void setLastAnswer(Date lastAnswer) {
		this.lastAnswer = lastAnswer;
	}
	public Integer getAnswerNum() {
		return answerNum;
	}
	public void setAnswerNum(Integer answerNum) {
		this.answerNum = answerNum;
	}
	public Integer getUnCompleteNum() {
		return unCompleteNum;
	}
	public void setUnCompleteNum(Integer unCompleteNum) {
		this.unCompleteNum = unCompleteNum;
	}
	public Integer getCompleteNum() {
		return completeNum;
	}
	public void setCompleteNum(Integer completeNum) {
		this.completeNum = completeNum;
	}
	public Integer getUnEffectiveNum() {
		return unEffectiveNum;
	}
	public void setUnEffectiveNum(Integer unEffectiveNum) {
		this.unEffectiveNum = unEffectiveNum;
	}
	public Integer getEffectiveNum() {
		return effectiveNum;
	}
	public void setEffectiveNum(Integer effectiveNum) {
		this.effectiveNum = effectiveNum;
	}
	public Integer getUnHandleNum() {
		return unHandleNum;
	}
	public void setUnHandleNum(Integer unHandleNum) {
		this.unHandleNum = unHandleNum;
	}
	public Integer getHandlePassNum() {
		return handlePassNum;
	}
	public void setHandlePassNum(Integer handlePassNum) {
		this.handlePassNum = handlePassNum;
	}
	public Integer getHandleUnPassNum() {
		return handleUnPassNum;
	}
	public void setHandleUnPassNum(Integer handleUnPassNum) {
		this.handleUnPassNum = handleUnPassNum;
	}
	public Integer getOnlineNum() {
		return onlineNum;
	}
	public void setOnlineNum(Integer onlineNum) {
		this.onlineNum = onlineNum;
	}
	public Integer getInputNum() {
		return inputNum;
	}
	public void setInputNum(Integer inputNum) {
		this.inputNum = inputNum;
	}
	public Integer getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(Integer mobileNum) {
		this.mobileNum = mobileNum;
	}
	public Integer getImportNum() {
		return importNum;
	}
	public void setImportNum(Integer importNum) {
		this.importNum = importNum;
	}
	public Integer getAnMinTime() {
		return anMinTime;
	}
	public void setAnMinTime(Integer anMinTime) {
		this.anMinTime = anMinTime;
	}
	public Integer getAnAvgTime() {
		return anAvgTime;
	}
	public void setAnAvgTime(Integer anAvgTime) {
		this.anAvgTime = anAvgTime;
	}
	public Integer getIsNewData() {
		return isNewData;
	}
	public void setIsNewData(Integer isNewData) {
		this.isNewData = isNewData;
	}
	
	
	private List<Question> questions=new ArrayList<Question>();
	@Transient
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	
}
