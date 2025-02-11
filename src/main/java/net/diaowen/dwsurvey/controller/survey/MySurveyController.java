package net.diaowen.dwsurvey.controller.survey;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.HttpStatus;
import net.diaowen.common.plugs.httpclient.PageResult;
import net.diaowen.common.plugs.httpclient.ResultUtils;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.dwsurvey.entity.SurveyDetail;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.SurveyDetailManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.SurveyStatsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/dwsurvey/app/survey")
public class MySurveyController {

    @Autowired
    private AccountManager accountManager;
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
    @Autowired
    private SurveyDetailManager surveyDetailManager;
    @Autowired
    private SurveyAnswerManager surveyAnswerManager;
    @Autowired
    private SurveyStatsManager surveyStatsManager;

    /**
     * 拉取问卷列表
     * @param pageResult
     * @return
     */
    @RequestMapping(value = "/list.do",method = RequestMethod.GET)
    @ResponseBody
    public PageResult<SurveyDirectory> list(PageResult<SurveyDirectory> pageResult, String surveyName, Integer surveyState) {

        User user = accountManager.getCurUser();
        if(user!=null){
            Page<SurveyDirectory> page = ResultUtils.getPageByPageResult(pageResult);
            page = surveyDirectoryManager.findByUser(page,surveyName, surveyState);
            page.setResult(surveyDirectoryManager.upAnQuNum(page.getResult()));
            pageResult = ResultUtils.getPageResultByPage(page,pageResult);
        }
        return pageResult;
    }


    /**
     * 获取问卷详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/info.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<SurveyDirectory> info(String id) {
        try{
            User user = accountManager.getCurUser();
            if(user!=null){
                surveyStatsManager.findBySurvey(id);
                SurveyDirectory survey = surveyDirectoryManager.findUniqueBy(id);
                survey = surveyDirectoryManager.upAnQuNum(survey);
                return HttpResult.SUCCESS(survey);
            }else{
                return HttpResult.buildResult(HttpStatus.NOLOGIN);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.FAILURE();
    }

    /**
     * 创建新问卷
     * @param surveyDirectory
     * @return
     */
    @RequestMapping(value = "/add.do",method = RequestMethod.POST)
    @ResponseBody
    // @RequiresRoles(value = "PROJECT_ADMIN,DEPT_ADMIN,ENT_ADMIN,SUPER_ADMIN,QT_SURVEY_LIST")
    public HttpResult add(@RequestBody SurveyDirectory surveyDirectory) {
        try{
            surveyDirectory.setDirType(2);
            surveyDirectory.setSurveyNameText(surveyDirectory.getSurveyName());
            surveyDirectoryManager.save(surveyDirectory);
            return HttpResult.SUCCESS(surveyDirectory);
        }catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.FAILURE();
    }


    //引用问卷
    @RequestMapping(value = "/copy.do",method = RequestMethod.POST)
    @ResponseBody
    public HttpResult copy(String fromSurveyId, String surveyName, String tag) throws Exception {
        tag="2";
        SurveyDirectory directory=surveyDirectoryManager.createBySurvey(fromSurveyId,surveyName,tag);
        String surveyId=directory.getId();
        return HttpResult.SUCCESS(directory);
    }


    /**
     * 问卷删除
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
    @ResponseBody
    public HttpResult delete(@RequestBody Map<String, String[]> map) throws Exception {
        String result = null;
        try{
            User curUser = accountManager.getCurUser();
            if(curUser!=null){
                if(map!=null){
                    if(map.containsKey("id")){
                        String[] ids = map.get("id");
                        if(ids!=null){
                            for (String id:ids) {
                                SurveyDirectory surveyDirectory= surveyDirectoryManager.findById(id);
                                if (surveyDirectory!=null && !surveyDirectory.getUserId().equals(curUser.getId())) {
                                    return new HttpResult(HttpStatus.SERVER_10002);
                                }
                            }
                            surveyDirectoryManager.delete(ids);
                            return HttpResult.SUCCESS();
                        }
                    }
                }
            }
        }catch (Exception e) {
            result=e.getMessage();
        }
        return HttpResult.FAILURE(result);
    }


    /**
     * 修改状态
     * @return
     */
    @RequestMapping(value = "/up-survey-status.do",method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<SurveyDirectory> upSurveyState(String surveyId, Integer surveyState) {
        try{
            surveyDirectoryManager.upSurveyState(surveyId,surveyState);
            return HttpResult.SUCCESS();
        }catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.FAILURE();
    }


    /**
     * 保存更新基本属性
     * @param surveyDetail
     * @return
     */
    @RequestMapping(value = "/survey-base-attr.do",method = RequestMethod.PUT)
    @ResponseBody
    public HttpResult<SurveyDirectory> saveBaseAttr(@RequestBody SurveyDetail surveyDetail) {
        try{
            surveyDetailManager.saveBaseUp(surveyDetail);
            return HttpResult.SUCCESS();
        }catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.FAILURE();
    }


}
