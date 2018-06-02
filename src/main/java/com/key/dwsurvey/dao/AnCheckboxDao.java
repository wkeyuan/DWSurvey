package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.AnCheckbox;
import com.key.dwsurvey.entity.DataCross;
import com.key.dwsurvey.entity.Question;

import java.util.List;


/**
 * 多选题数据 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnCheckboxDao extends BaseDao<AnCheckbox, String>{

	public void findGroupStats(Question question);

	public List<DataCross> findStatsDataCross(Question rowQuestion,
                                              Question colQuestion);

	public List<DataCross> findStatsDataChart(Question question);

}
