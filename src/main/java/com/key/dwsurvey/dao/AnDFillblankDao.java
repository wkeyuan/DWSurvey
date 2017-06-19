package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.AnDFillblank;
import com.key.dwsurvey.entity.Question;

/**
 * 多项填空题 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnDFillblankDao extends BaseDao<AnDFillblank, String>{

	public void findGroupStats(Question question);

}
