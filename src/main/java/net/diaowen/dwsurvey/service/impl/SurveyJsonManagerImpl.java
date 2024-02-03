package net.diaowen.dwsurvey.service.impl;

import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.dwsurvey.dao.AnAnswerDao;
import net.diaowen.dwsurvey.dao.SurveyJsonDao;
import net.diaowen.dwsurvey.entity.AnAnswer;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyJson;
import net.diaowen.dwsurvey.service.AnAnswerManager;
import net.diaowen.dwsurvey.service.SurveyJsonManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author keyuan
 * keyuan258@gmail.com
 *
 */

@Service
public class SurveyJsonManagerImpl extends BaseServiceImpl<SurveyJson, String> implements SurveyJsonManager {

	@Autowired
	private SurveyJsonDao surveyJsonDao;

	@Override
	public void setBaseDao() {
		this.baseDao=surveyJsonDao;
	}

	@Override
	public void saveNew(SurveyJson surveyJson) {
		/*String surveyId = surveyJson.getSurveyId();
		SurveyJson surveyJsonDb = findBySurveyId(surveyId);
		if(surveyJsonDb!=null){
			surveyJsonDb.setSurveyJsonText(surveyJson.getSurveyJsonText());
			surveyJsonDb.setSaveDate(new Date());
			super.save(surveyJson);
		}else{
			surveyJson.setSaveDate(new Date());
			super.save(surveyJson);
		}*/
		//如果这样直接保存，则需要考虑库中保存的历史数据可能过多，需要定量清除
		surveyJson.setSaveDate(new Date());
		super.save(surveyJson);
		//每份问卷仅保留最近的1000次操作记录
		//前端每30秒检查下问卷是否有变动，如果有变动则自动保存一次并生成一次历史记录
	}

	public SurveyJson findBySurveyId(String surveyId) {
		Criterion cri1 = Restrictions.eq("surveyId",surveyId);
		return surveyJsonDao.findFirst("saveDate",false, cri1);
	}
}
