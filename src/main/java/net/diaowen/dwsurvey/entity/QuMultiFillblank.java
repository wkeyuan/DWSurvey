package net.diaowen.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.diaowen.common.CheckType;

import net.diaowen.common.base.entity.IdEntity;

/**
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Entity
@Table(name="t_qu_multi_fillblank")
public class QuMultiFillblank extends IdEntity{

	//所属题
	private String quId;
	//选项标题
	private String optionTitle;
	//选项问题
	private String optionName;
	//说明的验证方式
	private CheckType checkType;
	//排序ID
	private Integer orderById;
	//是否显示  0不显示
	private Integer visibility=1;

	public String getQuId() {
		return quId;
	}
	public void setQuId(String quId) {
		this.quId = quId;
	}
	public String getOptionTitle() {
		return optionTitle;
	}
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
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
	public CheckType getCheckType() {
		return checkType;
	}
	public void setCheckType(CheckType checkType) {
		this.checkType = checkType;
	}

	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

	private Integer anCount;
	@Transient
	public Integer getAnCount() {
		return anCount;
	}
	public void setAnCount(Integer anCount) {
		this.anCount = anCount;
	}
}
