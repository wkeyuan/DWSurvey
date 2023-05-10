package net.diaowen.dwsurvey.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.diaowen.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 问卷JSON结构信息
 */
@Entity
@Table(name="t_survey_json")
public class SurveyJson extends IdEntity {

//    所属问卷ID
    private String surveyId;
//    问卷JSON信息
    private String surveyJsonText;
//    保存时间
    private Date saveDate;

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
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

}
