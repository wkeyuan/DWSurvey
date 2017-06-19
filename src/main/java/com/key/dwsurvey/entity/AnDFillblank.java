package com.key.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.key.common.base.entity.IdEntity;

/**
 * 答卷  多行填空题保存表
 * @author keyuan
 * @date 2012-10-21下午9:26:43
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_an_dfillblank")
public class AnDFillblank extends IdEntity{
	
	//所属问卷ID
	private String belongId;
	//对应的答卷信息表
	private String belongAnswerId;
	//题目 ID
	private String quId;
	//对应的填空项ID
	private String quItemId;
	//答案
	private String answer;
	
	private Integer visibility=1;
	
	public AnDFillblank(){
		
	}
	public AnDFillblank(String surveyId, String surveyAnswerId, String quId,
			String quItemId, String answerValue) {
		this.belongId=surveyId;
		this.belongAnswerId=surveyAnswerId;
		this.quId=quId;
		this.quItemId=quItemId;
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
	public String getQuItemId() {
		return quItemId;
	}
	public void setQuItemId(String quItemId) {
		this.quItemId = quItemId;
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
