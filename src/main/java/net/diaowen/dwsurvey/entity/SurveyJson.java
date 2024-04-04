package net.diaowen.dwsurvey.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.diaowen.common.base.entity.IdEntity;
import net.diaowen.dwsurvey.common.AnswerCheckData;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 问卷JSON结构信息
 */
@Entity
@Table(name="t_survey_json")
public class SurveyJson extends IdEntity {

//    所属问卷ID
    private String surveyId;
    private String sid;
    //    问卷基本JSON信息，不包含题目
    private String surveyJsonSimple;
//    问卷全部JSON信息，含题目
    private String surveyJsonText;
//    保存时间
    private Date saveDate;

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyJsonSimple() {
        return surveyJsonSimple;
    }

    public void setSurveyJsonSimple(String surveyJsonSimple) {
        this.surveyJsonSimple = surveyJsonSimple;
    }

    public String getSurveyJsonText() {
        return surveyJsonText;
    }

    public void setSurveyJsonText(String surveyJsonText) {
        this.surveyJsonText = surveyJsonText;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
