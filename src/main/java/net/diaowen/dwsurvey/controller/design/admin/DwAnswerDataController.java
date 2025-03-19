package net.diaowen.dwsurvey.controller.design.admin;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.es.DwAnswerEsClientService;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.PageResult;
import net.diaowen.common.plugs.httpclient.ResultUtils;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.utils.UserAgentUtils;
import net.diaowen.common.utils.ZipUtil;
import net.diaowen.dwsurvey.common.AggregationResultItem;
import net.diaowen.dwsurvey.common.PermissionCode;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.*;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswer;
import net.diaowen.dwsurvey.service.SurveyAnswerExportManager;
import net.diaowen.dwsurvey.service.SurveyAnswerJsonManager;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.es.EsSurveyAnswerManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/dwsurvey/app/v6/dw-answer-data-survey")
@Api(tags = "答卷数据管理")
public class DwAnswerDataController {
    private static Logger logger = LoggerFactory.getLogger(DwAnswerDataController.class);
    @Resource
    private DwAnswerEsClientService dwAnswerEsClientService;
    @Autowired
    private AccountManager accountManager;
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
    @Autowired
    private SurveyAnswerManager surveyAnswerManager;
    @Autowired
    private SurveyAnswerJsonManager surveyAnswerJsonManager;
    @Autowired
    private EsSurveyAnswerManager esSurveyAnswerManager;
    @Autowired
    private SurveyAnswerExportManager surveyAnswerExportManager;

