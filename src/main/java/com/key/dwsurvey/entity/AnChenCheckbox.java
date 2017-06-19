package com.key.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.key.common.base.entity.IdEntity;

/**
 * 矩阵多选题
 * @author KeYuan
 * @date 2013下午8:48:43
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_an_chen_checkbox")
public class AnChenCheckbox extends IdEntity{
	//所属问卷ID
	private String belongId;
	//对应的答卷信息表
	private String belongAnswerId;
	//题目ID
	private String quId;
	//对应的行ID
	private String quRowId;
	//对应的结果ID  即列ID
	private String quColId;
	
	private Integer visibility=1;
	
	public AnChenCheckbox(){
		
	}
	public AnChenCheckbox(String surveyId, String surveyAnswerId, String quId,String quRowId,String quColId) {
		this.belongId=surveyId;
		this.belongAnswerId=surveyAnswerId;
		this.quId=quId;
		this.quRowId=quRowId;
		this.quColId=quColId;
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
	public String getQuColId() {
		return quColId;
	}
	public void setQuColId(String quColId) {
		this.quColId = quColId;
	}
	
	
	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}


	private int anCount;
	@Transient
	public int getAnCount() {
		return anCount;
	}
	public void setAnCount(int anCount) {
		this.anCount = anCount;
	}
}
