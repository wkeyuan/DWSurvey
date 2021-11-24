package net.diaowen.dwsurvey.dao;

import java.util.List;

import net.diaowen.common.dao.BaseDao;
import net.diaowen.dwsurvey.entity.AnYesno;
import net.diaowen.dwsurvey.entity.DataCross;
import net.diaowen.dwsurvey.entity.Question;

/**
 * 是非题 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnYesnoDao extends BaseDao<AnYesno, String>{

	public void findGroupStats(Question question);

	public List<DataCross> findStatsDataCross(Question rowQuestion, Question colQuestion);

	public List<DataCross> findStatsDataChart(Question question);

}
