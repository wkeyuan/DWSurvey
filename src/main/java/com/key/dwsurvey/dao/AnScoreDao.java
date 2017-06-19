package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.AnScore;
import com.key.dwsurvey.entity.Question;

/**
 * 评分题 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnScoreDao extends BaseDao<AnScore, String>{

	public void findGroupStats(Question question);
	
}
