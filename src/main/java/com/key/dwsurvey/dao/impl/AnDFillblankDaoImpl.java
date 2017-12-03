package com.key.dwsurvey.dao.impl;

import java.util.List;

import com.key.dwsurvey.dao.AnDFillblankDao;
import org.springframework.stereotype.Repository;

import com.key.common.dao.BaseDaoImpl;
import com.key.dwsurvey.entity.AnDFillblank;
import com.key.dwsurvey.entity.QuMultiFillblank;
import com.key.dwsurvey.entity.Question;

/**
 * 多项填空 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class AnDFillblankDaoImpl extends BaseDaoImpl<AnDFillblank, String> implements AnDFillblankDao {

	@Override
	public void findGroupStats(Question question) {
		String sql="select qu_item_id,count(*) from t_an_dfillblank where  visibility=1 and  qu_id=? group by qu_item_id";
		
		List<Object[]> list=this.getSession().createSQLQuery(sql).setString(0, question.getId()).list();
		List<QuMultiFillblank> quMultiFillblanks=question.getQuMultiFillblanks();
		
		for (QuMultiFillblank quMultiFillblank : quMultiFillblanks) {
			String quMultiFillblankId=quMultiFillblank.getId();
			for (Object[] objects : list) {
				if(quMultiFillblankId.equals(objects[0].toString())){
					quMultiFillblank.setAnCount(Integer.parseInt(objects[1].toString()));
					break;
				}
			}
		}
	}
	
}
