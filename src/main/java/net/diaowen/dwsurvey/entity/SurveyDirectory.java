package net.diaowen.dwsurvey.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Formula;

import net.diaowen.common.base.entity.IdEntity;

/**
 * 问卷目录和问卷,该类为问卷目录或者问卷,取决于其中的dirType属性的值，用于存储问卷或问卷目录的相关信息
 *
 * @author keyuan
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_survey_directory")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SurveyDirectory extends IdEntity{
	/**
	 * 用于短链接的ID
	 */
	private String sid;
	private String parentId="";
	/**
	 * 问卷名称
	 */
	private String surveyName;
	/**
	 * 问卷名称文本
	 */
	private String surveyNameText;
	/**
	 * 创建者ID
	 */
	private String userId;
	/**
	 * 问卷与问卷目录的标志,1表示目录,2表示问卷
	 */
	private Integer dirType=1;
	/**
	 * 在dirType属性等于2时,表示所对应的问卷详细信息的表的ID
	 */
	private String surveyDetailId;
	/**
	 * 问卷或目录的创建时间
	 */
	private Date createDate=new Date();
	/**
	 * 问卷的状态，默认为0——设计状态，1——问卷收集中，2——问卷收集结束
	 */
	private Integer surveyState=0;
	/**
	 * 该问卷包含的问题数
	 */
	private Integer surveyQuNum=0;
	/**
	 * 该问卷在被提交前最少需要完成的题目数量
	 */
	private Integer anItemLeastNum=0;
	/**
	 * 该问卷被回答的次数
	 */
	private Integer answerNum;
	/**
	 * 该问卷是否显示在问卷列表的控制属性,1表示显示,0表示不显示
	 */
	private Integer visibility=1;
	/**
	 * 该问卷所属的问卷模块, 1表示属于问卷模块
	 */
	private Integer surveyModel=1;
	/**
	 * 控制该问卷是否公开问卷的结果的属性,0表示不公开,1表示公开问卷的结果
	 */
	private Integer viewAnswer=0;
	/**
	 * 控制该问卷是否共享的属性,0表示不共享,1表示共享
	 */
	private Integer isShare=1;
	/**
	 * 问卷被引用的次数
	 */
	private Integer excerptNum=0;
	/**
	 * 问卷的状态标识,默认为0——表示待审核,1——表示审核通过,2——表示审核未通过
	 */
	private Integer surveyTag=1;
	/**
	 * 问卷显示的静态HTML保存的路径
	 */
	private String htmlPath;
	/**
	 * json形式的路径
	 */
	private String jsonPath;
	/**
	 * 问卷的分组名称
	 */
	public String groupName;
	/**
	 * 问卷的设置的详细信息
	 */
	private SurveyDetail surveyDetail=null;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 问题列表
	 */
	private List<Question> questions=null;
	/**
	 * 具体某次问卷调查的回答记录
	 */
	private SurveyAnswer surveyAnswer;

	/**
	 * parentId属性的Get方法
	 *
	 * @return parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * parentId属性的Set方法
	 *
	 * @param parentId parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * surveyName属性的Get方法
	 *
	 * @return surveyName
	 */
	public String getSurveyName() {
		return surveyName;
	}

	/**
	 * surveyName属性的Set方法
	 *
	 * @param surveyName surveyName
	 */
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	/**
	 * dirType属性的Get方法
	 *
	 * @return dirType
	 */
	public Integer getDirType() {
		return dirType;
	}

	/**
	 * dirType属性的Set方法
	 *
	 * @param dirType dirType
	 */
	public void setDirType(Integer dirType) {
		this.dirType = dirType;
	}

	/**
	 * createDate属性的Get方法
	 *
	 * @return createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * createDate属性的Set方法
	 *
	 * @param createDate createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * visibility属性的Get方法
	 *
	 * @return visibility
	 */
	public Integer getVisibility() {
		return visibility;
	}

	/**
	 * visibility属性的Set方法
	 *
	 * @param visibility visibility
	 */
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

	/**
	 * surveyDetailId属性的Get方法
	 *
	 * @return surveyDetailId
	 */
	public String getSurveyDetailId() {
		return surveyDetailId;
	}

	/**
	 * surveyDetailId属性的Set方法
	 *
	 * @param surveyDetailId surveyDetailId
	 */
	public void setSurveyDetailId(String surveyDetailId) {
		this.surveyDetailId = surveyDetailId;
	}

	/**
	 * surveyState属性的Get方法
	 *
	 * @return surveyState
	 */
	public Integer getSurveyState() {
		return surveyState;
	}

	/**
	 * surveyState属性的Set方法
	 *
	 * @param surveyState surveyState
	 */
	public void setSurveyState(Integer surveyState) {
		this.surveyState = surveyState;
	}

	/**
	 * surveyQuNum属性的Get方法
	 *
	 * @return surveyQuNum
	 */
	public Integer getSurveyQuNum() {
		return surveyQuNum;
	}

	/**
	 * surveyQuNum属性的Set方法
	 *
	 * @param surveyQuNum surveyQuNum
	 */
	public void setSurveyQuNum(Integer surveyQuNum) {
		this.surveyQuNum = surveyQuNum;
	}

	/**
	 * anItemLeastNum属性的Get方法
	 *
	 * @return anItemLeastNum
	 */
	public Integer getAnItemLeastNum() {
		if(anItemLeastNum==null){
			return 0;
		}
		return anItemLeastNum;
	}

	/**
	 * anItemLeastNum属性的Set方法
	 *
	 * @param anItemLeastNum anItemLeastNum
	 */
	public void setAnItemLeastNum(Integer anItemLeastNum) {
		this.anItemLeastNum = anItemLeastNum;
	}

	/**
	 * userId属性的Get方法
	 *
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userId属性的Set方法
	 *
	 * @param userId userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * surveyTag属性的Get方法
	 *
	 * @return surveyTag
	 */
	public Integer getSurveyTag() {
		return surveyTag;
	}

	/**
	 * surveyTag属性的Set方法
	 *
	 * @param surveyTag surveyTag
	 */
	public void setSurveyTag(Integer surveyTag) {
		this.surveyTag = surveyTag;
	}

	/**
	 * answerNum属性的Get方法
	 *
	 * @return answerNum
	 */
	public Integer getAnswerNum() {
		return answerNum;
	}

	/**
	 * answerNum属性的Set方法
	 *
	 * @param answerNum answerNum
	 */
	public void setAnswerNum(Integer answerNum) {
		this.answerNum = answerNum;
	}

	/**
	 * surveyModel属性的Get方法
	 *
	 * @return surveyModel
	 */
	public Integer getSurveyModel() {
		return surveyModel;
	}

	/**
	 * surveyModel属性的Set方法
	 *
	 * @param surveyModel surveyModel
	 */
	public void setSurveyModel(Integer surveyModel) {
		this.surveyModel = surveyModel;
	}

	/**
	 * viewAnswer属性的Get方法
	 *
	 * @return viewAnswer
	 */
	public Integer getViewAnswer() {
		return viewAnswer;
	}

	/**
	 * viewAnswer属性的Set方法
	 *
	 * @param viewAnswer viewAnswer
	 */
	public void setViewAnswer(Integer viewAnswer) {
		this.viewAnswer = viewAnswer;
	}

	/**
	 * isShare属性的Get方法
	 *
	 * @return isShare
	 */
	public Integer getIsShare() {
		return isShare;
	}

	/**
	 * isShare属性的Set方法
	 *
	 * @param isShare isShare
	 */
	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}

	/**
	 * htmlPath属性的Get方法
	 *
	 * @return htmlPath
	 */
	public String getHtmlPath() {
		return htmlPath;
	}

	/**
	 * htmlPath属性的Set方法
	 *
	 * @param htmlPath htmlPath
	 */
	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	/**
	 * sid属性的Get方法
	 *
	 * @return sid
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * sid属性的Set方法
	 *
	 * @param sid sid
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

	/**
	 * jsonPath属性的Get方法
	 *
	 * @return jsonPath
	 */
	public String getJsonPath() {
		return jsonPath;
	}

	/**
	 * jsonPath属性的Set方法
	 *
	 * @param jsonPath jsonPath
	 */
	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}

	/**
	 * surveyNameText属性的Get方法
	 *
	 * @return surveyNameText
	 */
	public String getSurveyNameText() {
		return surveyNameText;
	}

	/**
	 * surveyNameText属性的Set方法
	 *
	 * @param surveyNameText surveyNameText
	 */
	public void setSurveyNameText(String surveyNameText) {
		this.surveyNameText = surveyNameText;
	}

	/**
	 * groupName属性的Get方法
	 *
	 * @return groupName
	 */
	@Transient
	public String getGroupName() {
		return groupName;
	}

	/**
	 * groupName属性的Set方法
	 *
	 * @param groupName groupName
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * surveyDetail属性的Get方法
	 *
	 * @return surveyDetail
	 */
	@Transient
	public SurveyDetail getSurveyDetail() {
		return surveyDetail;
	}

	/**
	 * surveyDetail属性的Set方法
	 *
	 * @param surveyDetail surveyDetail
	 */
	public void setSurveyDetail(SurveyDetail surveyDetail) {
		this.surveyDetail = surveyDetail;
	}

	/**
	 * excerptNum属性的Get方法
	 *
	 * @return excerptNum
	 */
	public Integer getExcerptNum() {
		return excerptNum;
	}

	/**
	 * excerptNum属性的Set方法
	 *
	 * @param excerptNum excerptNum
	 */
	public void setExcerptNum(Integer excerptNum) {
		this.excerptNum = excerptNum;
	}

	/**
	 * userName属性的Get方法
	 *
	 * @return userName
	 */
	@Formula("(select o.name from t_user o where o.id = user_id)")
	public String getUserName() {
		return userName;
	}

	/**
	 * userName属性的Set方法
	 *
	 * @param userName userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * questions问题列表属性的Get方法
	 *
	 * @return questions
	 */
	@Transient
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * questions问题列表属性的Set方法
	 *
	 * @param questions questions
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	/**
	 * surveyAnswer属性的Get方法
	 *
	 * @return surveyAnswer
	 */
	@Transient
	public SurveyAnswer getSurveyAnswer() {
		return surveyAnswer;
	}

	/**
	 * surveyAnswer属性的Set方法
	 *
	 * @param surveyAnswer surveyAnswer
	 */
	public void setSurveyAnswer(SurveyAnswer surveyAnswer) {
		this.surveyAnswer = surveyAnswer;
	}
}
