package com.key.dwsurvey.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.key.common.base.entity.IdEntity;

/**
 * 题目逻辑设置
 * @author KeYuan
 * @date 2014下午9:23:21
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_question_logic")
public class QuestionLogic extends IdEntity{
	//cgQuId,cgQuItem,skQuId,skType
	//回答选择的题ID
	private String ckQuId;
	//回答选择题的选项ID  （0任意选项）
	private String cgQuItemId;
	//要跳转的题  (end1提前结束-计入结果  end2提前结束-不计结果)
	private String skQuId;
	//逻辑类型  (1=跳转,2显示)
	private String logicType="1";
	//评分题 ge大于，le小于
	private String geLe;
	private Integer scoreNum;
	//创建时间
	private Date createDate=new Date();
	//是否显示  1显示 0不显示
	private Integer visibility=1;
	
	public String getCkQuId() {
		return ckQuId;
	}
	public void setCkQuId(String ckQuId) {
		this.ckQuId = ckQuId;
	}
	public String getCgQuItemId() {
		return cgQuItemId;
	}
	public void setCgQuItemId(String cgQuItemId) {
		this.cgQuItemId = cgQuItemId;
	}
	public String getSkQuId() {
		return skQuId;
	}
	public void setSkQuId(String skQuId) {
		this.skQuId = skQuId;
	}
	public String getLogicType() {
		return logicType;
	}
	public void setLogicType(String logicType) {
		this.logicType = logicType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}
	
	public String getGeLe() {
	    return geLe;
	}
	public void setGeLe(String geLe) {
	    this.geLe = geLe;
	}
	public Integer getScoreNum() {
	    return scoreNum;
	}
	public void setScoreNum(Integer scoreNum) {
	    this.scoreNum = scoreNum;
	}

	private String title;
	@Transient
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
