package net.diaowen.dwsurvey.service;

import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.SysLog;
import net.diaowen.dwsurvey.entity.SysProperty;

/**
 * 系统操作日志
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface SysPropertyManager extends BaseService<SysProperty, String> {

    public SysProperty getEntity();

}
