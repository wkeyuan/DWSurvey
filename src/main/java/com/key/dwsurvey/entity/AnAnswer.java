package com.key.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.key.common.base.entity.IdEntity;
/**
 * 答卷  问答题保存表
 * @author keyuan(keyuan258@gmail.com)
 * @date 2012-10-21下午9:26:43
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Entity
@Table(name="t_an_answer")
public class AnAnswer extends IdEntity{
	
	//所属问卷ID
	private String belongId;
	//对应的答卷信息表ID
	private String belongAnswerId;
	//所属题目ID
	private String quId;
	//结果
	private String answer;
	
	private Integer visibility=1;
	
	public AnAnswer(){

	}
	public AnAnswer(String surveyId, String surveyAnswerId, String quId,
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
