package com.key.dwsurvey.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.key.dwsurvey.service.UserManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key.common.base.dao.UserDao;
import com.key.common.base.entity.User;
import com.key.common.plugs.page.Page;
import com.key.common.service.BaseServiceImpl;
import com.key.common.utils.security.DigestUtils;


/**
 * 用户管理
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class UserManagerImpl extends BaseServiceImpl<User, String> implements UserManager {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=userDao;
	}

	@Override
	public void adminSave(User entity, String[] userRoleIds) {
		if(entity!=null){
			
			String pwd=entity.getPwd();
			if(pwd!=null && !"".equals(pwd)){
				//加点盐
//				String salt=RandomUtils.randomWordNum(5);
				String shaPassword = DigestUtils.sha1Hex(pwd);
				entity.setShaPassword(shaPassword);
//				entity.setSalt(salt);
			}
			save(entity);
		}
	}

	public Page<User> findPage(Page<User> page, User entity) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		Integer status = entity.getStatus();
		String loginName = entity.getLoginName();
		criterions.add(Restrictions.eq("status", status));
		System.out.println("status:"+status);
		if(status!=null && !"".equals(status)){
			criterions.add(Restrictions.eq("status", status));
		}
		if(loginName!=null && !"".equals(loginName)){
			criterions.add(Restrictions.eq("loginName", loginName));
		}
		return super.findPageByCri(page, criterions);
	}

	/**
	 * 禁用账号
	 */
	@Transactional
	@Override
	public void disUser(String id) {
		User user=get(id);
		int status=user.getStatus();
		if(status==0){
			user.setStatus(1);	
		}else{
			user.setStatus(0);
		}
		save(user);
	}

	@Override
	public User findNameUn(String id, String loginName) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("loginName", loginName));
		if(id!=null && !"".equals(id)){
			criterions.add(Restrictions.ne("id", id));
		}
		return userDao.findFirst(criterions);
	}
	
	@Override
	public User findEmailUn(String id, String email) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("email", email));
		if(id!=null && !"".equals(id)){
			criterions.add(Restrictions.ne("id", id));	
		}
		return userDao.findFirst(criterions);
	}

	@Override
	public User findByCode(String code) {
		Criterion criterion=Restrictions.eq("findPwdCode", code);
		return userDao.findFirst(criterion);
	}
	
	@Override
	public User findByActivationCode(String activationCode) {
		Criterion criterion=Restrictions.eq("activationCode", activationCode);
		return userDao.findFirst(criterion);
	}
	
	@Override
	public void resetUserGroup(String groupId) {
		userDao.resetUserGroup(groupId);
	}
}
