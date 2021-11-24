package net.diaowen.common.base.service;

import java.util.List;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.plugs.page.PageRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.diaowen.common.base.dao.UserDao;
import net.diaowen.common.exception.ServiceException;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.plugs.security.ShiroDbRealm;
import net.diaowen.common.utils.security.DigestUtils;

/**
 *
 * @author KeYuan
 * @date 2013下午10:22:04
 *
 */
@Service
public class AccountManager {

	private static Logger logger = LoggerFactory.getLogger(AccountManager.class);

	@Autowired
	private UserDao userDao;


//	@Autowired
//	private NotifyMessageProducer notifyMessageProducer;//JMS消息推送

	private ShiroDbRealm shiroRealm;

	/**
	 * 在保存用户时,发送用户修改通知消息, 由消息接收者异步进行较为耗时的通知邮件发送.
	 *
	 * 如果企图修改超级用户,取出当前操作员用户,打印其信息然后抛出异常.
	 *
	 */
	// 演示指定非默认名称的TransactionManager.
	@Transactional
	public void saveUser(User user) {
		if (isSupervisor(user)) {
			logger.warn("操作员{}尝试修改超级管理员用户", SecurityUtils.getSubject()
					.getPrincipal());
			throw new ServiceException("不能修改超级管理员用户");
		}
		//判断是否有重复用户
		String shaPassword = DigestUtils.sha1Hex(user.getPlainPassword());
		user.setShaPassword(shaPassword);
		boolean bool=user.getId()==null?true:false;
		userDao.save(user);
		if (shiroRealm != null) {
			shiroRealm.clearCachedAuthorizationInfo(user.getLoginName());
		}
		/*if(bool){
//			Email email=new Email();
//			sendNotifyMessage(email);	使用jms辅助 发送邮件
			simpleMailService.sendRegisterMailByAsync(user);
		}*/
	}

	@Transactional
	public void saveUp(User user){
		if (isSupervisor(user)) {
			logger.warn("操作员{}尝试修改超级管理员用户", SecurityUtils.getSubject().getPrincipal());
			throw new ServiceException("不能修改超级管理员用户");
		}
		userDao.save(user);
	}

	@Transactional
	public void updatePwd(String curpwd, String newPwd) {
		User user = getCurUser();
		if(user!=null){
			//判断是否有重复用户
			String curShaPassword = DigestUtils.sha1Hex(curpwd);
			if(user.getShaPassword().equals(curShaPassword)){
				String shaPassword = DigestUtils.sha1Hex(newPwd);
				user.setShaPassword(shaPassword);
				userDao.save(user);
			}
		}
	}
	/*public User getByUid(String userSource,String uid){
		Criterion cri1=Restrictions.eq("thirdSource", userSource);
		Criterion cri2=Restrictions.eq("thirdUid", uid);
		return userDao.findUnique(cri1,cri2);
	}*/
	//新注册用户，注册后
//	private void sendNotifyMessage(Email email) {
//		notifyMessageProducer.sendQueue(email);
//	}


	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(User user) {
//		return (user.getId() != null && user.getId() == 1L);
		return false;
	}

	@Transactional(readOnly = true)
	public User getUser(String id) {
		return userDao.get(id);
	}


	@Transactional(readOnly = true)
	public User findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	@Transactional(readOnly = true)
	public User findUserByLoginNameOrEmail(String loginName) {
		if(loginName!=null){
			if(loginName.contains("@")){
				//是邮箱账号
				return userDao.findUniqueBy("email", loginName);
			}else{
				return userDao.findUniqueBy("loginName", loginName);
			}
		}
		return null;
	}

	/*验证邮箱是否存在*/
	@Transactional(readOnly = true)
	public User findUserByEmail(String email){
		List<User> users=userDao.findBy("email", email);
		if(users!=null && users.size()>0){
			return users.get(0);
		}
		return null;
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userDao.isPropertyUnique("loginName", newLoginName, oldLoginName);
	}

	/**
	 * 取出当前登陆用户
	 */
	public User getCurUser(){
		Subject subject=SecurityUtils.getSubject();

		if(subject!=null){
			Object principal=subject.getPrincipal();
			if(principal!=null){
				User user = findUserByLoginName(principal.toString());
				return user;
			}
		}
		return null;
	}


}
