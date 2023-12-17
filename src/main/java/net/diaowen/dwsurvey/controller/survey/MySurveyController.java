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
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/dwsurvey/app/survey")
public class MySurveyController {

    private final AccountManager accountManager;
    private final SurveyDirectoryManager surveyDirectoryManager;
    private final SurveyDetailManager surveyDetailManager;
    private final SurveyAnswerManager surveyAnswerManager;
    private final SurveyStatsManager surveyStatsManager;

    @Autowired
    public MySurveyController(AccountManager accountManager, SurveyDirectoryManager surveyDirectoryManager, SurveyDetailManager surveyDetailManager, SurveyAnswerManager surveyAnswerManager, SurveyStatsManager surveyStatsManager) {
        this.accountManager = accountManager;
        this.surveyDirectoryManager = surveyDirectoryManager;
        this.surveyDetailManager = surveyDetailManager;
        this.surveyAnswerManager = surveyAnswerManager;
        this.surveyStatsManager = surveyStatsManager;
    }

    /**
     * 拉取问卷列表
     * @param pageResult
     * @return
     */
    @GetMapping(path = "/list.do")
    public PageResult<SurveyDirectory> list(PageResult<SurveyDirectory> pageResult, String surveyName, Integer surveyState) {

        User user = accountManager.getCurUser();
        if(user!=null){
            Page<SurveyDirectory> page = ResultUtils.getPageByPageResult(pageResult);
            page = surveyDirectoryManager.findByUser(page,surveyName, surveyState);
            page.setResult(surveyAnswerManager.upAnQuNum(page.getResult()));
            pageResult = ResultUtils.getPageResultByPage(page,pageResult);
        }
        return pageResult;
    }


    /**
     * 获取问卷详情
     * @param id
     * @return
     */
    @GetMapping(path = "/info.do")
    public HttpResult<SurveyDirectory> info(String id) {
        try{
            User user = accountManager.getCurUser();
            if(user!=null){
                surveyStatsManager.findBySurvey(id);
                SurveyDirectory survey = surveyDirectoryManager.findUniqueBy(id);
                survey = surveyAnswerManager.upAnQuNum(survey);
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
    @PostMapping(value = "/add.do")
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


    /**
     * 引用问卷
     * @param fromSurveyId
     * @param surveyName
     * @param tag
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/copy.do")
    public HttpResult copy(String fromSurveyId, String surveyName, String tag) {
        tag="2";
        SurveyDirectory directory=surveyDirectoryManager.createBySurvey(fromSurveyId,surveyName,tag);
        return HttpResult.SUCCESS(directory);
    }


    /**
     * 问卷删除
     * @param map
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/delete.do")
    public HttpResult delete(@RequestBody Map<String, String[]> map) {
        String result = null;
        try{
            User curUser = accountManager.getCurUser();
            if(curUser!=null && map!=null && map.containsKey("id")){

                        String[] ids = map.get("id");
                        if(ids!=null){
                            surveyDirectoryManager.delete(ids);
                            return HttpResult.SUCCESS();
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
    @PostMapping(path = "/up-survey-status.do")
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
    @PutMapping(path = "/survey-base-attr.do")
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
