package net.diaowen.dwsurvey.controller.design;

import com.alibaba.fastjson.JSON;
import net.diaowen.common.plugs.es.DwAnswerEsClientService;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.dwsurvey.entity.SurveyAnswer;
import net.diaowen.dwsurvey.entity.SurveyAnswerJson;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.SurveyJson;
import net.diaowen.dwsurvey.entity.es.DwEsSurveyAnswer;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.SurveyJsonManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

@Controller
@RequestMapping("/api/dwsurvey/none/v6/dw-answer-survey")
public class DwAnswerSurveyController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SurveyAnswerManager surveyAnswerManager;
    @Autowired
    private SurveyJsonManager surveyJsonManager;
    @Autowired
    private DwAnswerEsClientService dwAnswerEsClientService;
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;

    @RequestMapping(value = "/survey-json-by-sid.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult surveyJsonByCascade(@RequestParam String sId){
//        SurveyJson surveyJson = surveyJsonManager.getByCascade(sId);
        return HttpResult.SUCCESS(null);
    }

    @RequestMapping(value = "/survey-json-by-survey-id.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult surveyJsonBySurveyId(@RequestParam String surveyId){
        SurveyJson surveyJson = surveyJsonManager.findBySurveyId(surveyId);
        if (surveyJson==null) {
            SurveyDirectory survey = surveyDirectoryManager.getSurveyBySid(surveyId);
            if (survey!=null) surveyId = survey.getId();
            surveyJson = surveyJsonManager.findBySurveyId(surveyId);
        }
        return HttpResult.SUCCESS(surveyJson);
    }

    @RequestMapping("/save-survey-answer.do")
    @ResponseBody
    public HttpResult saveAnswer(HttpServletRequest request, @RequestBody SurveyAnswerJson surveyAnswerJson) {
        /*SurveyAnswer surveyAnswer = new SurveyAnswer();
        surveyAnswer.setSurveyId(surveyAnswerJson.getSurveyId());
        surveyAnswer.setBgAnDate(new Date());
        surveyAnswer.setEndAnDate(new Date());
        surveyAnswerManager.saveAnswer(surveyAnswer, surveyAnswerJson);
        */
        logger.info("save SurveyAnswerJson {}", JSON.toJSONString(surveyAnswerJson));
        dwAnswerEsClientService.createAnswerDoc(surveyAnswerJson.getAnswerJson());
        return HttpResult.SUCCESS();
    }

    //获取答卷数据
    @RequestMapping(value = "/get-survey-answer.do", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<DwEsSurveyAnswer> answerDataById(String answerId){
        logger.info("answerId {}", answerId);
        DwEsSurveyAnswer dwEsSurveyAnswer = dwAnswerEsClientService.findById(answerId);
        return HttpResult.SUCCESS(dwEsSurveyAnswer);
    }
}
