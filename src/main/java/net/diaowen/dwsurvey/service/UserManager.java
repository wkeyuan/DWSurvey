package net.diaowen.dwsurvey.service;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.service.BaseService;

public interface UserManager extends BaseService<User, String>{

	public void adminSave(User entity, String[] userRoleIds);

	public Page<User> findPage(Page<User> page, User entity);

	public void disUser(String id);

	public User findNameUn(String id, String loginName);

	public User findEmailUn(String id, String email);

	public User findByCode(String code);

	public User findByActivationCode(String code);

	public void resetUserGroup(String groupId);

	public Page<User> findPageByKeyword(Page<User> page, String keyword);

}
