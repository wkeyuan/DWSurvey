package net.diaowen.dwsurvey.dao.impl;

import net.diaowen.dwsurvey.dao.AnAnswerDao;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.AnAnswer;
import org.springframework.stereotype.Repository;

import net.diaowen.common.dao.BaseDaoImpl;

/**
 * 答卷 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */


@Repository
public class AnAnswerDaoImpl extends BaseDaoImpl<AnAnswer, String> implements AnAnswerDao {

	@Override
	public void findGroupStats(Question question) {
		String sql="select count(case when answer='' then answer end) emptyCount, count(case when answer!='' then answer end) blankCount from t_an_answer where visibility=1 and qu_id=?";

		Object[] objs=(Object[]) this.getSession().createSQLQuery(sql).setParameter(1, question.getId()).uniqueResult();

		question.setRowContent(objs[0].toString());//未回答数
		question.setOptionContent(objs[1].toString());//回答的项数
		question.setAnCount(Integer.parseInt(objs[1].toString()));
	}

}
