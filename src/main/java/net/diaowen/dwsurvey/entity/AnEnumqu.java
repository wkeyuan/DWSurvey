package net.diaowen.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.diaowen.common.base.entity.IdEntity;

/**
 * 答卷  枚举题答案
 * @author keyuan(keyuan258@gmail.com)
 * @date 2012-10-21下午9:26:43
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_an_enumqu")
public class AnEnumqu extends IdEntity{
	
	//所属问卷ID
	private String belongId;
	//对应的答卷信息表
	private String belongAnswerId;
	//题目 ID
	private String quId;
	//第几个枚举项
	private Integer enumItem;
	//结果
	private String answer;
	
	private Integer visibility=1;
	
	public AnEnumqu(){
		
	}
	public AnEnumqu(String surveyId, String surveyAnswerId, String quId,
			Integer quItemNum, String answerValue) {
		this.belongId=surveyId;
		this.belongAnswerId=surveyAnswerId;
		this.quId=quId;
		this.enumItem=quItemNum;
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
	public Integer getEnumItem() {
		return enumItem;
	}
	public void setEnumItem(Integer enumItem) {
		this.enumItem = enumItem;
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
