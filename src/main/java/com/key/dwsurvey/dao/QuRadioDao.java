package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.QuRadio;

public interface QuRadioDao extends BaseDao<QuRadio, String> {
	
	public void quOrderByIdDel1(String quId,Integer orderById);
	
}
