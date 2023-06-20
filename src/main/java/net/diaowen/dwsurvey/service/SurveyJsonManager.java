package net.diaowen.dwsurvey.service;

import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.AnAnswer;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyJson;

/**
 * 问卷JSON数据
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface SurveyJsonManager extends BaseService<SurveyJson, String> {

    void saveNew(SurveyJson surveyJson);

    SurveyJson findBySurveyId(String surveyId);
}
