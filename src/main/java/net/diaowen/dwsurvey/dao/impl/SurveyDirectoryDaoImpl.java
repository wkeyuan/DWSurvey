package net.diaowen.dwsurvey.dao.impl;

import net.diaowen.dwsurvey.dao.SurveyDirectoryDao;
import org.springframework.stereotype.Repository;

import net.diaowen.common.dao.BaseDaoImpl;
import net.diaowen.dwsurvey.entity.SurveyDirectory;

/**
 * 问卷目录 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

//@Repository("surveyDirectoryDao")
@Repository
public class SurveyDirectoryDaoImpl extends BaseDaoImpl<SurveyDirectory, String> implements SurveyDirectoryDao {
    @Override
    public Object surveyCount(String state) {
        String hql = "select count(*) from SurveyDirectory ";
        if ("design".equals(state)) {
            hql = hql + " where surveyState=0";
        } else if ("collect".equals(state)) {
            hql = hql + " where surveyState=1";
        } else if ("close".equals(state)) {
            hql = hql + " where surveyState=2";
        }
        return countHqlResult(hql);
    }
}
