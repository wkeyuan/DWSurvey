package net.diaowen.dwsurvey.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.diaowen.common.base.entity.IdEntity;

/**
 * 单个问卷的全局统计信息，包含问卷相关的各类统计信息
 *
 * @author KeYuan
 * @date 2013下午6:48:25
 * <p>
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name = "t_survey_stats")
public class SurveyStats extends IdEntity {
    /**
     * 该全局统计信息所属的问卷Id
     */
    private String surveyId;
    /**
     * 问卷第一条问卷回答数据的时间
     */
    private Date firstAnswer;
    /**
     * 问卷最后一条回答数据的时间
     */
    private Date lastAnswer;
    /**
     * 问卷的回答数量
     */
    private Integer answerNum = 0;
    /**
     * 问卷提交的最少用时
     */
    private Integer anMinTime = 0;
    /**
     * 问卷提交的平均用时
     */
    private Integer anAvgTime = 0;
    /**
     * 未完成全部题目的问卷回答数
     */
    private Integer unCompleteNum = 0;
    /**
     * 完成全部题目的问卷回答数
     */
    private Integer completeNum = 0;
    /**
     * 问卷的无效回答数
     */
    private Integer unEffectiveNum = 0;
    /**
     * 问卷的有效回答数
     */
    private Integer effectiveNum = 0;
    /**
     * 问卷回答中未处理的回答数
     */
    private Integer unHandleNum = 0;
    /**
     * 问卷回答中审核通过的回答数
     */
    private Integer handlePassNum = 0;
    /**
     * 问卷回答中未通过的回答数
     */
    private Integer handleUnPassNum = 0;
    /**
     * 来源于网调的问卷回答数
     */
    private Integer onlineNum = 0;
    /**
     * 来源于数据录入的问卷回答数
     */
    private Integer inputNum = 0;
    /**
     * 来源于移动数据的问卷回答数
     */
    private Integer mobileNum = 0;
    /**
     * 来自导入数据的问卷回答数
     */
    private Integer importNum = 0;
    /**
     * 标识问卷的数据是否是最新的数据, 0 表示不是, 1 表示是
     */
    private Integer isNewData = 0;
    /**
     * 问卷的问题列表
     */
    private List<Question> questions = new ArrayList<Question>();

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
     * firstAnswer的Get方法
     *
     * @return firstAnswer
     */
    public Date getFirstAnswer() {
        return firstAnswer;
    }

    /**
     * firstAnswer的Set方法
     *
     * @param firstAnswer firstAnswer
     */
    public void setFirstAnswer(Date firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    /**
     * lastAnswer的Get方法
     *
     * @return lastAnswer
     */
    public Date getLastAnswer() {
        return lastAnswer;
    }

    /**
     * lastAnswer的Set方法
     *
     * @param lastAnswer lastAnswer
     */
    public void setLastAnswer(Date lastAnswer) {
        this.lastAnswer = lastAnswer;
    }

    /**
     * answerNum的Get方法
     *
     * @return answerNum
     */
    public Integer getAnswerNum() {
        return answerNum;
    }

    /**
     * answerNum的Set方法
     *
     * @param answerNum answerNum
     */
    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }

    /**
     * unCompleteNum的Get方法
     *
     * @return unCompleteNum
     */
    public Integer getUnCompleteNum() {
        return unCompleteNum;
    }

    /**
     * unCompleteNum的Set方法
     *
     * @param unCompleteNum unCompleteNum
     */
    public void setUnCompleteNum(Integer unCompleteNum) {
        this.unCompleteNum = unCompleteNum;
    }

    /**
     * completeNum的Get方法
     *
     * @return completeNum
     */
    public Integer getCompleteNum() {
        return completeNum;
    }

    /**
     * completeNum的Set方法
     *
     * @param completeNum completeNum
     */
    public void setCompleteNum(Integer completeNum) {
        this.completeNum = completeNum;
    }

    /**
     * unEffectiveNum的Get方法
     *
     * @return unEffectiveNum
     */
    public Integer getUnEffectiveNum() {
        return unEffectiveNum;
    }

    /**
     * unEffectiveNum的Set方法
     *
     * @param unEffectiveNum unEffectiveNum
     */
    public void setUnEffectiveNum(Integer unEffectiveNum) {
        this.unEffectiveNum = unEffectiveNum;
    }

    /**
     * effectiveNum的Get方法
     *
     * @return effectiveNum
     */
    public Integer getEffectiveNum() {
        return effectiveNum;
    }

    /**
     * effectiveNum的Set方法
     *
     * @param effectiveNum effectiveNum
     */
    public void setEffectiveNum(Integer effectiveNum) {
        this.effectiveNum = effectiveNum;
    }

    /**
     * unHandleNum的Get方法
     *
     * @return unHandleNum
     */
    public Integer getUnHandleNum() {
        return unHandleNum;
    }

    /**
     * unHandleNum的Set方法
     *
     * @param unHandleNum unHandleNum
     */
    public void setUnHandleNum(Integer unHandleNum) {
        this.unHandleNum = unHandleNum;
    }

    /**
     * handlePassNum的Get方法
     *
     * @return handlePassNum
     */
    public Integer getHandlePassNum() {
        return handlePassNum;
    }

    /**
     * handlePassNum的Set方法
     *
     * @param handlePassNum handlePassNum
     */
    public void setHandlePassNum(Integer handlePassNum) {
        this.handlePassNum = handlePassNum;
    }

    /**
     * handleUnPassNum的Get方法
     *
     * @return handleUnPassNum
     */
    public Integer getHandleUnPassNum() {
        return handleUnPassNum;
    }

    /**
     * handleUnPassNum的Set方法
     *
     * @param handleUnPassNum handleUnPassNum
     */
    public void setHandleUnPassNum(Integer handleUnPassNum) {
        this.handleUnPassNum = handleUnPassNum;
    }

    /**
     * onlineNum的Get方法
     *
     * @return onlineNum
     */
    public Integer getOnlineNum() {
        return onlineNum;
    }

    /**
     * onlineNum的Set方法
     *
     * @param onlineNum onlineNum
     */
    public void setOnlineNum(Integer onlineNum) {
        this.onlineNum = onlineNum;
    }

    /**
     * inputNum的Get方法
     *
     * @return inputNum
     */
    public Integer getInputNum() {
        return inputNum;
    }

    /**
     * inputNum的Set方法
     *
     * @param inputNum inputNum
     */
    public void setInputNum(Integer inputNum) {
        this.inputNum = inputNum;
    }

    /**
     * mobileNum的Get方法
     *
     * @return mobileNum
     */
    public Integer getMobileNum() {
        return mobileNum;
    }

    /**
     * mobileNum的Set方法
     *
     * @param mobileNum mobileNum
     */
    public void setMobileNum(Integer mobileNum) {
        this.mobileNum = mobileNum;
    }

    /**
     * importNum的Get方法
     *
     * @return importNum
     */
    public Integer getImportNum() {
        return importNum;
    }

    /**
     * importNum的Set方法
     *
     * @param importNum importNum
     */
    public void setImportNum(Integer importNum) {
        this.importNum = importNum;
    }

    /**
     * anMinTime的Get方法
     *
     * @return anMinTime
     */
    public Integer getAnMinTime() {
        return anMinTime;
    }

    /**
     * anMinTime的Set方法
     *
     * @param anMinTime anMinTime
     */
    public void setAnMinTime(Integer anMinTime) {
        this.anMinTime = anMinTime;
    }

    /**
     * anAvgTime的Get方法
     *
     * @return anAvgTime
     */
    public Integer getAnAvgTime() {
        return anAvgTime;
    }

    /**
     * anAvgTime的Set方法
     *
     * @param anAvgTime anAvgTime
     */
    public void setAnAvgTime(Integer anAvgTime) {
        this.anAvgTime = anAvgTime;
    }

    /**
     * isNewData的Get方法
     *
     * @return isNewData
     */
    public Integer getIsNewData() {
        return isNewData;
    }

    /**
     * isNewData的Set方法
     *
     * @param isNewData isNewData
     */
    public void setIsNewData(Integer isNewData) {
        this.isNewData = isNewData;
    }

    /**
     * questions的Get方法
     *
     * @return questions
     */
    @Transient
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * questions的Set方法
     *
     * @param questions questions
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
