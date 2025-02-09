package net.diaowen.dwsurvey.dao;

import net.diaowen.common.dao.BaseDao;
import net.diaowen.dwsurvey.entity.SysLog;

/**
 * 答卷数据 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface SysLogDao extends BaseDao<SysLog, String> {

    public long ipLoginCount(String logName, Integer status, String ip);

}
