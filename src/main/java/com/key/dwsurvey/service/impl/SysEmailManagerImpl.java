package com.key.dwsurvey.service.impl;

import com.key.dwsurvey.dao.SysEmailDao;
import com.key.dwsurvey.entity.SysEmail;
import com.key.dwsurvey.service.SysEmailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.common.service.BaseServiceImpl;

@Service
public class SysEmailManagerImpl extends BaseServiceImpl<SysEmail, String> implements SysEmailManager {
	
	@Autowired
	private SysEmailDao sysEmailDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=sysEmailDao;
	}
	
}
