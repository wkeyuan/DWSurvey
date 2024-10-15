package net.diaowen.dwsurvey.common;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import net.diaowen.common.utils.UserAgentUtils;
import net.diaowen.dwsurvey.entity.AnCheckbox;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.SurveyJson;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswer;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnSource;
import net.diaowen.dwsurvey.entity.es.answer.question.EsAnQuestion;
import net.diaowen.dwsurvey.entity.es.answer.question.option.EsAnCheckbox;
import net.diaowen.dwsurvey.entity.es.answer.question.option.EsAnRadio;
import net.diaowen.dwsurvey.entity.es.answer.question.option.EsAnScore;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DwAnswerEsUtils {

    private static Logger logger = LoggerFactory.getLogger(DwAnswerEsUtils.class);

    public static void calcSumScore(SurveyJson surveyJson, DwEsSurveyAnswer dwEsSurveyAnswer) {
        float surveyAnScoreNum = 0;
        JsonNode jsonNodeQus = jsonNodeQus(surveyJson);
        if (jsonNodeQus!=null) {
            List<EsAnQuestion> esAnQuestionList = dwEsSurveyAnswer.getAnQuestions();
            for (EsAnQuestion esAnQu:esAnQuestionList) {
                float quAnScoreNum = 0;
                if (esAnQu!=null) {
                    JsonNode jsonQuestion = jsonNodeQu(jsonNodeQus, esAnQu.getQuDwId());
                    if (jsonQuestion!=null) {
                        String quType = esAnQu.getQuType();
                        if ("RADIO".equals(quType)) {
                            // 计算单选分数
                            EsAnRadio esAnRadio = esAnQu.getAnRadio();
                            if (esAnRadio!=null) {
                                String optionDwId = esAnRadio.getOptionDwId();
                                JsonNode jsonQuRadios = jsonQuestion.get("quRadios");
                                if (jsonQuRadios!=null && jsonQuRadios.isArray() && jsonQuRadios.size()>0) {
                                    int jsonNodeSize = jsonQuRadios.size();
                                    for (int i=0; i<jsonNodeSize; i++) {
                                        JsonNode jsonOption = jsonQuRadios.get(i);
                                        String jsonOptionDwId = jsonOption.get("dwId").asText();
                                        if (optionDwId.equals(jsonOptionDwId)) {
                                            quAnScoreNum = Float.parseFloat(jsonOption.get("scoreNum").asText());
                                            break;
                                        }
                                    }
                                }
                            }
                        } else if ("CHECKBOX".equals(quType)) {
                            List<EsAnCheckbox> anCheckboxes = esAnQu.getAnCheckboxs();
                            if (anCheckboxes!=null) {
                                JsonNode jsonQuCheckboxs = jsonQuestion.get("quCheckboxs");
                                for (EsAnCheckbox esAnCheckbox: anCheckboxes) {
                                    if (esAnCheckbox!=null) {
                                        String optionDwId = esAnCheckbox.getOptionDwId();
                                        if (jsonQuCheckboxs!=null && jsonQuCheckboxs.isArray() && jsonQuCheckboxs.size()>0) {
                                            int jsonNodeSize = jsonQuCheckboxs.size();
                                            for (int i=0; i<jsonNodeSize; i++) {
                                                JsonNode jsonOption = jsonQuCheckboxs.get(i);
                                                String jsonOptionDwId = jsonOption.get("dwId").asText();
                                                if (optionDwId.equals(jsonOptionDwId)) {
                                                    quAnScoreNum+= jsonOption.get("scoreNum").asDouble();
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if ("SCORE".equals(quType)) {
                            List<EsAnScore> anScores = esAnQu.getAnScores();
                            if (anScores!=null) {
                                for (EsAnScore anScore: anScores) {
                                    String answerScore = anScore.getAnswerScore();
                                    if (NumberUtils.isNumber(answerScore)) {
                                        quAnScoreNum+= Float.parseFloat(answerScore);
                                    }
                                }
                            }
                        }
                    }
                    esAnQu.setQuAnScore(quAnScoreNum);
                    surveyAnScoreNum+=quAnScoreNum;
                }
                logger.info("quAnScoreNum {}", quAnScoreNum);
            }
        }
        dwEsSurveyAnswer.getAnswerCommon().setSumScore(surveyAnScoreNum);
        logger.info("setSumScore {}", surveyAnScoreNum);
    }

    public static JsonNode jsonNodeQu (JsonNode jsonNodeQus, String quId) {
        int jsonNodeSize = jsonNodeQus.size();
        for (int i=0; i<jsonNodeSize; i++) {
            JsonNode jsonQuestion = jsonNodeQus.get(i);
            String quType = jsonQuestion.get("quType").asText();
            String jsonQuId = jsonQuestion.get("dwId").asText();
            if (quId.equals(jsonQuId)) {
                return jsonQuestion;
            }
        }
        return null;
    }

    public static JsonNode jsonNodeQus (SurveyJson surveyJson) {
        String surveyJsonSimple = surveyJson.getSurveyJsonText();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode surveyJsonNode = objectMapper.readTree(surveyJsonSimple);
            if (surveyJsonNode != null) {
                if (surveyJsonNode.has("questions")) {
                    JsonNode jsonNodeQus = surveyJsonNode.get("questions");
                    if (jsonNodeQus!=null) {
                        if (jsonNodeQus.isArray() && jsonNodeQus.size()>0) {
                            return jsonNodeQus;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addAnSource(HttpServletRequest request, DwEsSurveyAnswer esSurveyAnswer) {
        String[] userAgentInt = UserAgentUtils.userAgentPsd(request);
        if(userAgentInt.length == 3){
            EsAnSource esAnSource = esSurveyAnswer.getAnswerCommon().getAnSource();
            if (esAnSource==null) esAnSource = new EsAnSource();
            esAnSource.setSys(userAgentInt[0]);
            esAnSource.setBro(userAgentInt[1]);
            esAnSource.setPcm(userAgentInt[2]);
            esSurveyAnswer.getAnswerCommon().setAnSource(esAnSource);
        }
    }

}
