package net.diaowen.dwsurvey.common;

import net.diaowen.dwsurvey.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class DesignSurveyToolbarTabQu {

    private List<Question> questions;

    private String tabQuName;

    public DesignSurveyToolbarTabQu() {

    }

    public DesignSurveyToolbarTabQu(String tabQuName, List<Question> questions) {
        this.tabQuName = tabQuName;
        this.questions = questions;
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
}
