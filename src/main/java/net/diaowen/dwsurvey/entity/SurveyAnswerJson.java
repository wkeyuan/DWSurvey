package net.diaowen.dwsurvey.entity;

import net.diaowen.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_survey_answer_json")
public class SurveyAnswerJson extends IdEntity {

    //问卷ID
    private String surveyId;
    private String sid;
    //所属答卷ID
    private String answerId;
    //text-答卷JSON信息
    private String answerJson;
    private Integer visibility=1;
    //默认为 0
    private Integer statsTag=0;
    //缓存解析状态信息 0=默认状态，未同步到t_an过  1=已保存对t_an对象中 2=正在同步中 3=同步异常
    private Integer answerObjStatus=0;
    //JSON结构版本，null或0为默认V5版本，6=V6
    private Integer jsonVersion;

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerJson() {
        return answerJson;
    }

    public void setAnswerJson(String answerJson) {
        this.answerJson = answerJson;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getStatsTag() {
        return statsTag;
    }

    public void setStatsTag(Integer statsTag) {
        this.statsTag = statsTag;
    }

    public Integer getAnswerObjStatus() {
        return answerObjStatus;
    }

    public void setAnswerObjStatus(Integer answerObjStatus) {
        this.answerObjStatus = answerObjStatus;
    }

    public Integer getJsonVersion() {
        return jsonVersion;
    }

    public void setJsonVersion(Integer jsonVersion) {
        this.jsonVersion = jsonVersion;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
