package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.entity.AnEnumqu;

/**
 * 枚举题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface AnEnumquManager extends BaseService<AnEnumqu, String> {
	public  List<AnEnumqu> findAnswer(String belongAnswerId,String quId);

	public void findGroupStats(Question question);
}
