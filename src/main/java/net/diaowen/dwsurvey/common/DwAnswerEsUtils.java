package net.diaowen.dwsurvey.common;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import net.diaowen.dwsurvey.entity.AnCheckbox;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.SurveyJson;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswer;
import net.diaowen.dwsurvey.entity.es.answer.question.EsAnQuestion;
import net.diaowen.dwsurvey.entity.es.answer.question.option.EsAnCheckbox;
import net.diaowen.dwsurvey.entity.es.answer.question.option.EsAnScore;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DwAnswerEsUtils {

    private static Logger logger = LoggerFactory.getLogger(DwAnswerEsUtils.class);

    public static void calcSumScore(SurveyJson surveyJson, DwEsSurveyAnswer dwEsSurveyAnswer) {
        double surveyAnScoreNum = 0;
        JsonNode jsonNodeQus = jsonNodeQus(surveyJson);
        if (jsonNodeQus!=null) {
            List<EsAnQuestion> esAnQuestionList = dwEsSurveyAnswer.getAnQuestions();
            for (EsAnQuestion esAnQu:esAnQuestionList) {
                double quAnScoreNum = 0;
                JsonNode jsonQuestion = jsonNodeQu(jsonNodeQus, esAnQu.getQuDwId());
                if (jsonQuestion!=null) {
                    String quType = esAnQu.getQuType();
                    if ("RADIO".equals(quType)) {
                        // 计算单选分数
                        String optionDwId = esAnQu.getAnRadio().getOptionDwId();
                        JsonNode jsonQuRadios = jsonQuestion.get("quRadios");
                        if (jsonQuRadios!=null && jsonQuRadios.isArray() && jsonQuRadios.size()>0) {
                            int jsonNodeSize = jsonQuRadios.size();
                            for (int i=0; i<jsonNodeSize; i++) {
                                JsonNode jsonOption = jsonQuRadios.get(i);
                                String jsonOptionDwId = jsonOption.get("dwId").asText();
                                if (optionDwId.equals(jsonOptionDwId)) {
                                    quAnScoreNum = jsonOption.get("scoreNum").asDouble();
                                    break;
                                }
                            }
                        }
                    } else if ("CHECKBOX".equals(quType)) {
                        List<EsAnCheckbox> anCheckboxes = esAnQu.getAnCheckboxs();
                        JsonNode jsonQuCheckboxs = jsonQuestion.get("quCheckboxs");
                        for (EsAnCheckbox esAnCheckbox: anCheckboxes) {
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
                    } else if ("SCORE".equals(quType)) {
                        List<EsAnScore> anScores = esAnQu.getAnScores();
                        for (EsAnScore anScore: anScores) {
                            String answerScore = anScore.getAnswerScore();
                            if (NumberUtils.isNumber(answerScore)) {
                                quAnScoreNum+= Double.parseDouble(answerScore);
                            }
                        }
                    }
                }
                esAnQu.setQuAnScore(quAnScoreNum);
                surveyAnScoreNum+=quAnScoreNum;
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

}
