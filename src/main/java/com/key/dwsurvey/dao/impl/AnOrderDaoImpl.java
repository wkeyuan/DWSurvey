package com.key.dwsurvey.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.key.dwsurvey.dao.AnOrderDao;
import com.key.dwsurvey.entity.AnOrder;
import com.key.dwsurvey.entity.QuOrderby;
import com.key.dwsurvey.entity.Question;
import org.springframework.stereotype.Repository;

import com.key.common.dao.BaseDaoImpl;

/**
 * 排序题 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class AnOrderDaoImpl extends BaseDaoImpl<AnOrder, String> implements AnOrderDao {

	@Override
	public void findGroupStats(Question question) {
		String sql="select qu_row_id,sum(ordery_num) sumOrderNum from t_an_order where visibility=1 and qu_id=? group by qu_row_id order by sumOrderNum";
		List<Object[]> list=this.getSession().createSQLQuery(sql).setString(0, question.getId()).list();
		List<QuOrderby> quOrderbies=question.getQuOrderbys();
		
		/*
		int count=0;
		for (QuOrderby quOrderby : quOrderbies) {
			String quOrderById= quOrderby.getId();
			for (Object[] objects : list) {
				if(quOrderById.equals(objects[0].toString())){
					Float sumOrderyNum=Float.parseFloat(objects[1].toString());
					count+=sumOrderyNum;
					quOrderby.setAnOrderSum(sumOrderyNum.intValue());
					System.out.println("sumOrderyNum:"+sumOrderyNum);
					continue;
				}
			}
		}
		question.setAnCount(count);
		*/
		
		int count=0;
		List<QuOrderby> list2 = new ArrayList<QuOrderby>();
		for (Object[] objects : list) {
			Float sumOrderyNum=Float.parseFloat(objects[1].toString());
			String quOrderById= objects[0].toString();
			for (QuOrderby quOrderby : quOrderbies) {
				if(quOrderById.equals(quOrderby.getId())){
					quOrderby.setAnOrderSum(sumOrderyNum.intValue());
					list2.add(quOrderby);
				}
			}
		}
//		question.setAnCount(count);
		question.setQuOrderbys(list2);
		
	}
	
}
