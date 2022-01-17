package net.diaowen.dwsurvey.dao.impl;

import net.diaowen.common.dao.BaseDaoImpl;
import net.diaowen.dwsurvey.dao.AnUploadFileDao;
import net.diaowen.dwsurvey.entity.AnUplodFile;
import net.diaowen.dwsurvey.entity.Question;
import org.springframework.stereotype.Repository;

/**
 * 填空 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class AnUploadFileDaoImpl extends BaseDaoImpl<AnUplodFile, String> implements AnUploadFileDao {

	@Override
	public void findGroupStats(Question question) {
		String sql="select count(case when file_path='' then file_path end) emptyCount, count(case when file_path!='' then file_path end) blankCount from t_an_uplodfile where  visibility=1 and  qu_id=?";
		Object[] objs=(Object[]) this.getSession().createSQLQuery(sql).setParameter(1, question.getId()).uniqueResult();

		question.setRowContent(objs[0].toString());//未回答数
		question.setOptionContent(objs[1].toString());//回答的项数
		question.setAnCount(Integer.parseInt(objs[1].toString()));
	}

}
