package net.diaowen.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.diaowen.common.base.entity.IdEntity;

/**
 * 答卷 单选题保存表
 *
 * @author keyuan
 * @date 2012-10-21下午9:26:43
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name = "t_an_radio")
public class AnRadio extends IdEntity{

	// 所属问卷ID
	private String belongId;
	// 对应的答卷信息表
	private String belongAnswerId;
	// 题目 ID
	private String quId;
	//结果选项ID
	private String quItemId;
	//复合题的其它项
	private String otherText;

	private Integer visibility=1;

	public AnRadio(){

	}
	public AnRadio(String surveyId, String surveyAnswerId, String quId,
			String quItemId) {
		this.belongId=surveyId;
		this.belongAnswerId=surveyAnswerId;
		this.quId=quId;
		this.quItemId=quItemId;
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
	public String getOtherText() {
		return otherText;
	}
	public void setOtherText(String otherText) {
		this.otherText = otherText;
	}
	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

}
