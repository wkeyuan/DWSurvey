package net.diaowen.dwsurvey.service;

import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.ExportLog;

import java.util.List;

/**
 * 答卷业务
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface ExportLogManager extends BaseService<ExportLog, String> {

    public ExportLog findById(String id);

    public List<ExportLog> findByParam1(String surveyId,int exportType);

}
