package net.diaowen.dwsurvey.service.impl;

import net.diaowen.dwsurvey.service.SysDbBackupManager;
import net.diaowen.dwsurvey.dao.SysDbBackupDao;
import net.diaowen.dwsurvey.entity.SysDbBackup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.diaowen.common.service.BaseServiceImpl;


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
