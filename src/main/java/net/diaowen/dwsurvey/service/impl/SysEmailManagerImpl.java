package net.diaowen.dwsurvey.service.impl;

import net.diaowen.dwsurvey.dao.SysEmailDao;
import net.diaowen.dwsurvey.entity.SysEmail;
import net.diaowen.dwsurvey.service.SysEmailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.diaowen.common.service.BaseServiceImpl;

@Service
public class SysEmailManagerImpl extends BaseServiceImpl<SysEmail, String> implements SysEmailManager {
	
	@Autowired
	private SysEmailDao sysEmailDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=sysEmailDao;
	}
	
}
