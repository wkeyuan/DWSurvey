package net.diaowen.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.diaowen.common.base.entity.IdEntity;

/**
 *
 * @author KeYuan
 * @date 2014下午8:41:44
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_surveyStyle")
public class SurveyStyle extends IdEntity{

	private String surveyId;
	//0属于某个问卷的 、1 样式模板
	private Integer surveyStyleType;

	//logo图片
	private String surveyLogoImage;
	private Integer showSurveyLogo=0;

	//背景色
	private String bodyBgColor;
	//背景图
	private String bodyBgImage;
	private Integer showBodyBi=0;

	//按钮背景色
	private String surveyBtnBgColor;

	//问卷宽度
	private Integer surveyWidth;
	//问卷区背景色
	private String surveyBgColor;
	//问卷区背景图
	private String surveyBgImage;
	private Integer showSurveyBi=0;

	//问卷内边距
	private String surveyPaddingTop;
	private String surveyPaddingBottom;
	private String surveyPaddingLeft;
	private String surveyPaddingRight;

	//是否显示问卷表头
	private Integer showSurveyHaed;
	//头部背景色
	private String surveyHeadBgColor;
	//头部背景图
	private String surveyHeadBgImage;
	private Integer showSurveyHbgi=0;
	//头部宽
	private Integer surveyHeadWidth;
	private Integer surveyHeadHeight;
	private Integer surveyHeadPaddingTop;
	private Integer surveyHeadPaddingBottom;
	private Integer surveyHeadPaddingLeft;
	private Integer surveyHeadPaddingRight;

	//显示问卷标题
	private Integer showSurTitle=1;
	//显示问卷副标题
	private Integer showSurNote=1;

	private String surveyContentBgColorTop;
	private String surveyContentBgColorMiddle;
	private String surveyContentBgColorBottom;
	private String surveyContentBgImageTop;
	private String surveyContentBgImageMiddle;
	private Integer showSurveyCbim=0;
	private String surveyContentBgImageBottom;
	private Integer surveyContentWidth;
	private Integer surveyContentPaddingTop;
	private Integer surveyContentPaddingBottom;
	private Integer surveyContentPaddingLeft;
	private Integer surveyContentPaddingRight;

	//文本样式
	private String questionTitleTextColor="color: rgb(85, 87, 89)";
	private String questionOptionTextColor="rgb(99, 101, 102)";
	private String surveyTitleTextColor="rgb(34, 34, 34)";
	private String surveyNoteTextColor="rgb(112, 114, 115)";

	//显示题序号
	private Integer showTiNum=1;
	//显示答题进度
	private Integer showProgressbar=1;

	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public Integer getSurveyStyleType() {
		return surveyStyleType;
	}
	public void setSurveyStyleType(Integer surveyStyleType) {
		this.surveyStyleType = surveyStyleType;
	}
	public String getBodyBgColor() {
		return bodyBgColor;
	}
	public void setBodyBgColor(String bodyBgColor) {
		this.bodyBgColor = bodyBgColor;
	}
	public String getBodyBgImage() {
		return bodyBgImage;
	}
	public void setBodyBgImage(String bodyBgImage) {
		this.bodyBgImage = bodyBgImage;
	}
	public Integer getSurveyWidth() {
		return surveyWidth;
	}
	public void setSurveyWidth(Integer surveyWidth) {
		this.surveyWidth = surveyWidth;
	}
	public String getSurveyBgColor() {
		return surveyBgColor;
	}
	public void setSurveyBgColor(String surveyBgColor) {
		this.surveyBgColor = surveyBgColor;
	}
	public String getSurveyBgImage() {
		return surveyBgImage;
	}
	public void setSurveyBgImage(String surveyBgImage) {
		this.surveyBgImage = surveyBgImage;
	}
	public String getSurveyPaddingTop() {
		return surveyPaddingTop;
	}
	public void setSurveyPaddingTop(String surveyPaddingTop) {
		this.surveyPaddingTop = surveyPaddingTop;
	}
	public String getSurveyPaddingBottom() {
		return surveyPaddingBottom;
	}
	public void setSurveyPaddingBottom(String surveyPaddingBottom) {
		this.surveyPaddingBottom = surveyPaddingBottom;
	}
	public String getSurveyPaddingLeft() {
		return surveyPaddingLeft;
	}
	public void setSurveyPaddingLeft(String surveyPaddingLeft) {
		this.surveyPaddingLeft = surveyPaddingLeft;
	}
	public String getSurveyPaddingRight() {
		return surveyPaddingRight;
	}
	public void setSurveyPaddingRight(String surveyPaddingRight) {
		this.surveyPaddingRight = surveyPaddingRight;
	}
	public Integer getShowSurveyHaed() {
		return showSurveyHaed;
	}
	public void setShowSurveyHaed(Integer showSurveyHaed) {
		this.showSurveyHaed = showSurveyHaed;
	}
	public String getSurveyHeadBgColor() {
		return surveyHeadBgColor;
	}
	public void setSurveyHeadBgColor(String surveyHeadBgColor) {
		this.surveyHeadBgColor = surveyHeadBgColor;
	}
	public String getSurveyHeadBgImage() {
		return surveyHeadBgImage;
	}
	public void setSurveyHeadBgImage(String surveyHeadBgImage) {
		this.surveyHeadBgImage = surveyHeadBgImage;
	}
	public Integer getSurveyHeadWidth() {
		return surveyHeadWidth;
	}
	public void setSurveyHeadWidth(Integer surveyHeadWidth) {
		this.surveyHeadWidth = surveyHeadWidth;
	}
	public Integer getSurveyHeadHeight() {
		return surveyHeadHeight;
	}
	public void setSurveyHeadHeight(Integer surveyHeadHeight) {
		this.surveyHeadHeight = surveyHeadHeight;
	}
	public Integer getSurveyHeadPaddingTop() {
		return surveyHeadPaddingTop;
	}
	public void setSurveyHeadPaddingTop(Integer surveyHeadPaddingTop) {
		this.surveyHeadPaddingTop = surveyHeadPaddingTop;
	}
	public Integer getSurveyHeadPaddingBottom() {
		return surveyHeadPaddingBottom;
	}
	public void setSurveyHeadPaddingBottom(Integer surveyHeadPaddingBottom) {
		this.surveyHeadPaddingBottom = surveyHeadPaddingBottom;
	}
	public Integer getSurveyHeadPaddingLeft() {
		return surveyHeadPaddingLeft;
	}
	public void setSurveyHeadPaddingLeft(Integer surveyHeadPaddingLeft) {
		this.surveyHeadPaddingLeft = surveyHeadPaddingLeft;
	}
	public Integer getSurveyHeadPaddingRight() {
		return surveyHeadPaddingRight;
	}
	public void setSurveyHeadPaddingRight(Integer surveyHeadPaddingRight) {
		this.surveyHeadPaddingRight = surveyHeadPaddingRight;
	}

	public String getSurveyContentBgColorTop() {
		return surveyContentBgColorTop;
	}
	public void setSurveyContentBgColorTop(String surveyContentBgColorTop) {
		this.surveyContentBgColorTop = surveyContentBgColorTop;
	}
	public String getSurveyContentBgColorMiddle() {
		return surveyContentBgColorMiddle;
	}
	public void setSurveyContentBgColorMiddle(String surveyContentBgColorMiddle) {
		this.surveyContentBgColorMiddle = surveyContentBgColorMiddle;
	}
	public String getSurveyContentBgColorBottom() {
		return surveyContentBgColorBottom;
	}
	public void setSurveyContentBgColorBottom(String surveyContentBgColorBottom) {
		this.surveyContentBgColorBottom = surveyContentBgColorBottom;
	}
	public String getSurveyContentBgImageTop() {
		return surveyContentBgImageTop;
	}
	public void setSurveyContentBgImageTop(String surveyContentBgImageTop) {
		this.surveyContentBgImageTop = surveyContentBgImageTop;
	}
	public String getSurveyContentBgImageMiddle() {
		return surveyContentBgImageMiddle;
	}
	public void setSurveyContentBgImageMiddle(String surveyContentBgImageMiddle) {
		this.surveyContentBgImageMiddle = surveyContentBgImageMiddle;
	}
	public String getSurveyContentBgImageBottom() {
		return surveyContentBgImageBottom;
	}
	public void setSurveyContentBgImageBottom(String surveyContentBgImageBottom) {
		this.surveyContentBgImageBottom = surveyContentBgImageBottom;
	}
	public Integer getSurveyContentWidth() {
		return surveyContentWidth;
	}
	public void setSurveyContentWidth(Integer surveyContentWidth) {
		this.surveyContentWidth = surveyContentWidth;
	}
	public Integer getSurveyContentPaddingTop() {
		return surveyContentPaddingTop;
	}
	public void setSurveyContentPaddingTop(Integer surveyContentPaddingTop) {
		this.surveyContentPaddingTop = surveyContentPaddingTop;
	}
	public Integer getSurveyContentPaddingBottom() {
		return surveyContentPaddingBottom;
	}
	public void setSurveyContentPaddingBottom(Integer surveyContentPaddingBottom) {
		this.surveyContentPaddingBottom = surveyContentPaddingBottom;
	}
	public Integer getSurveyContentPaddingLeft() {
		return surveyContentPaddingLeft;
	}
	public void setSurveyContentPaddingLeft(Integer surveyContentPaddingLeft) {
		this.surveyContentPaddingLeft = surveyContentPaddingLeft;
	}
	public Integer getSurveyContentPaddingRight() {
		return surveyContentPaddingRight;
	}
	public void setSurveyContentPaddingRight(Integer surveyContentPaddingRight) {
		this.surveyContentPaddingRight = surveyContentPaddingRight;
	}
	public Integer getShowBodyBi() {
		return showBodyBi;
	}
	public void setShowBodyBi(Integer showBodyBi) {
		this.showBodyBi = showBodyBi;
	}
	public Integer getShowSurveyBi() {
		return showSurveyBi;
	}
	public void setShowSurveyBi(Integer showSurveyBi) {
		this.showSurveyBi = showSurveyBi;
	}
	public Integer getShowSurveyHbgi() {
		return showSurveyHbgi;
	}
	public void setShowSurveyHbgi(Integer showSurveyHbgi) {
		this.showSurveyHbgi = showSurveyHbgi;
	}
	public Integer getShowSurveyCbim() {
		return showSurveyCbim;
	}
	public void setShowSurveyCbim(Integer showSurveyCbim) {
		this.showSurveyCbim = showSurveyCbim;
	}
	public String getSurveyLogoImage() {
		return surveyLogoImage;
	}
	public void setSurveyLogoImage(String surveyLogoImage) {
		this.surveyLogoImage = surveyLogoImage;
	}
	public Integer getShowSurveyLogo() {
		return showSurveyLogo;
	}
	public void setShowSurveyLogo(Integer showSurveyLogo) {
		this.showSurveyLogo = showSurveyLogo;
	}
	public String getQuestionTitleTextColor() {
		return questionTitleTextColor;
	}
	public void setQuestionTitleTextColor(String questionTitleTextColor) {
		this.questionTitleTextColor = questionTitleTextColor;
	}
	public String getQuestionOptionTextColor() {
		return questionOptionTextColor;
	}
	public void setQuestionOptionTextColor(String questionOptionTextColor) {
		this.questionOptionTextColor = questionOptionTextColor;
	}
	public String getSurveyTitleTextColor() {
		return surveyTitleTextColor;
	}
	public void setSurveyTitleTextColor(String surveyTitleTextColor) {
		this.surveyTitleTextColor = surveyTitleTextColor;
	}
	public String getSurveyNoteTextColor() {
		return surveyNoteTextColor;
	}
	public void setSurveyNoteTextColor(String surveyNoteTextColor) {
		this.surveyNoteTextColor = surveyNoteTextColor;
	}
	public String getSurveyBtnBgColor() {
	    return surveyBtnBgColor;
	}
	public void setSurveyBtnBgColor(String surveyBtnBgColor) {
	    this.surveyBtnBgColor = surveyBtnBgColor;
	}
	public Integer getShowSurTitle() {
		return showSurTitle;
	}
	public void setShowSurTitle(Integer showSurTitle) {
		this.showSurTitle = showSurTitle;
	}
	public Integer getShowSurNote() {
		return showSurNote;
	}
	public void setShowSurNote(Integer showSurNote) {
		this.showSurNote = showSurNote;
	}
	public Integer getShowTiNum() {
		return showTiNum;
	}
	public void setShowTiNum(Integer showTiNum) {
		this.showTiNum = showTiNum;
	}
	public Integer getShowProgressbar() {
		return showProgressbar;
	}
	public void setShowProgressbar(Integer showProgressbar) {
		this.showProgressbar = showProgressbar;
	}


}
