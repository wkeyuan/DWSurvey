package net.diaowen.dwsurvey.controller.design;

import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.dwsurvey.entity.SurveyJson;
import net.diaowen.dwsurvey.service.SurveyJsonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/dwsurvey/none/dw-answer-survey")
public class DwAnswerSurveyController {
    @Autowired
    private SurveyJsonManager surveyJsonManager;

    @RequestMapping(value = "/survey-json-by-survey-id.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult surveyJsonBySurveyId(@RequestParam String surveyId){
        SurveyJson surveyJson = surveyJsonManager.findBySurveyId(surveyId);
        return HttpResult.SUCCESS(surveyJson);
    }


}
