package com.key.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.key.common.base.entity.IdEntity;

/**
 * 评分题 行选项
 * 
 * @author KeYuan
 * @date 2013下午12:14:26
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name = "t_qu_score")
public class QuScore extends IdEntity {

	// 所属题
	private String quId;
	// 标识
	private String optionTitle;
	// 选项内容
	private String optionName;
	// 排序号
	private Integer orderById;
	// 是否显示 0不显示
	private Integer visibility = 1;

	public String getQuId() {
		return quId;
	}

	public void setQuId(String quId) {
		this.quId = quId;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public Integer getOrderById() {
		return orderById;
	}

	public void setOrderById(Integer orderById) {
		this.orderById = orderById;
	}

	public String getOptionTitle() {
		return optionTitle;
	}

	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
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

	public float avgScore=0f;
	@Transient
	public float getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(float avgScore) {
		this.avgScore = avgScore;
	}
	
}
