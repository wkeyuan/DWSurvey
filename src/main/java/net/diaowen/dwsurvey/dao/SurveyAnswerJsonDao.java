package net.diaowen.dwsurvey.dao;

import net.diaowen.common.dao.BaseDao;
import net.diaowen.dwsurvey.entity.SurveyAnswerJson;

public interface SurveyAnswerJsonDao extends BaseDao<SurveyAnswerJson, String> {

    public int countByJsonKey(String surveyId,String keyword);

}
