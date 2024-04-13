package net.diaowen.dwsurvey.common;

import net.diaowen.dwsurvey.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class DesignSurveyToolbarTabQu {

    private List<Question> questions;

    private String tabQuName;

    // 默认drag 或 click，
    private String eventType;

    private String dwBtnIconClass;

    public DesignSurveyToolbarTabQu() {

    }

    public DesignSurveyToolbarTabQu(String tabQuName, List<Question> questions) {
        this.tabQuName = tabQuName;
        this.questions = questions;
    }

    public DesignSurveyToolbarTabQu(String tabQuName, List<Question> questions, String eventType, String dwBtnIconClass) {
        this.tabQuName = tabQuName;
        this.questions = questions;
        this.eventType = eventType;
        this.dwBtnIconClass = dwBtnIconClass;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getTabQuName() {
        return tabQuName;
    }

    public void setTabQuName(String tabQuName) {
        this.tabQuName = tabQuName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDwBtnIconClass() {
        return dwBtnIconClass;
    }

    public void setDwBtnIconClass(String dwBtnIconClass) {
        this.dwBtnIconClass = dwBtnIconClass;
    }

}
