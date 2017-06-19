package com.key.dwsurvey.dao.impl;

import com.key.dwsurvey.entity.ImportError;
import com.key.dwsurvey.dao.ImportErrorDao;
import org.springframework.stereotype.Repository;

import com.key.common.dao.BaseDaoImpl;

/**
 * 导入错误记录 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class ImportErrorDaoImpl  extends BaseDaoImpl<ImportError, String> implements ImportErrorDao {

}
