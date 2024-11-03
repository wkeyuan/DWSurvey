package net.diaowen.dwsurvey.service;

import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.SysLog;

/**
 * 系统操作日志
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface SysLogManager extends BaseService<SysLog, String> {

    public void saveNew(String logName,String logNote,String logType,String userId,Integer status);

    public void saveNew(String logName,String logNote,String logType,String userId,Integer status,String ip);

    Page<SysLog> findPage(Page<SysLog> page, String createDate, String logName);

    public long ipLoginCount(String logName,Integer status,String ip);

    public boolean ipLoginOk(String ip);
}
