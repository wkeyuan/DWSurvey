package net.diaowen.dwsurvey.controller.design.admin;

import com.alibaba.fastjson.JSON;
import net.diaowen.common.plugs.es.DwAnswerEsClientService;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.dwsurvey.config.ESClientConfig;
import net.diaowen.dwsurvey.entity.es.DwEsSurveyAnswer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/dwsurvey/app/v6/dw-answer-data-survey")
public class DwAnswerDataController {
    private static Logger logger = LoggerFactory.getLogger(DwAnswerDataController.class);
    @Resource
    private DwAnswerEsClientService dwAnswerEsClientService;

    @RequestMapping(value = "/list.do",method = RequestMethod.GET)
    @ResponseBody
    public Page<DwEsSurveyAnswer> surveyJsonBySurveyId(Page<DwEsSurveyAnswer> page, String surveyId, String bgAnDate, String endAnDate, String ip, String city){
        logger.info("bgAnDate {}", bgAnDate);
        logger.info("endAnDate {}", endAnDate);
        return dwAnswerEsClientService.findPageByFromSize(page, surveyId, bgAnDate, endAnDate, ip, city, null, 0);
    }


}
