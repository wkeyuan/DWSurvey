package net.diaowen.dwsurvey.common;

import net.diaowen.dwsurvey.entity.SysLog;

public class SysLogUtils {

    public static SysLog newSysLog(String logName,String logNote,String logType,String userId,Integer status) {
        SysLog sysLog = new SysLog();
        sysLog.setUserId(userId);
        sysLog.setLogName(logName);
        sysLog.setLogNote(logNote);
        sysLog.setLogType(logType);
        sysLog.setStatus(status);
        return sysLog;
    }


}
