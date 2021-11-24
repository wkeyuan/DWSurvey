package net.diaowen.dwsurvey.service.impl;

import net.diaowen.dwsurvey.dao.SysSendEmailDao;
import net.diaowen.dwsurvey.entity.SysSendEmail;
import net.diaowen.dwsurvey.service.SysSendEmailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.diaowen.common.service.BaseServiceImpl;

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
