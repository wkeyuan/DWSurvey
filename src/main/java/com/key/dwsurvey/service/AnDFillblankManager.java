package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.plugs.page.Page;
import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.AnDFillblank;
import com.key.dwsurvey.entity.Question;

/**
 * 多项填空题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface AnDFillblankManager extends BaseService<AnDFillblank, String>{
	public List<AnDFillblank> findAnswer(String belongAnswerId,String quId);

	public void findGroupStats(Question question);

	public Page<AnDFillblank> findPage(Page<AnDFillblank> page, String quItemId);

}
