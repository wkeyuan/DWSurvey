package com.key.dwsurvey.dao;

import java.util.List;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.AnRadio;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.entity.DataCross;

/**
 * 单选题 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnRadioDao extends BaseDao<AnRadio, String> {

	public void findGroupStats(Question question);

	public List<DataCross> findStatsDataCross(Question rowQuestion,
			Question colQuestion);

	public List<DataCross> findStatsDataChart(Question question);

}