    /**
     * 获取答卷列表
     * @return
     */
    @RequestMapping(value = "/list.do",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取答卷列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageSize",value="页数据数",paramType = "query",dataType = "java.lang.Integer"),
            @ApiImplicitParam(name="current",value="当前页码",paramType = "query",dataType = "java.lang.Integer"),
    })
    public Page survey(HttpServletRequest request, @ApiIgnore Page<DwEsSurveyAnswer> page, String surveyId, String ipAddr, String city, Integer isEffective, Integer handleState, String anUserKey, Integer saveStatus, String bgAnDate, String endAnDate) {
        UserAgentUtils.userAgent(request);
        User user = accountManager.getCurUser();
        if(user!=null){
            Page<SurveyAnswer> newPage = ResultUtils.getPageByPage(page);
            SurveyDirectory survey=surveyDirectoryManager.get(surveyId);
            if(survey!=null){
                HttpResult httpResult = surveyDirectoryManager.isSurveyRoleOrPerm(user.getId(),survey.getUserId(), PermissionCode.HT_SURVEY_DATA_ANSWER_LIST);
                if(httpResult!=null) return page;
                newPage = surveyAnswerManager.answerPage(newPage, surveyId, ipAddr, city, isEffective, handleState, anUserKey, saveStatus);
                List<SurveyAnswer> surveyAnswers = newPage.getResult();
                List<DwEsSurveyAnswer> dwEsSurveyAnswers = new ArrayList<>();
                for (SurveyAnswer surveyAnswer:surveyAnswers) {
                    String answerId = surveyAnswer.getId();
                    SurveyAnswerJson surveyAnswerJson = surveyAnswerJsonManager.findByAnswerId(answerId);
                    if (surveyAnswerJson!=null) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            DwEsSurveyAnswer dwEsSurveyAnswer = objectMapper.readValue(surveyAnswerJson.getAnswerJson(), DwEsSurveyAnswer.class);
                            dwEsSurveyAnswer.getAnswerCommon().setAnswerId(answerId);
                            dwEsSurveyAnswers.add(dwEsSurveyAnswer);
                        } catch (JsonProcessingException e) {
                            logger.error("dwEsSurveyAnswer 转换错误 {}", e.getMessage());
                        }
                    }
                }
                page.setResult(dwEsSurveyAnswers);
                if (dwEsSurveyAnswers.size()<=0) {
                    return surveyJsonBySurveyId(page, surveyId, bgAnDate, endAnDate, ipAddr, city);
                }
            }
        }
        return page;
    }

    @RequestMapping(value = "/list-es.do",method = RequestMethod.GET)
    @ResponseBody
    public Page<DwEsSurveyAnswer> surveyJsonBySurveyId(Page<DwEsSurveyAnswer> page, String surveyId, String bgAnDate, String endAnDate, String ip, String city){
        User user = accountManager.getCurUser();
        try{
            if(user!=null){
                SurveyDirectory survey=surveyDirectoryManager.get(surveyId);
                if(survey!=null){
                    HttpResult httpResult = surveyDirectoryManager.isSurveyRoleOrPerm(user.getId(),survey.getUserId(), PermissionCode.HT_SURVEY_DATA_ANSWER_LIST);
                    if(httpResult!=null) return page;
                    page = dwAnswerEsClientService.findPageByFromSize(page, surveyId, bgAnDate, endAnDate, ip, city, null, 0);
                }
            }
        }catch (Exception e){
            logger.error("list-es.do {}", e.getMessage());
        }
        return page;
    }

    @RequestMapping(value = "/survey-stats.do",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取问卷统计数据")
    public HttpResult<Map<String, Map<String, AggregationResultItem>>> aggregationSearch(String surveyId){
        User user = accountManager.getCurUser();
        if(user!=null){
            SurveyDirectory survey = surveyDirectoryManager.get(surveyId);
            if(survey!=null){
                HttpResult httpResult = surveyDirectoryManager.isSurveyRoleOrPerm(user.getId(),survey.getUserId(), PermissionCode.HT_SURVEY_DATA_ANALYSIS);
                if(httpResult!=null) return httpResult;
                Map<String, Map<String, AggregationResultItem>> aggregationSearch = new HashMap<>();
                try{
                    aggregationSearch = dwAnswerEsClientService.aggregationSearch(surveyId);
                    return HttpResult.SUCCESS(aggregationSearch);
                } catch (Exception e){
                    logger.error("统计服务不可用 {}", e.getMessage());
                }
                return HttpResult.FAILURE_MSG("ES服务未启用，统计服务不可用");
            }
        }
        return HttpResult.FAILURE();
    }


    @RequestMapping(value="/export-by-sync.do",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "数据导出服务")
    public HttpResult<ExportLog> exportBySync(String surveyId, String expUpQu, Integer isEff, Integer handleState, Integer threadMax, Integer expDataContent) {
        logger.info("exportBySync surveyId {} ",surveyId);
        try{
//            String savePath = request.getSession().getServletContext().getRealPath("/");
            String savePath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
            User user=accountManager.getCurUser();
            if(user!=null){
                SurveyDirectory survey=surveyDirectoryManager.findUniqueBy(surveyId);
                if(survey!=null){
                    HttpResult httpResult = surveyDirectoryManager.isSurveyRoleOrPerm(user.getId(),survey.getUserId(), PermissionCode.HT_SURVEY_DATA_ANSWER_EXPORT);
                    if(httpResult!=null) return HttpResult.FAILURE_MSG("没有权限");
                    boolean isExpUpQu = false;
//                    List<AnUplodFile> anUplodFiles = anUploadFileManager.findAnswer(surveyId);
//                    if(anUplodFiles!=null && anUplodFiles.size()>0 && expUpQu!=null && "1".equals(expUpQu)) isExpUpQu = true;
//                    if (!user.getId().equals(survey.getUserId())) return HttpResult.FAILURE_MSG("没有权限");
                    if ("1".equals(expUpQu)) isExpUpQu = true;
                    ExportLog exportLog = surveyAnswerExportManager.buildExportXls(surveyId,savePath,threadMax,isExpUpQu?1:0, expDataContent);
                    String exportLogId = exportLog.getId();
                    surveyAnswerExportManager.exportLogXLS(surveyId,exportLogId,savePath,isExpUpQu,isEff,handleState);
                    return HttpResult.SUCCESS(exportLog);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return HttpResult.FAILURE_MSG(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/delete-answer.do",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除答卷数据")
    public HttpResult deleteAnswer(@RequestBody Map<String, String[]> map){
        try {
            if (map!=null && map.containsKey("id")) {
                String[] ids = map.get("id");
                logger.debug("deleteAnswer esid {}", ids);
                surveyAnswerManager.deleteData(ids);
                try{
                    if (map.containsKey("esId")) {
                        String[] esIds = map.get("esId");
                        esSurveyAnswerManager.deleteByIds(esIds);
                    }
                } catch (Exception e){
                    logger.error(e.getMessage());
                }
                return HttpResult.SUCCESS();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return HttpResult.EXCEPTION(e.getMessage());
        }
        return HttpResult.FAILURE();
    }
}
