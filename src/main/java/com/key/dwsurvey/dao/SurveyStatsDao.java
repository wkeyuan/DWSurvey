package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.entity.SurveyStats;

public interface SurveyStatsDao extends BaseDao<SurveyStats, String>{

	public void findStatsDataCross(Question rowQuestion, Question colQuestion);

}
