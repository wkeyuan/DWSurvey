package com.key.dwsurvey.service;

import com.key.dwsurvey.entity.SurveyStyle;

/**
 * 问卷样式
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface SurveyStyleManager  {

	public SurveyStyle get(String id) ;
	
	public SurveyStyle getBySurveyId(String surveyId) ;
	
	public void save(SurveyStyle surveyStyle) ;
}
