package net.diaowen.dwsurvey.dao;

import net.diaowen.common.dao.BaseDao;
import net.diaowen.dwsurvey.entity.QuRadio;

public interface QuRadioDao extends BaseDao<QuRadio, String> {
	
	public void quOrderByIdDel1(String quId, Integer orderById);
	
}
