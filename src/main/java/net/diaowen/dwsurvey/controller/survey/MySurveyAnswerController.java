package net.diaowen.dwsurvey.controller.survey;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.PageResult;
import net.diaowen.common.plugs.httpclient.ResultUtils;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.utils.UserAgentUtils;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyAnswer;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/dwsurvey/app/answer")
public class MySurveyAnswerController {

    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
    @Autowired
    private SurveyAnswerManager surveyAnswerManager;
    @Autowired
    private AccountManager accountManager;
    /**
     * 获取答卷列表
     * @return
     */
    @RequestMapping(value = "/list.do",method = RequestMethod.GET)
    @ResponseBody
    public PageResult survey(HttpServletRequest request,PageResult<SurveyAnswer> pageResult, String surveyId,String ipAddr,String city,Integer isEffective) {
        UserAgentUtils.userAgent(request);
        User user = accountManager.getCurUser();
        if(user!=null){
            Page page = ResultUtils.getPageByPageResult(pageResult);
            SurveyDirectory survey=surveyDirectoryManager.get(surveyId);
            if(survey!=null){
                if(!user.getId().equals(survey.getUserId())){
                    pageResult.setSuccess(false);
                    return pageResult;
                }
                page=surveyAnswerManager.answerPage(page, surveyId);
            }
            pageResult = ResultUtils.getPageResultByPage(page,pageResult);
        }
        return pageResult;

    }

    @RequestMapping(value = "/info.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult info(String answerId) throws Exception {
        try {
            SurveyAnswer answer = null;
            if (StringUtils.isNotEmpty(answerId)) {
                answer = surveyAnswerManager.findById(answerId);
            }
            if(answer!=null){
                SurveyDirectory survey = surveyDirectoryManager.findUniqueBy(answer.getSurveyId());
                User user= accountManager.getCurUser();
                if(user!=null && survey!=null) {
                    if(!user.getId().equals(survey.getUserId())) {
                        return HttpResult.FAILURE_MSG("没有相应数据权限");
                    }
                    List<Question> questions = surveyAnswerManager.findAnswerDetail(answer);
                    survey.setQuestions(questions);
                    survey.setSurveyAnswer(answer);
                    return HttpResult.SUCCESS(survey);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.FAILURE();
    }


    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
    @ResponseBody
    public HttpResult delete(@RequestBody Map<String, String[]> map) throws Exception {
//		HttpResult httpResultRP = ShiroAuthorizationUtils.isDefaultAdminRoleAndPermissionHttpResult(PermissionCode.HT_CASCADEDB_DELETE);
//		if (httpResultRP != null) return httpResultRP;
        try{
            if(map!=null){
                if(map.containsKey("id")){
                    String[] ids = map.get("id");
                    if(ids!=null){
                        surveyAnswerManager.deleteData(ids);
                    }
                }
            }
            return HttpResult.SUCCESS();
        }catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.FAILURE();
    }


    @RequestMapping("/export-xls.do")
    @ResponseBody
    public String exportXLS(HttpServletRequest request, HttpServletResponse response, String surveyId,String expUpQu) throws Exception{
        try{
            String savePath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
            User user=accountManager.getCurUser();
            if(user!=null){
                SurveyDirectory survey = surveyDirectoryManager.get(surveyId);
                if(survey!=null){
                    if(!user.getId().equals(survey.getUserId())){
                        return "没有相应数据权限";
                    }
                    //直接导出excel
                    savePath=surveyAnswerManager.exportXLS(surveyId,savePath);
                    response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode("dwsurvey_"+survey.getSid()+".xlsx", "UTF-8"));
                    request.getRequestDispatcher(savePath).forward(request,response);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
