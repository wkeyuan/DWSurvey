package com.key.common.base.dao;

import com.key.common.base.entity.User;
import com.key.common.dao.BaseDao;
import com.key.common.plugs.page.Page;


/**
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface UserDao extends BaseDao<User, String> {

	public void resetUserGroup(String groupId);
	
}
