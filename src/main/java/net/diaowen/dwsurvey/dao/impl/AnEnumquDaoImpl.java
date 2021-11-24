package net.diaowen.dwsurvey.dao.impl;


import java.util.List;

import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.dao.AnEnumquDao;
import net.diaowen.dwsurvey.entity.AnEnumqu;
import org.springframework.stereotype.Repository;

import net.diaowen.common.dao.BaseDaoImpl;

/**
 * 枚举 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class AnEnumquDaoImpl extends BaseDaoImpl<AnEnumqu, String> implements AnEnumquDao {

	@Override
	public void findGroupStats(Question question) {
		String sql="select answer,count(answer) from t_an_enumqu where  visibility=1 and  qu_id=? GROUP BY answer";
		List<Object[]> list=this.getSession().createSQLQuery(sql).setParameter(1,question.getId()).list();

		//一共有多少对枚举数
		if(list!=null && list.size()>0){
			question.setAnCount(list.size());
		}
	}

}
