package net.diaowen.dwsurvey.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.dwsurvey.service.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.SystemOutLogger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.diaowen.common.base.dao.UserDao;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.common.utils.security.DigestUtils;


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

	@Transactional
	@Override
	public User adminSave(User entity) {
		if(entity!=null){
			String pwd=entity.getPwd();
			if(pwd!=null && !"".equals(pwd)){
				//加点盐
//				String salt=RandomUtils.randomWordNum(5);
				String shaPassword = DigestUtils.sha1Hex(pwd);
				entity.setShaPassword(shaPassword);
//				entity.setSalt(salt);
			}
			super.save(entity);
			return entity;
		}
		return null;
	}

	public Page<User> findPage(Page<User> page, User entity) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		Integer status = entity.getStatus();
		String loginName = entity.getLoginName();
		if(status!=null && !"".equals(status)){
			criterions.add(Restrictions.eq("status", status));
		}
		if(loginName!=null && !"".equals(loginName)){
			criterions.add(Restrictions.like("loginName", "%"+loginName+"%"));
		}
		criterions.add(Restrictions.disjunction().add(Restrictions.eq("visibility", 1)).add(Restrictions.isNull("visibility")));
		page.setOrderBy("createTime");
		page.setOrderDir("desc");
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

	/**
	 * 删除账号
	 */
	@Transactional
	@Override
	public void delete(String id) {
		User user=get(id);
		user.setVisibility(0);
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


	@Override
	public Page<User> findPageByKeyword(Page<User> page, String keyword) {
		Criterion cri1=Restrictions.like("name", "%"+keyword+"%");
//		Criterion cri2=Restrictions.like("pinyin", "%"+keyword+"%");
//		Criterion cri3 = Restrictions.or(cri1,cri2);
		return userDao.findPage(page,cri1);
	}

	public Page<User> findPage(Page<User> page, Integer status, String loginName, String name, String email,String cellphone) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(status!=null){
			criterions.add(Restrictions.eq("status", status));
		}
		if(StringUtils.isNotEmpty(loginName)){
			criterions.add(Restrictions.like("loginName", "%"+loginName+"%"));
		}
		if(StringUtils.isNotEmpty(name)){
			criterions.add(Restrictions.like("name", "%"+name+"%"));
		}
		if(StringUtils.isNotEmpty(email)){
			criterions.add(Restrictions.like("email", "%"+email+"%"));
		}
		if(StringUtils.isNotEmpty(cellphone)){
			criterions.add(Restrictions.like("cellphone", "%"+cellphone+"%"));
		}
		criterions.add(Restrictions.disjunction().add(Restrictions.eq("visibility", 1)).add(Restrictions.isNull("visibility")));
		if(StringUtils.isEmpty(page.getOrderBy())){
			page.setOrderBy("createTime");
			page.setOrderDir("desc");
		}
		return super.findPageByCri(page, criterions);
	}


	@Transactional
	@Override
	public HttpResult upData(User user) {
		if(user!=null){
			String id = user.getId();
			if(id!=null){
				User dbUser = getModel(id);
				dbUser.setLoginName(user.getLoginName());
				/*
				dbUser.setName(user.getName());
				dbUser.setEmail(user.getEmail());
				dbUser.setCellphone(user.getCellphone());
				*/
				dbUser.setStatus(user.getStatus());
				String pwd = user.getPwd();
				if(StringUtils.isNotEmpty(pwd)) {
					//加点盐
					String shaPassword = DigestUtils.sha1Hex(pwd);
					dbUser.setShaPassword(shaPassword);
				}
				super.save(dbUser);
				return HttpResult.SUCCESS();
			}
		}
		return HttpResult.FAILURE("user为null");
	}

	@Transactional
	@Override
	public void deleteData(String[] ids) {
		for (String id:ids) {
			userDao.delete(id);
		}
	}

	@Override
	public User findBySessionId(String servletSessionId) {
		return userDao.findUniqueBy("sessionId",servletSessionId);
	}

	@Override
	public User findByOpenId(String wxOpenId) {
		return userDao.findUniqueBy("wxOpenId",wxOpenId);
	}
}
