package net.diaowen.dwsurvey.controller.design;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.utils.DwsUtils;
import net.diaowen.common.utils.RandomUtils;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.ExportLog;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.service.AnUploadFileManager;
import net.diaowen.dwsurvey.service.ExportLogManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 系统配置
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */

@Controller
@RequestMapping("/api/dwsurvey/app/v6/answer/export-log")
public class ExportLogController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ExportLogManager exportLogManager;
    @Autowired
    private AccountManager accountManager;
    @Autowired
    private SurveyDirectoryManager directoryManager;
    @Autowired
    private AnUploadFileManager anUploadFileManager;

	@RequestMapping(value = "/export-log-info.do",method = RequestMethod.GET)
	@ResponseBody
	public HttpResult info(String id) throws Exception {
		ExportLog exportLog = exportLogManager.findById(id);
		return HttpResult.SUCCESS(exportLog);
	}

    @RequestMapping(value = "/download-answer-xls.do",method = RequestMethod.GET)
    public HttpResult<ExportLog> downloadAnswerXls(HttpServletResponse response, String surveyId, String exportLogId) throws Exception{
        try{
            User user=accountManager.getCurUser();
            if(user!=null){
                SurveyDirectory survey=directoryManager.getSurvey(surveyId);
//                HttpResult httpResult = directoryManager.isSurveyRoleOrPerm(user.getId(), survey.getUserId(), PermissionCode.HT_SURVEY_DATA_ANSWER_EXPORT);
//                if(httpResult!=null){
//                    return HttpResult.FAILURE_MSG("没有权限");
//                }
                ExportLog exportLog = null;
                if(StringUtils.isNotEmpty(exportLogId)) exportLog = exportLogManager.findById(exportLogId);
                if(exportLog==null){
                    List<ExportLog> exportLogs = exportLogManager.findByParam1(surveyId,1);
                    if(exportLogs!=null){
                        for (ExportLog tempExportLog:exportLogs) {
                            if(tempExportLog.getProgress()==1){
                                exportLog = tempExportLog;
                                break;
                            }
                        }
                    }
                }
                if(exportLog!=null) downloadAnswerXls(response, surveyId, survey, exportLog);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.FAILURE();
    }

    private void downloadAnswerXls(HttpServletResponse response, String surveyId, SurveyDirectory survey, ExportLog exportLog) throws IOException {
        if(exportLog!=null && exportLog.getProgress()==1){
            String filePath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH + exportLog.getParam4();
            String fileName = exportLog.getParam3();
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
            String dateFormat = simpleDateFormat.format(new Date())+ RandomUtils.getVerifyCode();
            fileName = fileName + dateFormat;
            logger.info("Download Answer FilePath {}",filePath);
            logger.info("Download Answer fileName {}",fileName);
//                                response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode("dwsurvey"+survey.getSid()+dateFormName+".xlsx", "UTF-8"));
//                                request.getServletContext().getRequestDispatcher(path).forward(request,response);
            //考虑有上传文件的情况, 已经在导出方法内部考虑
            DwsUtils.downloadFile(response, fileName, filePath);
        }
    }


}
