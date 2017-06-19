package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.entity.AnChenCheckbox;

/**
 * 矩陈多选题数据 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnChenCheckboxDao extends BaseDao<AnChenCheckbox, String>{

	public void findGroupStats(Question question);

}
