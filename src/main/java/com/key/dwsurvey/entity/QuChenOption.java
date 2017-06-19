package com.key.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.key.common.base.entity.IdEntity;

/**
 * 矩陈题-题选项
 * @author KeYuan
 * @date 2013下午12:22:24
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_qu_chen_option")
public class QuChenOption extends IdEntity {
	
	//题
	private String quId;
	//选项名称
	private String optionName;
	//排序
	private Integer orderById;
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
	
	
}
