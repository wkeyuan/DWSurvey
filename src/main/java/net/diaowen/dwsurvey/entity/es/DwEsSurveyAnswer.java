package net.diaowen.dwsurvey.entity.es;

import net.diaowen.dwsurvey.entity.es.anquestion.EsAnQuestion;
import net.diaowen.dwsurvey.entity.es.answer.EsAnIp;
import net.diaowen.dwsurvey.entity.es.answer.EsAnState;
import net.diaowen.dwsurvey.entity.es.answer.EsAnTime;
import net.diaowen.dwsurvey.entity.es.answer.EsAnUser;

import java.io.Serializable;
import java.util.List;

public class DwEsSurveyAnswer implements Serializable {

    private String surveyId;
    private String esId;
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

    //
    private List<EsAnQuestion> anQuestions;


    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
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

    public List<EsAnQuestion> getAnQuestions() {
        return anQuestions;
    }

    public void setAnQuestions(List<EsAnQuestion> anQuestions) {
        this.anQuestions = anQuestions;
    }

    public String getEsId() {
        return esId;
    }

    public String getAnswerDwId() {
        return answerDwId;
    }

    public void setAnswerDwId(String answerDwId) {
        this.answerDwId = answerDwId;
    }

    public void setEsId(String esId) {
        this.esId = esId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
