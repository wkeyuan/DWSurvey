package net.diaowen.dwsurvey.entity.es.answer;

import net.diaowen.dwsurvey.entity.es.answer.extend.*;
import net.diaowen.dwsurvey.entity.es.answer.question.EsAnQuestion;

import java.io.Serializable;
import java.util.List;

public class DwEsSurveyAnswerCommon implements Serializable {

    private String surveyId;
    private String sid;
    // 原始记录ID
    private String answerId;
    //前端生成的答卷ID
    private String answerDwId;
    //问卷ID
    private String surveyDwId;
    //回答者ID
    private EsAnUser anUser;
    private EsAnTime anTime;
    //回答者IP
    private EsAnIp anIp;
    //答卷状态
    private EsAnState anState;
    //是否删除：0未删除；1删除状态
    private Integer isDelete;
    // 总分
    private Float sumScore;
    // 来源
    private EsAnSource anSource;
    //第三方平台信息
    private EsAnThirdInfo esAnThirdInfo;

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getAnswerDwId() {
        return answerDwId;
    }

    public void setAnswerDwId(String answerDwId) {
        this.answerDwId = answerDwId;
    }

    public String getSurveyDwId() {
        return surveyDwId;
    }

    public void setSurveyDwId(String surveyDwId) {
        this.surveyDwId = surveyDwId;
    }

    public EsAnUser getAnUser() {
        return anUser;
    }

    public void setAnUser(EsAnUser anUser) {
        this.anUser = anUser;
    }

    public EsAnTime getAnTime() {
        return anTime;
    }

    public void setAnTime(EsAnTime anTime) {
        this.anTime = anTime;
    }

    public EsAnIp getAnIp() {
        return anIp;
    }

    public void setAnIp(EsAnIp anIp) {
        this.anIp = anIp;
    }

    public EsAnState getAnState() {
        return anState;
    }

    public void setAnState(EsAnState anState) {
        this.anState = anState;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Float getSumScore() {
        return sumScore;
    }

    public void setSumScore(Float sumScore) {
        this.sumScore = sumScore;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public EsAnSource getAnSource() {
        return anSource;
    }

    public void setAnSource(EsAnSource anSource) {
        this.anSource = anSource;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public EsAnThirdInfo getEsAnThirdInfo() {
        return esAnThirdInfo;
    }

    public void setEsAnThirdInfo(EsAnThirdInfo esAnThirdInfo) {
        this.esAnThirdInfo = esAnThirdInfo;
    }
}
