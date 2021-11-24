package net.diaowen.dwsurvey.dao;

import net.diaowen.common.dao.BaseDao;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.AnEnumqu;

/**
 * 枚举题 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnEnumquDao extends BaseDao<AnEnumqu, String>{

	public void findGroupStats(Question question);

}
