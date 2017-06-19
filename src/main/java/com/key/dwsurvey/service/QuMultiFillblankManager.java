package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.QuMultiFillblank;

/**
 * 多项填空题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface QuMultiFillblankManager  extends BaseService<QuMultiFillblank, String>{

	public List<QuMultiFillblank> findByQuId(String quId);
	
	public QuMultiFillblank upOptionName(String quId,String quItemId, String optionName);

	public List<QuMultiFillblank> saveManyOptions(String quId,List<QuMultiFillblank> quMultiFillblanks);

	public void ajaxDelete(String quItemId);

	public void saveAttr(String quItemId);
}
