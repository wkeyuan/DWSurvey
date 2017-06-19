package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.QuChenRow;

/**
 * 矩陈题行
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface QuChenRowManager extends BaseService<QuChenRow, String>{
	public List<QuChenRow> findByQuId(String quId);
	
	public String getContentByQuId(String quId);
	
	public QuChenRow upOptionName(String quId,String quItemId, String optionName);

	public void ajaxDelete(String quItemId);
}
