package net.diaowen.dwsurvey.service.es.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.diaowen.common.QuType;
import net.diaowen.common.plugs.es.DwAnswerEsClientService;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.utils.RunExcelUtil;
import net.diaowen.common.utils.ZipUtil;
import net.diaowen.common.utils.excel.SXSSF_XLSXExportUtil;
import net.diaowen.common.utils.parsehtml.HtmlUtil;
import net.diaowen.dwsurvey.common.DwAnswerCheckResult;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.*;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswer;
import net.diaowen.dwsurvey.service.ExportLogManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.SurveyJsonManager;
import net.diaowen.dwsurvey.service.es.EsSurveyAnswerManager;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EsSurveyAnswerManagerImpl implements EsSurveyAnswerManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private DwAnswerEsClientService dwAnswerEsClientService;
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
    @Autowired
    private ExportLogManager exportLogManager;
    @Autowired
    private SurveyJsonManager surveyJsonManager;
    @Autowired
    private TaskExecutor taskExecutor;

    @Transactional
    public ExportLog buildExportXls(String surveyId, String savePath,Integer threadMax,Integer expUpQu, Integer expDataContent) {
        ExportLog exportLog = new ExportLog();
        exportLog.setParam1(surveyId);
        exportLog.setExportType(1);
        exportLog.setProgress(0);
        exportLog.setCreateDate(new Date());
        exportLog.setTitleTag(0);
        exportLog.setThreadMaxExportNum(threadMax);
        exportLog.setExpUpQu(expUpQu);
        exportLog.setExpDataContent(expDataContent);
        exportLogManager.save(exportLog);
        return exportLog;
    }

    public void exportLogXLS(final String surveyId,final String exportLogId,final String savePath,final Boolean isExpUpQu,final Integer isEff,final Integer handleState) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ExportLog exportLog = exportLogManager.findById(exportLogId);
                logger.info("exportLogXLS {}", JSON.toJSON(exportLog));
                try {
                    exportXLS(surveyId, savePath, isExpUpQu, isEff, handleState, exportLog);
                    updateExportLog(exportLog,1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String exportXLS(String surveyId, String savePath, boolean isExpUpQu, Integer isEff, Integer handleState, ExportLog exportLog) {
//		Date createDate = new Date();
//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
//		String basePath = surveyId + "/" + dateFormat.format(createDate);
        String urlPath = "/webin/expfile/" + surveyId + "/";// 下载所用的地址
        String path = urlPath.replace("/", File.separator);// 文件系统路径
        String fileName = "DWSurvey-"+surveyId + ".xlsx";
        if(exportLog!=null){
            exportLog.setParam2(path);//文件系统路径
            exportLog.setParam3(fileName);//文件名称
            exportLog.setParam4(urlPath + fileName);//下载所用的地址
            saveExportLog(exportLog);
        }

        urlPath = DWSurveyConfig.DWSURVEY_FILE_STORAGE_PREFIX+urlPath;// 下载所用的地址
        path = urlPath.replace("/", File.separator);// 文件系统路径
        savePath = savePath + path;
        File file = new File(savePath);
        if (!file.exists())
            file.mkdirs();
        //清空一下文件目录,防止重复文件
        FileUtil.deleteContents(file);

//        List<SurveyAnswer> answers = getSurveyAnswerManager().answerListByEff(surveyId, isEff, handleState);
//        List<Question> questions = questionManager.findDetails(surveyId,"2");

        JsonNode surveyJsonNode = getSurveyJsonNode(surveyId);
        JsonNode jsonNodeQus = null;
        if (surveyJsonNode!=null && surveyJsonNode.has("questions")) jsonNodeQus = surveyJsonNode.get("questions");

        int size = 6000;
        Page<DwEsSurveyAnswer> page = new Page<>();
        page.setPageSize(size);
        page = dwAnswerEsClientService.findPageByScroll(page, surveyId);

        List<SurveyAnswer> answers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        SXSSF_XLSXExportUtil exportUtil = new SXSSF_XLSXExportUtil(fileName, savePath);
        try {
            exportXLSTitle(exportUtil, jsonNodeQus);
            int answerListSize = Integer.parseInt(String.valueOf(page.getTotalItems()));
            logger.info("begin export answer {}",new Date());
            if(exportLog!=null && exportLog.getThreadMaxExportNum()!=null) size = exportLog.getThreadMaxExportNum();
            int expDataContent = 1;
            if(exportLog!=null && exportLog.getExpDataContent()!=null) expDataContent = exportLog.getExpDataContent();
            AtomicInteger ai=new AtomicInteger(0);

            int lastSize = answerListSize;
            int poolSize = 0;
            if(answerListSize>size){
                lastSize = answerListSize%size;
                poolSize = Integer.parseInt(String.valueOf(answerListSize / size));
            }

            //保证至少有一个
            ExecutorService exe = Executors.newFixedThreadPool(poolSize+1);
            logger.info("导出 countSize {} lastSize {} poolSize {} ",answerListSize,lastSize,lastSize>0?poolSize+1:poolSize);
            for (int index = 0; index<poolSize; index++ ){
                int beginIndex = index*size;
                int endIndex = beginIndex+size;
                logger.info("index {} beginIndex {} endIndex {}", index,beginIndex,endIndex);
                if (index>0) {
                    // 获取新的results
                    page = dwAnswerEsClientService.findPageByScrollId(page);
                }
                List<DwEsSurveyAnswer> pageResult = page.getResult();
                exe.execute(new RunExcelUtil(surveyId,exportUtil,pageResult,beginIndex,endIndex,savePath,isExpUpQu,ai,expDataContent, jsonNodeQus));
            }
            if(lastSize>0){
                int beginIndex = poolSize*size;
                int endIndex = beginIndex+lastSize;
                logger.info("lastSize {} beginIndex {} endIndex {}", lastSize,beginIndex,endIndex);
                if (poolSize>0) {
                    page = dwAnswerEsClientService.findPageByScrollId(page);
                }
                List<DwEsSurveyAnswer> pageResult = page.getResult();
                exe.execute(new RunExcelUtil(surveyId,exportUtil,pageResult,beginIndex,endIndex,savePath,isExpUpQu,ai,expDataContent, jsonNodeQus));
            }
            exe.shutdown();
            while (true) {
                updateExportProgress(answerListSize,ai.get(),exportLog);
                if (exe.isTerminated()) {
                    logger.info("XLS导出完成 {} {}",ai.get(),savePath);
                    exportUtil.exportXLS();
//					updateExportLog(exportLog,1);//在一下结束导出中执行
                    break;
                }
                Thread.sleep(200);
            }
            logger.info("end export answer {}",new Date());
            logger.info("end export answer getProgress {}", JSON.toJSONString(exportLog));
            Thread.sleep(1000);
            saveExportFile(surveyId,exportLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlPath + fileName;
    }

    private JsonNode getSurveyJsonNode(String surveyId) {
        SurveyJson surveyJson = surveyJsonManager.findBySurveyId(surveyId);
        ObjectMapper objectMapper = new ObjectMapper();
        DwAnswerCheckResult answerCheckResult = new DwAnswerCheckResult();
        try {
            JsonNode surveyJsonNode = objectMapper.readTree(surveyJson.getSurveyJsonText());
            return surveyJsonNode;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void  saveExportFile(String surveyId,ExportLog exportLog){
        if(exportLog!=null){
            //调用公共存储方法，保存到文件系统中，考虑是否有文件需要压缩
            Integer expUpQu = exportLog.getExpUpQu();
            String filePath = exportLog.getParam2();
            String fileName = exportLog.getParam3();
            String saveFilePath = null;
            if(expUpQu!=null && expUpQu==1){
                //原文件所在的目录
                String fromPath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH+DWSurveyConfig.DWSURVEY_FILE_STORAGE_PREFIX+filePath;
                fromPath = fromPath.replace("/", File.separator);

                //压缩到的目录
                String zipBasePath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH+DWSurveyConfig.DWSURVEY_FILE_STORAGE_PREFIX;
                String zipSaveFilePath = "/webin/zip/";
                String zipPath = (zipBasePath+zipSaveFilePath).replace("/", File.separator);
                File file = new File(zipPath);
                if (!file.exists()) file.mkdirs();

                String zipFileName = surveyId + ".zip";
                String toPath = zipPath + zipFileName;
                toPath = toPath.replace("/",File.separator);
                ZipUtil.createZip(fromPath, toPath, false);

                exportLog.setParam2(zipSaveFilePath);
                exportLog.setParam3(zipFileName);
                saveFilePath = zipSaveFilePath+zipFileName;

            }else{
                //直接存储xls
                saveFilePath = exportLog.getParam4();
            }
            exportLog.setParam4(saveFilePath);
            updateExportLog(exportLog,1);
        }
    }

    private void exportXLSTitle(SXSSF_XLSXExportUtil exportUtil,
                                JsonNode jsonNodeQus) {
        if (!jsonNodeQus.isNull() && jsonNodeQus.isArray()) {
            int jsonNodeSize = jsonNodeQus.size();
            exportUtil.createRow(0);
            int cellIndex = 0;
            int quNum=0;
            for (int i=0; i<jsonNodeSize; i++) {
                JsonNode jsonQuestion = jsonNodeQus.get(i);
                String quType = jsonQuestion.get("quType").asText();
                if (Objects.equals(quType, "PAGETAG") || Objects.equals(quType, "PARAGRAPH")) continue;
                quNum++;
                String quName = jsonQuestion.get("quTitleObj").get("dwText").asText();
                quName= HtmlUtil.removeTagFromText(quName);
                String titleName =quNum +"、" + quName + "[" + quType + "]";
                if (Objects.equals(quType, "RADIO")) {// 单选题
                    JsonNode jsonQuRadios=jsonQuestion.get("quRadios");
                    boolean isNote = false;
                    int optionSize = jsonQuRadios.size();
                    for (int j=0;j<optionSize;j++) {
                        JsonNode quRadio = jsonQuRadios.get(j);
                        if (quRadio.has("showOptionNote")) {
                            JsonNode showOptionNote = quRadio.get("showOptionNote");
                            if (showOptionNote.asInt() == 1) {
                                isNote = true;
                                break;
                            }
                        }
                    }
                    exportUtil.setCell(cellIndex++, titleName);
                    if(isNote) exportUtil.setCell(cellIndex++, titleName + "选项说明");
                } else if (Objects.equals(quType, "CHECKBOX")) {// 多选题
                    JsonNode jsonQuOptions=jsonQuestion.get("quCheckboxs");
                    int optionSize = jsonQuOptions.size();
                    for (int j=0; j<optionSize; j++) {
                        JsonNode jsonQuOption = jsonQuOptions.get(j);
                        String optionName = jsonQuOption.get("optionTitleObj").get("dwText").asText();
                        optionName=HtmlUtil.removeTagFromText(optionName);
                        exportUtil.setCell(cellIndex++, titleName + "－" + optionName);
                        if (jsonQuOption.has("showOptionNote")) {
                            JsonNode showOptionNote = jsonQuOption.get("showOptionNote");
                            if (showOptionNote!=null && showOptionNote.asInt() == 1) {
                                exportUtil.setCell(cellIndex++, titleName+ "－" + optionName  + "－选项说明");
                            }
                        }
                    }
                } else if (Objects.equals(quType, "FILLBLANK")) {// 填空题
                    exportUtil.setCell(cellIndex++, titleName);
                } else if (Objects.equals(quType, "MULTIFILLBLANK")) {// 组合填空题
                    JsonNode jsonQuOptions = jsonQuestion.get("quMultiFillblanks");
                    int optionSize = jsonQuOptions.size();
                    for (int j=0; j<optionSize; j++) {
                        JsonNode jsonQuOption = jsonQuOptions.get(j);
                        String optionName = jsonQuOption.get("optionTitleObj").get("dwText").asText();
                        optionName=HtmlUtil.removeTagFromText(optionName);
                        exportUtil.setCell(cellIndex++, titleName + "－" + optionName);
                    }
                } else if (Objects.equals(quType, "ORDERQU")) {// 评分题
                    JsonNode jsonQuOptions = jsonQuestion.get("quOrderbys");
                    int optionSize = jsonQuOptions.size();
                    for (int j=0; j<optionSize; j++) {
                        JsonNode jsonQuOption = jsonQuOptions.get(j);
                        String optionName = jsonQuOption.get("optionTitleObj").get("dwText").asText();
                        optionName=HtmlUtil.removeTagFromText(optionName);
                        exportUtil.setCell(cellIndex++, titleName + "－" + optionName);
                    }
                } else if (Objects.equals(quType, "SCORE")) {// 评分题
                    JsonNode jsonQuOptions = jsonQuestion.get("quScores");
                    int optionSize = jsonQuOptions.size();
                    for (int j=0; j<optionSize; j++) {
                        JsonNode jsonQuOption = jsonQuOptions.get(j);
                        String optionName = jsonQuOption.get("optionTitleObj").get("dwText").asText();
                        optionName=HtmlUtil.removeTagFromText(optionName);
                        exportUtil.setCell(cellIndex++, titleName + "－" + optionName);
                    }
                }
            }

            exportUtil.setCell(cellIndex++,  "回答者IP");
            exportUtil.setCell(cellIndex++,  "IP所在地");
            exportUtil.setCell(cellIndex++,  "回答时间");
            exportUtil.setCell(cellIndex++,  "时长（秒）");
            exportUtil.setCell(cellIndex++,  "审核");
            exportUtil.setCell(cellIndex++,  "ID");
            exportUtil.setCell(cellIndex++,  "AnUserKey");
            exportUtil.setCell(cellIndex++,  "用户名称");
            exportUtil.setCell(cellIndex++,  "用户账号");
        }

    }

    public void saveExportLog(ExportLog exportLog){
        exportLogManager.save(exportLog);
    }

    public void updateExportProgress(long answerListSize, int count,ExportLog exportLog) {
        if(count>0 && (count%5==0 || (count+30) > answerListSize)){
            float bfbProgress = (float)(count)/(float) (answerListSize+100);//大于100，让excel实际导完，但还要收尾工作让导出状态不至于提高结束
            logger.info("bfbProgress {}/{} {}",count,answerListSize,bfbProgress);
            updateExportLog(exportLog, bfbProgress);
        }
    }

    @Transactional
    public void updateExportLog(ExportLog exportLog, float bfbProgress) {
        if(exportLog!=null){
            exportLog.setEndDate(new Date());
            if(bfbProgress >=1){
                //用时计算
                Date createDate = exportLog.getCreateDate();
                Date endDate = exportLog.getEndDate();
                if(endDate!=null && createDate!=null){
                    long time=endDate.getTime()-createDate.getTime();
                    exportLog.setTotalTime(Float.parseFloat(time/1000+""));
                }
            }
            exportLog.setProgress(bfbProgress);
            exportLogManager.save(exportLog);
        }
    }
}
