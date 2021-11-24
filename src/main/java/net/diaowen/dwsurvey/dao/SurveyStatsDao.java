package net.diaowen.dwsurvey.dao;

import net.diaowen.common.dao.BaseDao;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyStats;

public interface SurveyStatsDao extends BaseDao<SurveyStats, String>{

	public void findStatsDataCross(Question rowQuestion, Question colQuestion);

}
