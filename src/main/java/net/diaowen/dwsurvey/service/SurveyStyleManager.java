package net.diaowen.dwsurvey.service;

import net.diaowen.dwsurvey.entity.SurveyStyle;

/**
 * 问卷样式
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface SurveyStyleManager  {
	/**
	 * 根据风格 id 获取问卷风格
	 * @param id
	 * @return
	 */
	public SurveyStyle get(String id) ;
	/**
	 * 根据问卷 id 获取问卷风格
	 * @param surveyId
	 * @return
	 */
	public SurveyStyle getBySurveyId(String surveyId) ;
	/**
	 * 保存问卷风格
	 * @param surveyStyle
	 */
	public void save(SurveyStyle surveyStyle) ;
}
