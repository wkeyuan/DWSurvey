package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.AnChenFbk;
import com.key.dwsurvey.entity.Question;

/**
 * 矩陈填空题数据 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnChenFbkDao extends BaseDao<AnChenFbk, String>{

	void findGroupStats(Question question);
	
}
