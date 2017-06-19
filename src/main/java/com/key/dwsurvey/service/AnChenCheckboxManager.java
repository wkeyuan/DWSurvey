package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.entity.AnChenCheckbox;

/**
 * 矩陈多选题业务
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface AnChenCheckboxManager extends BaseService<AnChenCheckbox, String> {
	public List<AnChenCheckbox> findAnswer(String belongAnswerId,String quId);

	public void findGroupStats(Question question);
}
