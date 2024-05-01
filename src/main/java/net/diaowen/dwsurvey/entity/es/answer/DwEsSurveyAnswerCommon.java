package net.diaowen.dwsurvey.entity.es.answer;

import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnIp;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnState;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnTime;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnUser;
import net.diaowen.dwsurvey.entity.es.answer.question.EsAnQuestion;

import java.io.Serializable;
import java.util.List;

public class DwEsSurveyAnswerCommon implements Serializable {

    private String surveyId;
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
    private Double sumScore;

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

    public Double getSumScore() {
        return sumScore;
    }

    public void setSumScore(Double sumScore) {
        this.sumScore = sumScore;
    }
}
