package net.diaowen.dwsurvey.controller.design.admin;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.es.DwAnswerEsClientService;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.utils.ZipUtil;
import net.diaowen.dwsurvey.common.AggregationResultItem;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.AnUplodFile;
import net.diaowen.dwsurvey.entity.ExportLog;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswer;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.es.EsSurveyAnswerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/dwsurvey/app/v6/dw-answer-data-survey")
public class DwAnswerDataController {
    private static Logger logger = LoggerFactory.getLogger(DwAnswerDataController.class);
    @Resource
    private DwAnswerEsClientService dwAnswerEsClientService;
    @Autowired
    private AccountManager accountManager;
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;

    @RequestMapping(value = "/list.do",method = RequestMethod.GET)
    @ResponseBody
    public Page<DwEsSurveyAnswer> surveyJsonBySurveyId(Page<DwEsSurveyAnswer> page, String surveyId, String bgAnDate, String endAnDate, String ip, String city){
        logger.info("bgAnDate {}", bgAnDate);
        logger.info("endAnDate {}", endAnDate);
        return dwAnswerEsClientService.findPageByFromSize(page, surveyId, bgAnDate, endAnDate, ip, city, null, 0);
    }

    @RequestMapping(value = "/survey-stats.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<Map<String, Map<String, AggregationResultItem>>> aggregationSearch(String surveyId){
        return HttpResult.SUCCESS(dwAnswerEsClientService.aggregationSearch(surveyId));
    }

    @Autowired
    private EsSurveyAnswerManager esSurveyAnswerManager;
    @Autowired
    private SurveyAnswerManager surveyAnswerManager;
    @RequestMapping(value="/export-by-sync.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<ExportLog> exportBySync(String surveyId, String expUpQu, Integer isEff, Integer handleState, Integer threadMax, Integer expDataContent) {
        logger.info("exportBySync surveyId {} ",surveyId);
        try{
//            String savePath = request.getSession().getServletContext().getRealPath("/");
            String savePath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
            User user=accountManager.getCurUser();
            if(user!=null){
                SurveyDirectory survey=surveyDirectoryManager.findUniqueBy(surveyId);
                if(survey!=null){
//                    HttpResult httpResult = surveyDirectoryManager.isSurveyRoleOrPerm(user.getId(),survey.getUserId(), PermissionCode.HT_SURVEY_DATA_ANSWER_EXPORT);
//                    if(httpResult!=null) return HttpResult.FAILURE_MSG("没有权限");
                    boolean isExpUpQu = false;
//                    List<AnUplodFile> anUplodFiles = anUploadFileManager.findAnswer(surveyId);
//                    if(anUplodFiles!=null && anUplodFiles.size()>0 && expUpQu!=null && "1".equals(expUpQu)) isExpUpQu = true;
                    ExportLog exportLog = esSurveyAnswerManager.buildExportXls(surveyId,savePath,threadMax,isExpUpQu?1:0, expDataContent);
                    String exportLogId = exportLog.getId();
                    esSurveyAnswerManager.exportLogXLS(surveyId,exportLogId,savePath,isExpUpQu,isEff,handleState);
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
    public HttpResult deleteAnswer(@RequestBody Map<String, String[]> map){
        try {
            if (map!=null && map.containsKey("id")) {
                String[] ids = map.get("id");
                logger.debug("deleteAnswer esid {}", ids);
                dwAnswerEsClientService.deleteByIds(ids);
                return HttpResult.SUCCESS();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return HttpResult.EXCEPTION(e.getMessage());
        }
        return HttpResult.FAILURE();
    }
}
