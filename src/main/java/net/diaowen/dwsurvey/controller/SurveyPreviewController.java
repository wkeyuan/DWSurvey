package net.diaowen.dwsurvey.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.utils.DiaowenProperty;
import net.diaowen.common.utils.RandomUtils;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.SurveyReqUrl;
import net.diaowen.dwsurvey.entity.SurveyStyle;
import net.diaowen.dwsurvey.service.QuestionManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.SurveyReqUrlManager;
import net.diaowen.dwsurvey.service.SurveyStyleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by keyuan on 2018/6/9.
 */
@Controller
@RequestMapping("/design/spc/dwsurvey-preview")
public class SurveyPreviewController {

    @Autowired
    private QuestionManager questionManager;
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
    @Autowired
    private SurveyStyleManager surveyStyleManager;
    @Autowired
    private SurveyReqUrlManager surveyReqUrlManager;
    @Autowired
    private AccountManager accountManager;

    //问卷的动态访问方式
    @RequestMapping("/preview.do")
    public ModelAndView preview(HttpServletRequest request, String surveyId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        buildSurvey(surveyId,modelAndView);
        SurveyReqUrl surveyReqUrl = surveyReqUrlManager.newSurveyReqUrl(surveyId,3);
        String baseUrl = request.getScheme() +"://" + request.getServerName() + (request.getServerPort() == 80 ? "" : ":" +request.getServerPort()) + request.getContextPath();
        modelAndView.addObject("baseUrl", baseUrl);
        modelAndView.addObject("surveyReqUrl",surveyReqUrl);
        modelAndView.setViewName("/diaowen-design/survey_preview");
        return modelAndView;
    }

    private void buildSurvey(String surveyId,ModelAndView modelAndView) {
        //判断是否拥有权限
        User user= accountManager.getCurUser();
        if(user!=null){
            String userId=user.getId();
            SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(surveyId, userId);
            if(surveyDirectory!=null){
                surveyDirectoryManager.getSurveyDetail(surveyId, surveyDirectory);
//				SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
                List<Question> questions=questionManager.findDetails(surveyId, "2");
                surveyDirectory.setQuestions(questions);
                surveyDirectory.setSurveyQuNum(questions.size());
                surveyDirectoryManager.save(surveyDirectory);
                modelAndView.addObject("survey",surveyDirectory);
                SurveyStyle surveyStyle=surveyStyleManager.getBySurveyId(surveyId);
                modelAndView.addObject("surveyStyle",surveyStyle);
                modelAndView.addObject("prevHost", DiaowenProperty.STORAGE_URL_PREFIX);
            }else{
                modelAndView.addObject("msg","未登录或没有相应数据权限");
            }
        }else{
            modelAndView.addObject("msg","未登录或没有相应数据权限");
        }
    }
}
