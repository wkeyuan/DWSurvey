package net.diaowen.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.diaowen.common.base.entity.IdEntity;

/**
 * 问卷样式信息,包含问卷的不同样式和显示相关设置
 *
 * @author KeYuan
 * @date 2014下午8:41:44
 * <p>
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name = "t_surveyStyle")
public class SurveyStyle extends IdEntity {
    /**
     * 问卷样式所属的问卷ID
     */
    private String surveyId;
    /**
     * 问卷样式的类型, 0 表示 属于每一个问卷, 1 表示样式模板
     */
    private Integer surveyStyleType;
    /**
     * 问卷的logo图片
     */
    private String surveyLogoImage;
    /**
     * 是否显示问卷的logo
     */
    private Integer showSurveyLogo = 0;
    /**
     * 问卷body的背景色
     */
    private String bodyBgColor;
    /**
     * 问卷body的背景图
     */
    private String bodyBgImage;
    /**
     * 是否显示问卷body的背景图
     */
    private Integer showBodyBi = 0;
    /**
     * 问卷中按钮的背景色
     */
    private String surveyBtnBgColor;
    /**
     * 问卷的宽度
     */
    private Integer surveyWidth;
    /**
     * 问卷区的背景色
     */
    private String surveyBgColor;
    /**
     * 问卷区的背景图
     */
    private String surveyBgImage;
    /**
     * 是否显示问卷区的背景图
     */
    private Integer showSurveyBi = 0;
    /**
     * 问卷的顶部内边距
     */
    private String surveyPaddingTop;
    /**
     * 问卷的底部内边距
     */
    private String surveyPaddingBottom;
    /**
     * 问卷的左侧内边距
     */
    private String surveyPaddingLeft;
    /**
     * 问卷的右侧内边距
     */
    private String surveyPaddingRight;
    /**
     * 是否显示问卷的表头
     */
    private Integer showSurveyHaed;
    /**
     * 设置问卷的表头背景色
     */
    private String surveyHeadBgColor;
    /**
     * 设置问卷的表头背景图
     */
    private String surveyHeadBgImage;
    /**
     * 设置是否显示表头背景图
     */
    private Integer showSurveyHbgi = 0;
    /**
     * 设置表头的宽度
     */
    private Integer surveyHeadWidth;
    /**
     * 设置表头的高度
     */
    private Integer surveyHeadHeight;
    /**
     * 设置表头顶部内边距
     */
    private Integer surveyHeadPaddingTop;
    /**
     * 设置表土底部内边距
     */
    private Integer surveyHeadPaddingBottom;
    /**
     * 设置表头左侧内边距
     */
    private Integer surveyHeadPaddingLeft;
    /**
     * 设置表头右侧内边距
     */
    private Integer surveyHeadPaddingRight;
    /**
     * 设置是否显示问卷的标题
     */
    private Integer showSurTitle = 1;
    /**
     * 设置是否显示问卷的副标题
     */
    private Integer showSurNote = 1;
    /**
     * 问卷内容区域头部背景色
     */
    private String surveyContentBgColorTop;
    /**
     * 问卷内容区域中部背景色
     */
    private String surveyContentBgColorMiddle;
    /**
     * 问卷内容区域底部背景色
     */
    private String surveyContentBgColorBottom;
    /**
     * 问卷内容区域顶部背景图
     */
    private String surveyContentBgImageTop;
    /**
     * 问卷内容区域中部背景图
     */
    private String surveyContentBgImageMiddle;
    /**
     * 是否显示问卷的内容区域的背景图
     */
    private Integer showSurveyCbim = 0;
    /**
     * 问卷内容区域底部背景图
     */
    private String surveyContentBgImageBottom;
    /**
     * 问卷内容区域的宽度
     */
    private Integer surveyContentWidth;
    /**
     * 问卷内容区域顶部内边距
     */
    private Integer surveyContentPaddingTop;
    /**
     * 问卷内容区域底部内边距
     */
    private Integer surveyContentPaddingBottom;
    /**
     * 问卷内容区域左侧内边距
     */
    private Integer surveyContentPaddingLeft;
    /**
     * 问卷内容区域右侧内边距
     */
    private Integer surveyContentPaddingRight;
    /**
     * 问卷问题的文本颜色
     */
    private String questionTitleTextColor = "color: rgb(85, 87, 89)";
    /**
     * 问卷选项的文本颜色
     */
    private String questionOptionTextColor = "rgb(99, 101, 102)";
    /**
     * 问卷标题的文本颜色
     */
    private String surveyTitleTextColor = "rgb(34, 34, 34)";
    /**
     * 问卷说明的文本颜色
     */
    private String surveyNoteTextColor = "rgb(112, 114, 115)";
    /**
     * 设置是否显示题序号
     */
    private Integer showTiNum = 1;
    /**
     * 设置是否显示答题的进度
     */
    private Integer showProgressbar = 1;

    /**
     * surveyId的Get方法
     *
     * @return surveyId
     */
    public String getSurveyId() {
        return surveyId;
    }

    /**
     * surveyId的Set方法
     *
     * @param surveyId surveyId
     */
    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * surveyStyleType的Get方法
     *
     * @return surveyStyleType
     */
    public Integer getSurveyStyleType() {
        return surveyStyleType;
    }

    /**
     * surveyStyleType的Set方法
     *
     * @param surveyStyleType surveyStyleType
     */
    public void setSurveyStyleType(Integer surveyStyleType) {
        this.surveyStyleType = surveyStyleType;
    }

    /**
     * bodyBgColor的Get方法
     *
     * @return bodyBgColor
     */
    public String getBodyBgColor() {
        return bodyBgColor;
    }

    /**
     * bodyBgColor的Set方法
     *
     * @param bodyBgColor bodyBgColor
     */
    public void setBodyBgColor(String bodyBgColor) {
        this.bodyBgColor = bodyBgColor;
    }

    /**
     * bodyBgImage的Get方法
     *
     * @return bodyBgImage
     */
    public String getBodyBgImage() {
        return bodyBgImage;
    }

    /**
     * bodyBgImage的Set方法
     *
     * @param bodyBgImage bodyBgImage
     */
    public void setBodyBgImage(String bodyBgImage) {
        this.bodyBgImage = bodyBgImage;
    }

    /**
     * surveyWidth的Get方法
     *
     * @return surveyWidth
     */
    public Integer getSurveyWidth() {
        return surveyWidth;
    }

    /**
     * surveyWidth的Set方法
     *
     * @param surveyWidth surveyWidth
     */
    public void setSurveyWidth(Integer surveyWidth) {
        this.surveyWidth = surveyWidth;
    }

    /**
     * surveyBgColor的Get方法
     *
     * @return surveyBgColor
     */
    public String getSurveyBgColor() {
        return surveyBgColor;
    }

    /**
     * surveyBgColor的Set方法
     *
     * @param surveyBgColor surveyBgColor
     */
    public void setSurveyBgColor(String surveyBgColor) {
        this.surveyBgColor = surveyBgColor;
    }

    /**
     * surveyBgImage的Get方法
     *
     * @return surveyBgImage
     */
    public String getSurveyBgImage() {
        return surveyBgImage;
    }

    /**
     * surveyBgImage的Set方法
     *
     * @param surveyBgImage surveyBgImage
     */
    public void setSurveyBgImage(String surveyBgImage) {
        this.surveyBgImage = surveyBgImage;
    }

    /**
     * surveyPaddingTop的Get方法
     *
     * @return surveyPaddingTop
     */
    public String getSurveyPaddingTop() {
        return surveyPaddingTop;
    }

    /**
     * surveyPaddingTop的Set方法
     *
     * @param surveyPaddingTop surveyPaddingTop
     */
    public void setSurveyPaddingTop(String surveyPaddingTop) {
        this.surveyPaddingTop = surveyPaddingTop;
    }

    /**
     * surveyPaddingBottom的Get方法
     *
     * @return surveyPaddingBottom
     */
    public String getSurveyPaddingBottom() {
        return surveyPaddingBottom;
    }

    /**
     * surveyPaddingBottom的Set方法
     *
     * @param surveyPaddingBottom surveyPaddingBottom
     */
    public void setSurveyPaddingBottom(String surveyPaddingBottom) {
        this.surveyPaddingBottom = surveyPaddingBottom;
    }

    /**
     * surveyPaddingLeft的Get方法
     *
     * @return surveyPaddingLeft
     */
    public String getSurveyPaddingLeft() {
        return surveyPaddingLeft;
    }

    /**
     * surveyPaddingLeft的Set方法
     *
     * @param surveyPaddingLeft surveyPaddingLeft
     */
    public void setSurveyPaddingLeft(String surveyPaddingLeft) {
        this.surveyPaddingLeft = surveyPaddingLeft;
    }

    /**
     * surveyPaddingRight的Get方法
     *
     * @return surveyPaddingRight
     */
    public String getSurveyPaddingRight() {
        return surveyPaddingRight;
    }

    /**
     * surveyPaddingRight的Set方法
     *
     * @param surveyPaddingRight surveyPaddingRight
     */
    public void setSurveyPaddingRight(String surveyPaddingRight) {
        this.surveyPaddingRight = surveyPaddingRight;
    }

    /**
     * showSurveyHaed的Get方法
     *
     * @return showSurveyHaed
     */
    public Integer getShowSurveyHaed() {
        return showSurveyHaed;
    }

    /**
     * showSurveyHaed的Set方法
     *
     * @param showSurveyHaed showSurveyHaed
     */
    public void setShowSurveyHaed(Integer showSurveyHaed) {
        this.showSurveyHaed = showSurveyHaed;
    }

    /**
     * surveyHeadBgColor的Get方法
     *
     * @return surveyHeadBgColor
     */
    public String getSurveyHeadBgColor() {
        return surveyHeadBgColor;
    }

    /**
     * surveyHeadBgColor的Set方法
     *
     * @param surveyHeadBgColor surveyHeadBgColor
     */
    public void setSurveyHeadBgColor(String surveyHeadBgColor) {
        this.surveyHeadBgColor = surveyHeadBgColor;
    }

    /**
     * surveyHeadBgImage的Get方法
     *
     * @return surveyHeadBgImage
     */
    public String getSurveyHeadBgImage() {
        return surveyHeadBgImage;
    }

    /**
     * surveyHeadBgImage的Set方法
     *
     * @param surveyHeadBgImage surveyHeadBgImage
     */
    public void setSurveyHeadBgImage(String surveyHeadBgImage) {
        this.surveyHeadBgImage = surveyHeadBgImage;
    }

    /**
     * surveyHeadWidth的Get方法
     *
     * @return surveyHeadWidth
     */
    public Integer getSurveyHeadWidth() {
        return surveyHeadWidth;
    }

    /**
     * surveyHeadWidth的Set方法
     *
     * @param surveyHeadWidth surveyHeadWidth
     */
    public void setSurveyHeadWidth(Integer surveyHeadWidth) {
        this.surveyHeadWidth = surveyHeadWidth;
    }

    /**
     * surveyHeadHeight的Get方法
     *
     * @return surveyHeadHeight
     */
    public Integer getSurveyHeadHeight() {
        return surveyHeadHeight;
    }

    /**
     * surveyHeadHeight的Set方法
     *
     * @param surveyHeadHeight
     */
    public void setSurveyHeadHeight(Integer surveyHeadHeight) {
        this.surveyHeadHeight = surveyHeadHeight;
    }

    /**
     * surveyHeadPaddingTop的Get方法
     *
     * @return surveyHeadPaddingTop
     */
    public Integer getSurveyHeadPaddingTop() {
        return surveyHeadPaddingTop;
    }

    /**
     * surveyHeadPaddingTop的Set方法
     *
     * @param surveyHeadPaddingTop surveyHeadPaddingTop
     */
    public void setSurveyHeadPaddingTop(Integer surveyHeadPaddingTop) {
        this.surveyHeadPaddingTop = surveyHeadPaddingTop;
    }

    /**
     * surveyHeadPaddingBottom的Get方法
     *
     * @return surveyHeadPaddingBottom
     */
    public Integer getSurveyHeadPaddingBottom() {
        return surveyHeadPaddingBottom;
    }

    /**
     * surveyHeadPaddingBottom的Set方法
     *
     * @param surveyHeadPaddingBottom surveyHeadPaddingBottom
     */
    public void setSurveyHeadPaddingBottom(Integer surveyHeadPaddingBottom) {
        this.surveyHeadPaddingBottom = surveyHeadPaddingBottom;
    }

    /**
     * surveyHeadPaddingLeft的Get方法
     *
     * @return surveyHeadPaddingLeft
     */
    public Integer getSurveyHeadPaddingLeft() {
        return surveyHeadPaddingLeft;
    }

    /**
     * surveyHeadPaddingLeft的Set方法
     *
     * @param surveyHeadPaddingLeft surveyHeadPaddingLeft
     */
    public void setSurveyHeadPaddingLeft(Integer surveyHeadPaddingLeft) {
        this.surveyHeadPaddingLeft = surveyHeadPaddingLeft;
    }

    /**
     * surveyHeadPaddingRight的Get方法
     *
     * @return surveyHeadPaddingRight
     */
    public Integer getSurveyHeadPaddingRight() {
        return surveyHeadPaddingRight;
    }

    /**
     * surveyHeadPaddingRight的Set方法
     *
     * @param surveyHeadPaddingRight surveyHeadPaddingRight
     */
    public void setSurveyHeadPaddingRight(Integer surveyHeadPaddingRight) {
        this.surveyHeadPaddingRight = surveyHeadPaddingRight;
    }

    /**
     * surveyContentBgColorTop的Get方法
     *
     * @return surveyContentBgColorTop
     */
    public String getSurveyContentBgColorTop() {
        return surveyContentBgColorTop;
    }

    /**
     * surveyContentBgColorTop的Set方法
     *
     * @param surveyContentBgColorTop surveyContentBgColorTop
     */
    public void setSurveyContentBgColorTop(String surveyContentBgColorTop) {
        this.surveyContentBgColorTop = surveyContentBgColorTop;
    }

    /**
     * surveyContentBgColorMiddle的Get方法
     *
     * @return surveyContentBgColorMiddle
     */
    public String getSurveyContentBgColorMiddle() {
        return surveyContentBgColorMiddle;
    }

    /**
     * surveyContentBgColorMiddle的Set方法
     *
     * @param surveyContentBgColorMiddle surveyContentBgColorMiddle
     */
    public void setSurveyContentBgColorMiddle(String surveyContentBgColorMiddle) {
        this.surveyContentBgColorMiddle = surveyContentBgColorMiddle;
    }

    /**
     * surveyContentBgColorBottom的Get方法
     *
     * @return surveyContentBgColorBottom
     */
    public String getSurveyContentBgColorBottom() {
        return surveyContentBgColorBottom;
    }

    /**
     * surveyContentBgColorBottom的Set方法
     *
     * @param surveyContentBgColorBottom surveyContentBgColorBottom
     */
    public void setSurveyContentBgColorBottom(String surveyContentBgColorBottom) {
        this.surveyContentBgColorBottom = surveyContentBgColorBottom;
    }

    /**
     * surveyContentBgImageTop的Get方法
     *
     * @return surveyContentBgImageTop
     */
    public String getSurveyContentBgImageTop() {
        return surveyContentBgImageTop;
    }

    /**
     * surveyContentBgImageTop的Set方法
     *
     * @param surveyContentBgImageTop surveyContentBgImageTop
     */
    public void setSurveyContentBgImageTop(String surveyContentBgImageTop) {
        this.surveyContentBgImageTop = surveyContentBgImageTop;
    }

    /**
     * surveyContentBgImageMiddle的Get方法
     *
     * @return surveyContentBgImageMiddle
     */
    public String getSurveyContentBgImageMiddle() {
        return surveyContentBgImageMiddle;
    }

    /**
     * surveyContentBgImageMiddle的Set方法
     *
     * @param surveyContentBgImageMiddle surveyContentBgImageMiddle
     */
    public void setSurveyContentBgImageMiddle(String surveyContentBgImageMiddle) {
        this.surveyContentBgImageMiddle = surveyContentBgImageMiddle;
    }

    /**
     * surveyContentBgImageBottom的Get方法
     *
     * @return surveyContentBgImageBottom
     */
    public String getSurveyContentBgImageBottom() {
        return surveyContentBgImageBottom;
    }

    /**
     * surveyContentBgImageBottom的Set方法
     *
     * @param surveyContentBgImageBottom surveyContentBgImageBottom
     */
    public void setSurveyContentBgImageBottom(String surveyContentBgImageBottom) {
        this.surveyContentBgImageBottom = surveyContentBgImageBottom;
    }

    /**
     * surveyContentWidth的Get方法
     *
     * @return surveyContentWidth
     */
    public Integer getSurveyContentWidth() {
        return surveyContentWidth;
    }

    /**
     * surveyContentWidth的Set方法
     *
     * @param surveyContentWidth surveyContentWidth
     */
    public void setSurveyContentWidth(Integer surveyContentWidth) {
        this.surveyContentWidth = surveyContentWidth;
    }

    /**
     * surveyContentPaddingTop的Get方法
     *
     * @return surveyContentPaddingTop
     */
    public Integer getSurveyContentPaddingTop() {
        return surveyContentPaddingTop;
    }

    /**
     * surveyContentPaddingTop的Set方法
     *
     * @param surveyContentPaddingTop surveyContentPaddingTop
     */
    public void setSurveyContentPaddingTop(Integer surveyContentPaddingTop) {
        this.surveyContentPaddingTop = surveyContentPaddingTop;
    }

    /**
     * surveyContentPaddingBottom的Get方法
     *
     * @return surveyContentPaddingBottom
     */
    public Integer getSurveyContentPaddingBottom() {
        return surveyContentPaddingBottom;
    }

    /**
     * surveyContentPaddingBottom的Set方法
     *
     * @param surveyContentPaddingBottom surveyContentPaddingBottom
     */
    public void setSurveyContentPaddingBottom(Integer surveyContentPaddingBottom) {
        this.surveyContentPaddingBottom = surveyContentPaddingBottom;
    }

    /**
     * surveyContentPaddingLeft的Get方法
     *
     * @return surveyContentPaddingLeft
     */
    public Integer getSurveyContentPaddingLeft() {
        return surveyContentPaddingLeft;
    }

    /**
     * surveyContentPaddingLeft的Set方法
     *
     * @param surveyContentPaddingLeft surveyContentPaddingLeft
     */
    public void setSurveyContentPaddingLeft(Integer surveyContentPaddingLeft) {
        this.surveyContentPaddingLeft = surveyContentPaddingLeft;
    }

    /**
     * surveyContentPaddingRight的Get方法
     *
     * @return surveyContentPaddingRight
     */
    public Integer getSurveyContentPaddingRight() {
        return surveyContentPaddingRight;
    }

    /**
     * surveyContentPaddingRight的Set方法
     *
     * @param surveyContentPaddingRight surveyContentPaddingRight
     */
    public void setSurveyContentPaddingRight(Integer surveyContentPaddingRight) {
        this.surveyContentPaddingRight = surveyContentPaddingRight;
    }

    /**
     * showBodyBi的Get方法
     *
     * @return showBodyBi
     */
    public Integer getShowBodyBi() {
        return showBodyBi;
    }

    /**
     * showBodyBi的Set方法
     *
     * @param showBodyBi showBodyBi
     */
    public void setShowBodyBi(Integer showBodyBi) {
        this.showBodyBi = showBodyBi;
    }

    /**
     * showSurveyBi的Get方法
     *
     * @return showSurveyBi
     */
    public Integer getShowSurveyBi() {
        return showSurveyBi;
    }

    /**
     * showSurveyBi的Set方法
     *
     * @param showSurveyBi showSurveyBi
     */
    public void setShowSurveyBi(Integer showSurveyBi) {
        this.showSurveyBi = showSurveyBi;
    }

    /**
     * showSurveyHbgi的Get方法
     *
     * @return showSurveyHbgi
     */
    public Integer getShowSurveyHbgi() {
        return showSurveyHbgi;
    }

    /**
     * showSurveyHbgi的Set方法
     *
     * @param showSurveyHbgi showSurveyHbgi
     */
    public void setShowSurveyHbgi(Integer showSurveyHbgi) {
        this.showSurveyHbgi = showSurveyHbgi;
    }

    /**
     * showSurveyCbim的Get方法
     *
     * @return showSurveyCbim
     */
    public Integer getShowSurveyCbim() {
        return showSurveyCbim;
    }

    /**
     * showSurveyCbim的Set方法
     *
     * @param showSurveyCbim showSurveyCbim
     */
    public void setShowSurveyCbim(Integer showSurveyCbim) {
        this.showSurveyCbim = showSurveyCbim;
    }

    /**
     * surveyLogoImage的Get方法
     *
     * @return surveyLogoImage
     */
    public String getSurveyLogoImage() {
        return surveyLogoImage;
    }

    /**
     * surveyLogoImage的Set方法
     *
     * @param surveyLogoImage
     */
    public void setSurveyLogoImage(String surveyLogoImage) {
        this.surveyLogoImage = surveyLogoImage;
    }

    /**
     * showSurveyLogo的Get方法
     *
     * @return showSurveyLogo
     */
    public Integer getShowSurveyLogo() {
        return showSurveyLogo;
    }

    /**
     * showSurveyLogo的Set方法
     *
     * @param showSurveyLogo showSurveyLogo
     */
    public void setShowSurveyLogo(Integer showSurveyLogo) {
        this.showSurveyLogo = showSurveyLogo;
    }

    /**
     * questionTitleTextColor的Get方法
     *
     * @return questionTitleTextColor
     */
    public String getQuestionTitleTextColor() {
        return questionTitleTextColor;
    }

    /**
     * questionTitleTextColor的Set方法
     *
     * @param questionTitleTextColor questionTitleTextColor
     */
    public void setQuestionTitleTextColor(String questionTitleTextColor) {
        this.questionTitleTextColor = questionTitleTextColor;
    }

    /**
     * questionOptionTextColor的Get方法
     *
     * @return questionOptionTextColor
     */
    public String getQuestionOptionTextColor() {
        return questionOptionTextColor;
    }

    /**
     * questionOptionTextColor的Set方法
     *
     * @param questionOptionTextColor questionOptionTextColor
     */
    public void setQuestionOptionTextColor(String questionOptionTextColor) {
        this.questionOptionTextColor = questionOptionTextColor;
    }

    /**
     * surveyTitleTextColor的Get方法
     *
     * @return surveyTitleTextColor
     */
    public String getSurveyTitleTextColor() {
        return surveyTitleTextColor;
    }

    /**
     * surveyTitleTextColor的Set方法
     *
     * @param surveyTitleTextColor surveyTitleTextColor
     */
    public void setSurveyTitleTextColor(String surveyTitleTextColor) {
        this.surveyTitleTextColor = surveyTitleTextColor;
    }

    /**
     * surveyNoteTextColor的Get方法
     *
     * @return surveyNoteTextColor
     */
    public String getSurveyNoteTextColor() {
        return surveyNoteTextColor;
    }

    /**
     * surveyNoteTextColor的Set方法
     *
     * @param surveyNoteTextColor surveyNoteTextColor
     */
    public void setSurveyNoteTextColor(String surveyNoteTextColor) {
        this.surveyNoteTextColor = surveyNoteTextColor;
    }

    /**
     * surveyBtnBgColor的Get方法
     *
     * @return surveyBtnBgColor
     */
    public String getSurveyBtnBgColor() {
        return surveyBtnBgColor;
    }

    /**
     * surveyBtnBgColor的Set方法
     *
     * @param surveyBtnBgColor surveyBtnBgColor
     */
    public void setSurveyBtnBgColor(String surveyBtnBgColor) {
        this.surveyBtnBgColor = surveyBtnBgColor;
    }

    /**
     * showSurTitle的Get方法
     *
     * @return showSurTitle
     */
    public Integer getShowSurTitle() {
        return showSurTitle;
    }

    /**
     * showSurTitle的Set方法
     *
     * @param showSurTitle showSurTitle
     */
    public void setShowSurTitle(Integer showSurTitle) {
        this.showSurTitle = showSurTitle;
    }

    /**
     * showSurNote的Get方法
     *
     * @return showSurNote
     */
    public Integer getShowSurNote() {
        return showSurNote;
    }

    /**
     * showSurNote的Set方法
     *
     * @param showSurNote showSurNote
     */
    public void setShowSurNote(Integer showSurNote) {
        this.showSurNote = showSurNote;
    }

    /**
     * showTiNum的Get方法
     *
     * @return showTiNum
     */
    public Integer getShowTiNum() {
        return showTiNum;
    }

    /**
     * showTiNum的Set方法
     *
     * @param showTiNum showTiNum
     */
    public void setShowTiNum(Integer showTiNum) {
        this.showTiNum = showTiNum;
    }

    /**
     * showProgressbar的Get方法
     *
     * @return showProgressbar
     */
    public Integer getShowProgressbar() {
        return showProgressbar;
    }

    /**
     * showProgressbar的Set方法
     *
     * @param showProgressbar
     */
    public void setShowProgressbar(Integer showProgressbar) {
        this.showProgressbar = showProgressbar;
    }
}
