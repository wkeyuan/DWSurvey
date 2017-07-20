package com.key.dwsurvey.service.impl;

import com.key.dwsurvey.service.SurveyStyleManager;
import com.key.dwsurvey.dao.SurveyStyleDao;
import com.key.dwsurvey.entity.SurveyStyle;
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

	@Autowired
	private SurveyStyleDao surveyStyleDao;
	
	public SurveyStyle get(String id) {
		return surveyStyleDao.findUniqueBy("id", id);
	}
	
	public SurveyStyle getBySurveyId(String surveyId) {
		Criterion cri1=Restrictions.eq("surveyId", surveyId);
		return surveyStyleDao.findFirst(cri1);
	}
	
	@Transactional
	public void save(SurveyStyle surveyStyle){
		surveyStyleDao.save(surveyStyle);
	}
	
}
