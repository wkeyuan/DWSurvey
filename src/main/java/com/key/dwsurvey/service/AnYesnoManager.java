package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.AnYesno;
import com.key.dwsurvey.entity.DataCross;
import com.key.dwsurvey.entity.Question;

/**
 * 是非题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface AnYesnoManager extends BaseService<AnYesno, String>{
	public AnYesno findAnswer(String belongAnswerId,String quId);

	public void findGroupStats(Question question);

	public List<DataCross> findStatsDataCross(Question rowQuestion, Question colQuestion);

	public List<DataCross> findStatsDataChart(Question question);
}
