package com.key.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.key.common.base.entity.IdEntity;

/**
 * 答卷  多选题保存表
 * @author keyuan
 * @date 2012-10-21下午9:26:43
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_an_fillblank")
public class AnFillblank extends IdEntity{
	
	//所属问卷ID
	private String belongId;
	//对应的答卷信息表
	private String belongAnswerId;
	//题目 ID
	private String quId;
	//结果
	private String answer;
	
	private Integer visibility=1;
	
	public AnFillblank(){
		
	}
	public AnFillblank(String surveyId, String surveyAnswerId, String quId,
			String answerValue) {
		this.belongId=surveyId;
		this.belongAnswerId=surveyAnswerId;
		this.quId=quId;
		this.answer=answerValue;
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
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}
	
}
