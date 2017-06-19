package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.AnAnswer;
import com.key.dwsurvey.entity.Question;

/**
 * 答卷数据 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnAnswerDao extends BaseDao<AnAnswer, String> {

	public void findGroupStats(Question question);

}
