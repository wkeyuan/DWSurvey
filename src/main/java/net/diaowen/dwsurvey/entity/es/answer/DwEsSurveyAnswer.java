package net.diaowen.dwsurvey.entity.es.answer;

import net.diaowen.dwsurvey.entity.es.answer.question.EsAnQuestion;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnIp;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnState;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnTime;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnUser;

import java.io.Serializable;
import java.util.List;

public class DwEsSurveyAnswer implements Serializable {

    private String esId;

    private DwEsSurveyAnswerCommon answerCommon;

    private List<EsAnQuestion> anQuestions;

    //答卷密码
    private String anPwd;
    //sessionId
    private String httpSessionId;

    public DwEsSurveyAnswerCommon getAnswerCommon() {
        return answerCommon;
    }

    public void setAnswerCommon(DwEsSurveyAnswerCommon answerCommon) {
        this.answerCommon = answerCommon;
    }

    public List<EsAnQuestion> getAnQuestions() {
        return anQuestions;
    }

    public void setAnQuestions(List<EsAnQuestion> anQuestions) {
        this.anQuestions = anQuestions;
    }

    public String getAnPwd() {
        return anPwd;
    }

    public void setAnPwd(String anPwd) {
        this.anPwd = anPwd;
    }

    public String getEsId() {
        return esId;
    }

    public void setEsId(String esId) {
        this.esId = esId;
    }

    public String getHttpSessionId() {
        return httpSessionId;
    }

    public void setHttpSessionId(String httpSessionId) {
        this.httpSessionId = httpSessionId;
    }
}
