package net.diaowen.dwsurvey.service.impl;

import net.diaowen.common.utils.ReflectionUtils;
import net.diaowen.dwsurvey.service.SurveyStyleManager;
import net.diaowen.dwsurvey.dao.SurveyStyleDao;
import net.diaowen.dwsurvey.entity.SurveyStyle;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 问卷样式
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class SurveyStyleManagerImpl implements SurveyStyleManager {

	private final SurveyStyleDao surveyStyleDao;
	@Autowired
	public SurveyStyleManagerImpl(SurveyStyleDao surveyStyleDao) {
		this.surveyStyleDao = surveyStyleDao;
	}

	/**
	 * 根据风格 id 获取问卷风格
	 * @param id
	 * @return
	 */
	public SurveyStyle get(String id) {
		return surveyStyleDao.findUniqueBy("id", id);
	}

	/**
	 * 根据问卷 id 获取问卷风格
	 * @param surveyId
	 * @return
	 */
	public SurveyStyle getBySurveyId(String surveyId) {
		Criterion cri1 = Restrictions.eq("surveyId", surveyId);
		return surveyStyleDao.findFirst(cri1);
	}

	/**
	 * 保存问卷风格
	 * @param surveyStyle
	 */
	@Transactional
	public void save(SurveyStyle surveyStyle) {
		SurveyStyle upSurveyStyle = getBySurveyId(surveyStyle.getSurveyId());
		if (upSurveyStyle != null) {
			ReflectionUtils.copyAttr(surveyStyle, upSurveyStyle);
			surveyStyleDao.save(upSurveyStyle);
		} else {
			surveyStyleDao.save(surveyStyle);
		}
	}
}
