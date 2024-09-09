package net.diaowen.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.json.Json;
import net.diaowen.common.QuType;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.utils.excel.SXSSF_XLSXExportUtil;
import net.diaowen.common.utils.parsehtml.HtmlUtil;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.*;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswer;
import net.diaowen.dwsurvey.entity.es.answer.question.EsAnQuestion;
import net.diaowen.dwsurvey.entity.es.answer.question.option.*;
import net.diaowen.dwsurvey.service.QuestionManager;
import net.diaowen.dwsurvey.service.impl.QuestionManagerImpl;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class RunExcelUtil extends Thread {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String surveyId;
    private SXSSF_XLSXExportUtil exportUtil;
    private SXSSFSheet sheet;
    private List<DwEsSurveyAnswer> answers;
    private int beginIndex;
    private int endIndex;
    private String savePath;
    private boolean isExpUpQu;
    private AtomicInteger ai;
    private int expDataContent;

    private JsonNode jsonNodeQus;

    RunExcelUtil(){

    }

    public RunExcelUtil(String surveyId, SXSSF_XLSXExportUtil exportUtil, List<DwEsSurveyAnswer> answers, int beginIndex, int endIndex, String savePath, boolean isExpUpQu, AtomicInteger ai, int expDataContent, JsonNode jsonNodeQus){
        this.surveyId = surveyId;
        this.exportUtil = exportUtil;
        this.sheet = exportUtil.getSheet();
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.answers = answers;
        this.savePath = savePath;
        this.isExpUpQu = isExpUpQu;
        this.ai = ai;
        this.expDataContent = expDataContent;
        this.jsonNodeQus = jsonNodeQus;
    }

    @Override
    public void run() {
        for (int j = beginIndex; j < endIndex; j++) {
            DwEsSurveyAnswer dwEsSurveyAnswer = answers.get(j);
            String surveyAnswerId = dwEsSurveyAnswer.getEsId();
            exportUtil.createNewRow(j+1);
            exportXLSRow(surveyAnswerId, jsonNodeQus, dwEsSurveyAnswer, (j+1), savePath, isExpUpQu);
            int exportCount = ai.addAndGet(1);
            //logger.info("export ai {}",exportCount);
        }
        logger.info("export finish row {} - {}", this.beginIndex, this.endIndex);
    }

    private void exportXLSRow(String surveyAnswerId, JsonNode jsonNodeQus,DwEsSurveyAnswer dwEsSurveyAnswer,int rowIndex, String savePath, boolean isExpUpQu) {
        int cellIndex = 0;
        int quNum=0;
        SXSSFRow row = exportUtil.getRow(rowIndex);
        int jsonNodeSize = jsonNodeQus.size();
        List<EsAnQuestion> anQuestionList = dwEsSurveyAnswer.getAnQuestions();
        exportUtil.setCell(row, cellIndex++, String.valueOf(rowIndex));
        for (int i=0; i<jsonNodeSize; i++) {
            JsonNode jsonQuestion = jsonNodeQus.get(i);
            String quType = jsonQuestion.get("quType").asText();
            if(Objects.equals(quType, "PAGETAG") || Objects.equals(quType, "PARAGRAPH")) continue;
            quNum++;
            String quDwId = jsonQuestion.get("dwId").asText();
            EsAnQuestion esAnQuestion = getAnQuestion(quDwId, anQuestionList);
            if (Objects.equals(quType, "RADIO")) {// 单选题
//                String quItemId = question.getAnRadio().getQuItemId();
//                List<QuRadio> quRadios = question.getQuRadios();
                JsonNode quRadios=jsonQuestion.get("quRadios");
                String answerOptionName="";
                String answerOtherText="";
                Float answerScore = 0f;
                boolean isNote = false;
                int optionSize = quRadios.size();
                for (int j=0;j<optionSize;j++) {
                    JsonNode quRadio = quRadios.get(j);
                    if (quRadio.has("showOptionNote")) {
                        JsonNode showOptionNote = quRadio.get("showOptionNote");
                        if (showOptionNote.asInt() == 1) {
                            isNote = true;
                            break;
                        }
                    }
                }

                String quItemId = null;
                if (esAnQuestion!=null) {
                    EsAnRadio esAnRadio = esAnQuestion.getAnRadio();
                    if (esAnRadio!=null) quItemId = esAnRadio.getOptionDwId();
                }

                for (int j=0;j<optionSize;j++) {
                    JsonNode jsonQuRadio = quRadios.get(j);
                    String quRadioId= jsonQuRadio.get("dwId").asText();
                    if(quRadioId.equals(quItemId)){
                        answerOptionName = jsonQuRadio.get("optionTitleObj").get("dwText").asText();
//                        answerScore = quRadio.getScoreNum();
                        answerOtherText = esAnQuestion.getAnRadio().getOtherText();
                        break;
                    }
                }
                answerOptionName = HtmlUtil.removeTagFromText(answerOptionName);
                answerOptionName = answerOptionName.replace("&nbsp;"," ");
                if (answerScore==null) answerScore = 0f;
                if (expDataContent==2) answerOptionName = String.valueOf(answerScore);
                exportUtil.setCell(row, cellIndex++, answerOptionName);
                if(isNote) exportUtil.setCell(row, cellIndex++, answerOtherText);
            } else if (Objects.equals(quType, "CHECKBOX")) {// 多选题
                List<EsAnCheckbox> anCheckboxs = null;
                if (esAnQuestion!=null) anCheckboxs= esAnQuestion.getAnCheckboxs();
                JsonNode checkboxs = jsonQuestion.get("quCheckboxs");
                int optionSize = checkboxs.size();
                for (int j=0;j<optionSize;j++) {
                    JsonNode quCheckbox = checkboxs.get(j);
                    String quCkId = quCheckbox.get("dwId").asText();
                    String answerOptionName="0";
                    String answerOtherText="";
                    Float answerScore = 0f;
                    boolean isNote = false;
                    if (quCheckbox.has("showOptionNote")) {
                        JsonNode showOptionNote = quCheckbox.get("showOptionNote");
                        if (showOptionNote!=null && showOptionNote.asInt() == 1) {
                            isNote = true;
                        }
                    }
                    if (anCheckboxs!=null) {
                        for (EsAnCheckbox anCheckbox : anCheckboxs) {
                            String anQuItemId=anCheckbox.getOptionDwId();
                            if(quCkId.equals(anQuItemId)){
                                answerOptionName = quCheckbox.get("optionTitleObj").get("dwText").asText();
                                answerOptionName="1";
//                            answerScore = quCheckbox.getScoreNum();
                                if (isNote) answerOtherText = anCheckbox.getOtherText();
                                break;
                            }
                        }
                    }

                    answerOptionName=HtmlUtil.removeTagFromText(answerOptionName);
                    answerOptionName = answerOptionName.replace("&nbsp;"," ");
                    if (answerScore==null) answerScore = 0f;
                    if (expDataContent==2) answerOptionName = String.valueOf(answerScore);
                    exportUtil.setCell(row, cellIndex++, answerOptionName);
                    if(isNote) exportUtil.setCell(row, cellIndex++, answerOtherText);
                }
            } else if (Objects.equals(quType, "FILLBLANK")) {// 填空题
                String answer = "";
                if (esAnQuestion!=null && esAnQuestion.getAnFbk()!=null) {
                    answer = esAnQuestion.getAnFbk().getAnswer();
                }
                exportUtil.setCell(row, cellIndex++, answer);
            } else if (Objects.equals(quType, "ORDERQU")) {// 评分题
                JsonNode quOrderbys = jsonQuestion.get("quOrderbys");
                List<EsAnOrder> anOrders = null;
                if (esAnQuestion!=null) anOrders = esAnQuestion.getAnOrders();
                for (int j=0;j<quOrderbys.size();j++) {
                    JsonNode quOrder = quOrderbys.get(j);
                    String quOrderbyId=quOrder.get("dwId").asText();
                    String answerOptionName="";
                    if (anOrders!=null) {
                        for (EsAnOrder anOrder : anOrders) {
                            if(quOrderbyId.equals(anOrder.getOptionDwId())) {
                                answerOptionName = anOrder.getOrderNum();
                                break;
                            }
                        }
                    }
                    answerOptionName = answerOptionName.replace("&nbsp;"," ");
                    exportUtil.setCell(row, cellIndex++, answerOptionName);
                }
            } else if (Objects.equals(quType, "MULTIFILLBLANK")) {// 组合填空题
                JsonNode quMultiFillblanks = jsonQuestion.get("quMultiFillblanks");
                List<EsAnFbk> anDFillblanks = null;
                if (esAnQuestion!=null) anDFillblanks = esAnQuestion.getAnMFbks();
                for (int j=0;j<quMultiFillblanks.size();j++) {
                    JsonNode quMultiFillblank = quMultiFillblanks.get(j);
                    String quMultiFillbankId=quMultiFillblank.get("dwId").asText();
                    String answerOptionName="";
                    if (anDFillblanks!=null) {
                        for (EsAnFbk anDFillblank : anDFillblanks) {
                            if(quMultiFillbankId.equals(anDFillblank.getOptionDwId())){
                                answerOptionName=anDFillblank.getAnswer();
                                break;
                            }
                        }
                    }
                    answerOptionName = answerOptionName.replace("&nbsp;"," ");
                    exportUtil.setCell(row, cellIndex++, answerOptionName);
                }
            } else if (Objects.equals(quType, "SCORE")) {// 评分题
                JsonNode quScores = jsonQuestion.get("quScores");
                List<EsAnScore> anScores = null;
                if (esAnQuestion!=null) anScores = esAnQuestion.getAnScores();
                for (int j=0;j<quScores.size();j++) {
                    JsonNode quScore = quScores.get(j);
                    String quScoreId = quScore.get("dwId").asText();
                    String answerScore="";
                    if (anScores!=null) {
                        for (EsAnScore anScore : anScores) {
                            if(quScoreId.equals(anScore.getOptionDwId())){
                                answerScore=anScore.getAnswerScore();
                                break;
                            }
                        }
                    }
                    exportUtil.setCell(row, cellIndex++, answerScore);
                }
            } else if (Objects.equals(quType, "UPLOADFILE")) {
                //为导出文件
                String upFilePath = File.separator + "webin" + File.separator + "upload" + File.separator;
                List<EsAnUploadFile> anUplodFiles = null;
                if (esAnQuestion!=null) anUplodFiles = esAnQuestion.getAnUploadFiles();
                StringBuilder answerBuf = new StringBuilder();
                String toFilePath = "";
                if(isExpUpQu){
//					String toFilePath = savePath+File.separator+orderNum+File.separator+HtmlUtil.removeTagFromText(titleName);
//					String toFilePath = savePath + File.separator + orderNum + File.separator + quNum + "_" + HtmlUtil.removeTagFromText(titleName);
                    toFilePath = savePath + File.separator + rowIndex + File.separator + "Q_" + quNum;
                    File file = new File(toFilePath);
                    if (!file.exists()) file.mkdirs();
                }
                if (anUplodFiles!=null) {
                    for (EsAnUploadFile anUplodFile : anUplodFiles) {
                        answerBuf.append(rowIndex).append("/Q_").append(quNum).append("/").append(anUplodFile.getFileName()).append("      ");
                        if(isExpUpQu){
                            File fromFile = new File(DWSurveyConfig.DWSURVEY_WEB_FILE_PATH + anUplodFile.getFilePath());
                            if (fromFile.exists()) {
                                File toFile = new File(toFilePath + File.separator + anUplodFile.getFileName());
                                try {
                                    FileUtil.copyFile(fromFile, toFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
//				answerBuf=answerBuf.substring(0,answerBuf.lastIndexOf("      "));
                exportUtil.setCell(row, cellIndex++, answerBuf.toString());
            } else if (Objects.equals(quType, "MATRIX_RADIO")) {// 评分题
                List<EsAnMatrixRadio> anMatrixRadios = null;
                if (esAnQuestion!=null) anMatrixRadios = esAnQuestion.getAnMatrixRadios();
                JsonNode jsonQuOptions=jsonQuestion.get("quRows");
                JsonNode jsonQuOptionCols=jsonQuestion.get("quCols");
                int optionSize = jsonQuOptions.size();
                int optionColSize = jsonQuOptionCols.size();
                for (int j=0; j<optionSize; j++) {
                    JsonNode jsonQuOption = jsonQuOptions.get(j);
                    String quChenRowId=jsonQuOption.get("dwId").asText();
                    String answerColOptionName="";
                    boolean breakTag=false;
                    for (int k=0; k<optionColSize; k++) {
                        JsonNode jsonQuOptionCol = jsonQuOptionCols.get(k);
                        String quChenColumnId=jsonQuOptionCol.get("dwId").asText();
                        String colName = jsonQuOptionCol.get("optionTitleObj").get("dwText").asText();
                        if (anMatrixRadios!=null) {
                            for (EsAnMatrixRadio esAnMatrixRadio:anMatrixRadios) {
                                String anQuRowId=esAnMatrixRadio.getRowDwId();
                                String anQuColId=esAnMatrixRadio.getColDwId();
                                if(quChenRowId.equals(anQuRowId) && quChenColumnId.equals(anQuColId)){
                                    breakTag=true;
                                    break;
                                }
                            }
                            if(breakTag){
                                answerColOptionName = colName;
                                break;
                            }
                        }
                    }
                    answerColOptionName=HtmlUtil.removeTagFromText(answerColOptionName);
                    answerColOptionName = answerColOptionName.replace("&nbsp;"," ");
                    exportUtil.setCell(row, cellIndex++, answerColOptionName);
                }
            } else if (Objects.equals(quType, "MATRIX_CHECKBOX")) {// 矩阵单选题
                List<EsAnMatrixCheckbox> anMatrixCheckboxes = null;
                if (esAnQuestion!=null) anMatrixCheckboxes = esAnQuestion.getAnMatrixCheckboxes();
                JsonNode jsonQuOptions=jsonQuestion.get("quRows");
                JsonNode jsonQuOptionCols=jsonQuestion.get("quCols");
                int optionSize = jsonQuOptions.size();
                int optionColSize = jsonQuOptionCols.size();
                for (int j=0; j<optionSize; j++) {
                    JsonNode jsonQuOption = jsonQuOptions.get(j);
                    String quChenRowId=jsonQuOption.get("dwId").asText();
                    for (int k=0; k<optionColSize; k++) {
                        JsonNode jsonQuOptionCol = jsonQuOptionCols.get(k);
                        if (!jsonQuOptionCol.has("tempEmptyOption")  || (jsonQuOptionCol.has("tempEmptyOption") && !jsonQuOptionCol.get("tempEmptyOption").asBoolean())) {
                            String quChenColumnId=jsonQuOptionCol.get("dwId").asText();
                            String colName = jsonQuOptionCol.get("optionTitleObj").get("dwText").asText();
                            String answerOptionName = "";
                            if (anMatrixCheckboxes!=null) {
                                for (EsAnMatrixCheckbox esAnMatrixCheckbox:anMatrixCheckboxes) {
                                    String anQuRowId=esAnMatrixCheckbox.getRowDwId();
                                    List<EsAnCheckbox> rowAnCheckboxs=esAnMatrixCheckbox.getRowAnCheckboxs();
                                    for (EsAnCheckbox esAnCheckbox: rowAnCheckboxs) {
                                        String anQuColId = esAnCheckbox.getOptionDwId();
                                        if (quChenRowId.equals(anQuRowId) && quChenColumnId.equals(anQuColId)) {
                                            answerOptionName = colName;
                                            break;
                                        }
                                    }
                                }
                            }
                            answerOptionName=HtmlUtil.removeTagFromText(answerOptionName);
                            answerOptionName = answerOptionName.replace("&nbsp;"," ");
                            exportUtil.setCell(row, cellIndex++, answerOptionName);
                        }
                    }
                }
            } else if (Objects.equals(quType, "MATRIX_INPUT")) {
                List<EsAnMatrixFbk> esAnMatrixFbks = null;
                if (esAnQuestion!=null) esAnMatrixFbks = esAnQuestion.getAnMatrixFbks();
                JsonNode jsonQuOptions=jsonQuestion.get("quRows");
                JsonNode jsonQuOptionCols=jsonQuestion.get("quCols");
                int optionSize = jsonQuOptions.size();
                int optionColSize = jsonQuOptionCols.size();
                for (int j=0; j<optionSize; j++) {
                    JsonNode jsonQuOption = jsonQuOptions.get(j);
                    String quChenRowId=jsonQuOption.get("dwId").asText();
                    for (int k=0; k<optionColSize; k++) {
                        JsonNode jsonQuOptionCol = jsonQuOptionCols.get(k);
                        if (!jsonQuOptionCol.has("tempEmptyOption")  || (jsonQuOptionCol.has("tempEmptyOption") && !jsonQuOptionCol.get("tempEmptyOption").asBoolean())) {
                            String quChenColumnId=jsonQuOptionCol.get("dwId").asText();
                            String colName = jsonQuOptionCol.get("optionTitleObj").get("dwText").asText();
                            String answerOptionName = "";
                            if (esAnMatrixFbks!=null) {
                                for (EsAnMatrixFbk esAnMatrixFbk: esAnMatrixFbks) {
                                    String anQuRowId=esAnMatrixFbk.getRowDwId();
                                    List<EsAnFbk> rowAnCheckboxs=esAnMatrixFbk.getRowAnFbks();
                                    for (EsAnFbk esAnFbk: rowAnCheckboxs) {
                                        String anQuColId = esAnFbk.getOptionDwId();
                                        if (quChenRowId.equals(anQuRowId) && quChenColumnId.equals(anQuColId)) {
                                            answerOptionName = colName;
                                            break;
                                        }
                                    }
                                }
                            }
                            answerOptionName=HtmlUtil.removeTagFromText(answerOptionName);
                            answerOptionName = answerOptionName.replace("&nbsp;"," ");
                            exportUtil.setCell(row, cellIndex++, answerOptionName);
                        }
                    }
                }
            } else if (Objects.equals(quType, "MATRIX_SCALE") || Objects.equals(quType, "MATRIX_SLIDER")) {
                List<EsAnMatrixScale> esAnMatrixScales = null;
                if (esAnQuestion!=null) esAnMatrixScales = esAnQuestion.getAnMatrixScales();
                JsonNode jsonQuOptions=jsonQuestion.get("quRows");
                int optionSize = jsonQuOptions.size();
                for (int j=0; j<optionSize; j++) {
                    JsonNode jsonQuOption = jsonQuOptions.get(j);
                    String quChenRowId=jsonQuOption.get("dwId").asText();
                    String answerColOptionName="";
                    if (esAnMatrixScales!=null) {
                        for (EsAnMatrixScale anScale:esAnMatrixScales) {
                            String quItemId = anScale.getRowDwId();
                            if(quChenRowId.equals(quItemId)){
                                answerColOptionName = anScale.getAnswerScore();
                                break;
                            }
                        }
                    }
                    exportUtil.setCell(row, cellIndex++, answerColOptionName);
                }
            }

        }

        exportUtil.setCell(row, cellIndex++,  dwEsSurveyAnswer.getAnswerCommon().getAnIp().getIp());
        exportUtil.setCell(row, cellIndex++,  dwEsSurveyAnswer.getAnswerCommon().getAnIp().getCity());
        exportUtil.setCell(row, cellIndex++,  new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(dwEsSurveyAnswer.getAnswerCommon().getAnTime().getEndAnDate()));
        exportUtil.setCell(row, cellIndex++, String.valueOf(dwEsSurveyAnswer.getAnswerCommon().getAnTime().getTotalTime() / 1000));

        Integer handleState = dwEsSurveyAnswer.getAnswerCommon().getAnState().getHandleState();
        String handleStateStr = "未操作";
        if(handleState!=null){
            if(handleState==1) handleStateStr = "通过";
            if(handleState==2) handleStateStr = "未通过";
            if(handleState==300) handleStateStr = "删除";
        }
        exportUtil.setCell(row, cellIndex++,  handleStateStr);
        exportUtil.setCell(row, cellIndex++,  dwEsSurveyAnswer.getEsId());
//        exportUtil.setCell(row, cellIndex++,  surveyAnswer.getAnUserKey());
        String userName = "";
        String loginName = "";
        /*User answerUser = surveyAnswer.getAnswerUser();
        if(answerUser!=null){
            userName = answerUser.getName();
            loginName = answerUser.getLoginName();
        }*/
        exportUtil.setCell(row, cellIndex++,  userName);
        exportUtil.setCell(row, cellIndex++,  loginName);

    }

    private EsAnQuestion getAnQuestion(String quDwId,List<EsAnQuestion> anQuestions) {
        for (EsAnQuestion esAnQuestion: anQuestions) {
            if (quDwId.equals(esAnQuestion.getQuDwId())) {
                return esAnQuestion;
            }
        }
        return null;
    }

}
