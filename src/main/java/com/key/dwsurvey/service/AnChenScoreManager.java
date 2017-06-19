package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.AnChenScore;
import com.key.dwsurvey.entity.Question;

/**
 * 矩陈评分题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface AnChenScoreManager extends BaseService<AnChenScore, String>{
	public List<AnChenScore> findAnswer(String belongAnswerId,String quId);

	public void findGroupStats(Question question);
}
