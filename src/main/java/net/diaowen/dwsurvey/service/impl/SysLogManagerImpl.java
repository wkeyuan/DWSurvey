package net.diaowen.dwsurvey.service.impl;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.dwsurvey.common.SysLogUtils;
import net.diaowen.dwsurvey.dao.SysLogDao;
import net.diaowen.dwsurvey.entity.SysLog;
import net.diaowen.dwsurvey.service.SysLogManager;
import net.diaowen.dwsurvey.service.UserManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keyuan
 * keyuan258@gmail.com
 *
 */

@Service
public class SysLogManagerImpl extends BaseServiceImpl<SysLog, String> implements SysLogManager {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysLogDao sysLogDao;
	@Autowired
	private UserManager userManager;

	@Override
	public void setBaseDao() {
		this.baseDao=sysLogDao;
	}

	@Transactional
	@Override
	public void saveNew(String logName,String logNote,String logType,String userId,Integer status) {
		SysLog sysLog = SysLogUtils.newSysLog(logName,logNote,logType,userId,status);
		super.save(sysLog);
	}

	public void saveNew(String logName,String logNote,String logType,String userId,Integer status,String ip) {
		SysLog sysLog = SysLogUtils.newSysLog(logName,logNote,logType,userId,status);
		sysLog.setIp(ip);
		super.save(sysLog);
	}

	@Override
	public Page<SysLog> findPage(Page<SysLog> page, String createDate, String logName) {
		page.setOrderBy("createDate");
		page.setOrderDir("desc");
		List<Criterion> cris = new ArrayList<Criterion>();
		if(createDate!=null && !"".equals(createDate)){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Criterion cri = Restrictions.gt("createDate",simpleDateFormat.parse(createDate));
				cris.add(cri);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(logName!=null && !"".equals(logName)){
			Criterion cri1 = Restrictions.like("logName","%"+logName+"%");
			Criterion cri2 = Restrictions.like("logNote","%"+logName+"%");
			cris.add(Restrictions.or(cri1,cri2));
		}
		page = findPageByCri(page,cris);

		List<SysLog> sysLogs = page.getResult();
		for (SysLog syslog:sysLogs) {
			String userId = syslog.getUserId();
			if(userId!=null){
				User user = userManager.findById(userId);
				if(user!=null){
					syslog.setLoginName(user.getLoginName());
				}
			}
		}
		return page;
	}

	/**
	 * 某个IP当天某项操作的次数
	 * @param logName
	 * @param status
	 * @param ip
	 * @return
	 */
	@Override
	public long ipLoginCount(String logName,Integer status,String ip) {
		return sysLogDao.ipLoginCount(logName,status,ip);
	}

	/**
	 * 某个IP当天登录失败的次数，如果超过10000次则当天会被限制登录
	 * @param ip
	 * @return
	 */
	public boolean ipLoginOk(String ip) {
		long ipCount =  ipLoginCount("登录失败",2,ip);
//		logger.info("ip {}，ipCount {}",ip,ipCount);
		if (ipCount>10000) return false;
		return true;
	}

}

