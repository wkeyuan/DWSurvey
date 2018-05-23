package com.key.dwsurvey.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import com.key.common.base.entity.IdEntity;

/**
 * 问卷目录及问卷
 * @author keyuan
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_survey_directory")
public class SurveyDirectory extends IdEntity{
	
	//用于短链接的ID
	private String sid;
	private String parentId="";
	private String surveyName;
	//创建者ID
	private String userId;
	//1目录，2问卷
	private Integer dirType=1;
	//所对应的问卷详细信息表Id  当dirType=2
	private String surveyDetailId;
	//创建时间
	private Date createDate=new Date();
	//问卷状态  0默认设计状态  1执行中 2结束 
	private Integer surveyState=0;
	//问卷下面有多少题目数 
	private Integer surveyQuNum=0;
	//可以回答的最少选项数目 
	private Integer anItemLeastNum=0;
	//回答次数
	private Integer answerNum=0;
	//是否显示  1显示 0不显示
	private Integer visibility=1;

	//问卷所属的问卷模块   1问卷模块
	private Integer surveyModel=1;
	//是否公开结果  0不  1公开
	private Integer viewAnswer=0;
	//是否共享问卷  0不共享 1共享 
	private Integer isShare=1;
	//引用次数
	private Integer excerptNum=0;
	//问卷标识 默认 0待审核  1审核通过  2审核未通过
	private Integer surveyTag=1;
	
	//静态HTML保存路径
	private String htmlPath;
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public Integer getDirType() {
		return dirType;
	}

	public void setDirType(Integer dirType) {
		this.dirType = dirType;
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

	public String getSurveyDetailId() {
		return surveyDetailId;
	}

	public void setSurveyDetailId(String surveyDetailId) {
		this.surveyDetailId = surveyDetailId;
	}
	public Integer getSurveyState() {
		return surveyState;
	}

	public void setSurveyState(Integer surveyState) {
		this.surveyState = surveyState;
	}
	public Integer getSurveyQuNum() {
		return surveyQuNum;
	}

	public void setSurveyQuNum(Integer surveyQuNum) {
		this.surveyQuNum = surveyQuNum;
	}

	public Integer getAnItemLeastNum() {
		if(anItemLeastNum==null){
			return 0;
		}
		return anItemLeastNum;
	}

	public void setAnItemLeastNum(Integer anItemLeastNum) {
		this.anItemLeastNum = anItemLeastNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getSurveyTag() {
		return surveyTag;
	}

	public void setSurveyTag(Integer surveyTag) {
		this.surveyTag = surveyTag;
	}

	public Integer getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(Integer answerNum) {
		this.answerNum = answerNum;
	}

	public Integer getSurveyModel() {
		return surveyModel;
	}

	public void setSurveyModel(Integer surveyModel) {
		this.surveyModel = surveyModel;
	}

	public Integer getViewAnswer() {
		return viewAnswer;
	}

	public void setViewAnswer(Integer viewAnswer) {
		this.viewAnswer = viewAnswer;
	}

	public Integer getIsShare() {
		return isShare;
	}

	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}


	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}


	public String groupName;
	@Transient
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	private SurveyDetail surveyDetail=null;
	@Transient
	public SurveyDetail getSurveyDetail() {
		return surveyDetail;
	}

	public void setSurveyDetail(SurveyDetail surveyDetail) {
		this.surveyDetail = surveyDetail;
	}
	
	public Integer getExcerptNum() {
		return excerptNum;
	}

	public void setExcerptNum(Integer excerptNum) {
		this.excerptNum = excerptNum;
	}


	//用户名
	private String userName;
	@Formula("(select o.name from t_user o where o.id = user_id)")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	private List<Question> questions=null;
	@Transient
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
}
