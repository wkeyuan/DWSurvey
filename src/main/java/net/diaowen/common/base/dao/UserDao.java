package net.diaowen.common.base.dao;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.dao.BaseDao;
import net.diaowen.common.plugs.page.Page;


/**
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface UserDao extends BaseDao<User, String> {

	public void resetUserGroup(String groupId);
	
}
