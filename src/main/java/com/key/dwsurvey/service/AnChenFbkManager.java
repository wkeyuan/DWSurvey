package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.AnChenFbk;
import com.key.dwsurvey.entity.Question;

/**
 * 矩陈填空题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface AnChenFbkManager extends BaseService<AnChenFbk, String>{
	public List<AnChenFbk> findAnswer(String belongAnswerId,String quId);
	
	public void findGroupStats(Question question);
}
