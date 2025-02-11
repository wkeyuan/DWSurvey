package net.diaowen.dwsurvey.common;

import net.diaowen.dwsurvey.entity.SurveyJson;

public class DwSurveyAnswerResult {

    private DwAnswerCheckResult answerCheckResult;

    private SurveyJson surveyJson;

    public DwAnswerCheckResult getAnswerCheckResult() {
        return answerCheckResult;
    }

    public void setAnswerCheckResult(DwAnswerCheckResult answerCheckResult) {
        this.answerCheckResult = answerCheckResult;
    }

    public SurveyJson getSurveyJson() {
        return surveyJson;
    }

    public void setSurveyJson(SurveyJson surveyJson) {
        this.surveyJson = surveyJson;
    }
}
