package com.key.dwsurvey.service.impl;

import com.key.dwsurvey.dao.SysSendEmailDao;
import com.key.dwsurvey.entity.SysSendEmail;
import com.key.dwsurvey.service.SysSendEmailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.common.service.BaseServiceImpl;

@Service
public class SysSendEmailManagerImpl extends BaseServiceImpl<SysSendEmail, String> implements SysSendEmailManager {

	@Autowired
	private SysSendEmailDao sysSendEmailDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=sysSendEmailDao;
	}
	
	@Override
	public void scanning() {
		//扫描没有发送的邮件，进行邮件发送操作，ok.
		
	}
	
}
