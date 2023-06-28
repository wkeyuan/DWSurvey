package net.diaowen.dwsurvey.controller.design;

import com.alibaba.fastjson.JSON;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.dwsurvey.entity.SurveyAnswer;
import net.diaowen.dwsurvey.entity.SurveyAnswerJson;
import net.diaowen.dwsurvey.entity.SurveyJson;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.SurveyJsonManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/api/dwsurvey/none/v6/dw-answer-survey")
public class DwAnswerSurveyController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SurveyAnswerManager surveyAnswerManager;
    @Autowired
    private SurveyJsonManager surveyJsonManager;

    @RequestMapping(value = "/survey-json-by-survey-id.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult surveyJsonBySurveyId(@RequestParam String surveyId){
        SurveyJson surveyJson = surveyJsonManager.findBySurveyId(surveyId);
        return HttpResult.SUCCESS(surveyJson);
    }

    @RequestMapping("/save-survey-answer.do")
    @ResponseBody
    public HttpResult saveAnswer(HttpServletRequest request, @RequestBody SurveyAnswerJson surveyAnswerJson) {
        SurveyAnswer surveyAnswer = new SurveyAnswer();
        surveyAnswer.setSurveyId(surveyAnswerJson.getSurveyId());
        surveyAnswer.setBgAnDate(new Date());
        surveyAnswer.setEndAnDate(new Date());
        surveyAnswerManager.saveAnswer(surveyAnswer, surveyAnswerJson);
        logger.info("save SurveyAnswerJson {}", JSON.toJSONString(surveyAnswerJson));
        return HttpResult.SUCCESS();
    }


}