package net.diaowen.dwsurvey.controller.survey;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.SurveyStats;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.SurveyStatsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/dwsurvey/app/stats")
public class MySurveyStatsController {

    @Autowired
    private SurveyStatsManager surveyStatsManager;
    @Autowired
    private SurveyAnswerManager surveyAnswerManager;
    @Autowired
    private AccountManager accountManager;
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;

    @RequestMapping("/report.do")
    @ResponseBody
    public HttpResult report(String surveyId) {

        // 得到频数分析数据
        User user = accountManager.getCurUser();
        if(user!=null){
            SurveyDirectory survey = surveyDirectoryManager.findUniqueBy(surveyId);
            if(survey!=null){
                if(!user.getId().equals(survey.getUserId())){
                    return HttpResult.FAILURE_MSG("没有相应数据权限");
                }
                List<Question> questions = surveyStatsManager.findFrequency(survey);
                SurveyStats surveyStats = new SurveyStats();
                surveyStats.setQuestions(questions);
                return HttpResult.SUCCESS(surveyStats);
            }
        }
        return HttpResult.FAILURE();
    }



}
