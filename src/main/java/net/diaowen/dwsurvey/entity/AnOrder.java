package net.diaowen.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.diaowen.common.base.entity.IdEntity;

/**
 * 评分题
 * @author KeYuan
 * @date 2013下午8:48:24
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_an_order")
public class AnOrder extends IdEntity{

	//所属问卷ID
	private String belongId;
	//对应的答卷信息表
	private String belongAnswerId;
	//题目ID
	private String quId;
	//对应的行ID
	private String quRowId;
	//对应的结果，即分值
	private String orderyNum;

	private Integer visibility=1;

	public AnOrder(){

	}
	public AnOrder(String surveyId, String surveyAnswerId, String quId,String quRowId,String orderyNum) {
		this.belongId=surveyId;
		this.belongAnswerId=surveyAnswerId;
		this.quId=quId;
		this.quRowId=quRowId;
		this.orderyNum=orderyNum;
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
	public String getQuRowId() {
		return quRowId;
	}
	public void setQuRowId(String quRowId) {
		this.quRowId = quRowId;
	}
	public String getOrderyNum() {
		return orderyNum;
	}
	public void setOrderyNum(String orderyNum) {
		this.orderyNum = orderyNum;
	}
	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

}
