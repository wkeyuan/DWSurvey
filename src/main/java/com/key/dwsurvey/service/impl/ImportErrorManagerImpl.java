package com.key.dwsurvey.service.impl;

import com.key.dwsurvey.entity.ImportError;
import com.key.dwsurvey.dao.ImportErrorDao;
import com.key.dwsurvey.service.ImportErrorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 导入错误记录题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class ImportErrorManagerImpl implements ImportErrorManager {
    @Autowired
    private ImportErrorDao importErrorDao;
    @Transactional
    public void save(ImportError entity) {
	importErrorDao.save(entity);
    }
    
}
