package net.diaowen.dwsurvey.dao.impl;

import net.diaowen.common.dao.BaseDaoImpl;
import net.diaowen.dwsurvey.dao.SysLogDao;
import net.diaowen.dwsurvey.entity.SysLog;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 答卷 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */


@Repository
public class SysLogDaoImpl extends BaseDaoImpl<SysLog, String> implements SysLogDao {

    @Override
    public long ipLoginCount(String logName, Integer status, String ip) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Date date = new Date();
        String beginDate = simpleDateFormat.format(date);
        return countHqlResult(" FROM SysLog where logName='"+logName+"' and status="+status+" and ip='"+ip+"' and createDate>='"+beginDate+"'");
    }
}
