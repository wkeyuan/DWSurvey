package com.key.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.key.common.base.entity.IdEntity;

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
@Table(name = "t_an_yesno")
public class AnYesno extends IdEntity {

	// 所属问卷ID
	private String belongId;
	// 对应的答卷信息表
	private String belongAnswerId;
	// 问题ID
	private String quId;
	// 1 是 0非
	private String yesnoAnswer;
	
	private Integer visibility=1;
	
	public AnYesno(){
		
	}
	public AnYesno(String surveyId, String surveyAnswerId, String quId,
			String yesnoAnswer) {
		this.belongId=surveyId;
		this.belongAnswerId=surveyAnswerId;
		this.quId=quId;
		this.yesnoAnswer=yesnoAnswer;
	}
	public String getBelongId() {
		return belongId;
	}
	public void setBelongId(String belongId) {
		this.belongId = belongId;
	}
	public String getBelongAnswerId() {
		return belongAnswerId;
	}
	public void setBelongAnswerId(String belongAnswerId) {
		this.belongAnswerId = belongAnswerId;
	}
	public String getQuId() {
		return quId;
	}
	public void setQuId(String quId) {
		this.quId = quId;
	}
	public String getYesnoAnswer() {
		return yesnoAnswer;
	}
	public void setYesnoAnswer(String yesnoAnswer) {
		this.yesnoAnswer = yesnoAnswer;
	}
	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}
	
}
