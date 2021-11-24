package net.diaowen.dwsurvey.dao.impl;

import net.diaowen.dwsurvey.entity.ImportError;
import net.diaowen.dwsurvey.dao.ImportErrorDao;
import org.springframework.stereotype.Repository;

import net.diaowen.common.dao.BaseDaoImpl;

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
