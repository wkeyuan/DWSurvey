package net.diaowen.dwsurvey.service.es;

import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.dwsurvey.entity.ExportLog;

import java.io.IOException;

public interface EsSurveyAnswerManager {

    public ExportLog buildExportXls(String surveyId, String savePath,Integer threadMax,Integer expUpQu, Integer expDataContent);

    public void exportLogXLS(final String surveyId,final String exportLogId,final String savePath,final Boolean isExpUpQu,final Integer isEff,final Integer handleState);

    public String exportXLS(String surveyId, String savePath, boolean isExpUpQu, Integer isEff, Integer handleState, ExportLog exportLog);

    HttpResult deleteByIds(String[] ids) throws IOException;
}
