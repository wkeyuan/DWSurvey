package net.diaowen.dwsurvey.entity.es.answer;

import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnIp;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnState;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnTime;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnUser;
import net.diaowen.dwsurvey.entity.es.answer.question.EsAnQuestion;

import java.io.Serializable;
import java.util.List;

public class DwEsSurveyAnswerAnOption implements Serializable {

    private String answerOptionDwId;
    // 回答选项数值
    private Float answerNum;
    // 回答选项文本值
    private String answerText;
    private String quDwId;
    private String quType;
    // 所有与之有关联的dwId;
    private List<String> relateDwIds;
    // 关联的 DwEsSurveyAnswer 索引ID
    private String relateAnswerResponseId;

    private DwEsSurveyAnswerCommon answerCommon;

    public String getAnswerOptionDwId() {
        return answerOptionDwId;
    }

    public void setAnswerOptionDwId(String answerOptionDwId) {
        this.answerOptionDwId = answerOptionDwId;
    }

    public Float getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Float answerNum) {
        this.answerNum = answerNum;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getQuDwId() {
        return quDwId;
    }

    public void setQuDwId(String quDwId) {
        this.quDwId = quDwId;
    }

    public String getQuType() {
        return quType;
    }

    public void setQuType(String quType) {
        this.quType = quType;
    }

    public List<String> getRelateDwIds() {
        return relateDwIds;
    }

    public void setRelateDwIds(List<String> relateDwIds) {
        this.relateDwIds = relateDwIds;
    }

    public String getRelateAnswerResponseId() {
        return relateAnswerResponseId;
    }

    public void setRelateAnswerResponseId(String relateAnswerResponseId) {
        this.relateAnswerResponseId = relateAnswerResponseId;
    }

    public DwEsSurveyAnswerCommon getAnswerCommon() {
        return answerCommon;
    }

    public void setAnswerCommon(DwEsSurveyAnswerCommon answerCommon) {
        this.answerCommon = answerCommon;
    }
}
