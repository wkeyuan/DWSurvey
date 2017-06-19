package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.AnChenScore;
import com.key.dwsurvey.entity.Question;

/**
 * 矩陈评分题 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnChenScoreDao extends BaseDao<AnChenScore, String>{

	public void findGroupStats(Question question);

}
