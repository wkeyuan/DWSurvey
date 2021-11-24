package net.diaowen.dwsurvey.service;

import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.SurveyDetail;

/**
 * 问卷评情
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface SurveyDetailManager extends BaseService<SurveyDetail, String>{
	
	public SurveyDetail getBySurveyId(String surveyId);
	
}
