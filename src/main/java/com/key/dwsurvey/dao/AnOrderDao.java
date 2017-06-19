package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.AnOrder;
import com.key.dwsurvey.entity.Question;

/**
 * 排序题 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnOrderDao extends BaseDao<AnOrder, String>{

	public void findGroupStats(Question question);
	
}
