package com.key.dwsurvey.service.impl;

import com.key.dwsurvey.service.SysDbBackupManager;
import com.key.dwsurvey.dao.SysDbBackupDao;
import com.key.dwsurvey.entity.SysDbBackup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.common.service.BaseServiceImpl;


/**
 * 问卷备份
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class SysDbBackupManagerImpl extends BaseServiceImpl<SysDbBackup, String> implements SysDbBackupManager {

	@Autowired
	private SysDbBackupDao sysDbBackupDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=sysDbBackupDao;
	}
	
}
