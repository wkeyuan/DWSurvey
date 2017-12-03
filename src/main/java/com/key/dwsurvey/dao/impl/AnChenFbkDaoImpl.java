package com.key.dwsurvey.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.key.dwsurvey.dao.AnChenFbkDao;
import com.key.dwsurvey.entity.AnChenFbk;
import com.key.dwsurvey.entity.QuChenColumn;
import com.key.dwsurvey.entity.Question;
import org.springframework.stereotype.Repository;

import com.key.common.dao.BaseDaoImpl;
import com.key.dwsurvey.entity.QuChenRow;

/**
 * 矩陈填空 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class AnChenFbkDaoImpl extends BaseDaoImpl<AnChenFbk, String> implements AnChenFbkDao {

	@Override
	public void findGroupStats(Question question) {
//		String sql="select qu_row_id,count(*) from t_an_chen_checkbox where qu_id=? GROUP BY qu_row_id";
		String sql="select qu_row_id,qu_col_id,count(qu_col_id) from t_an_chen_fbk where visibility=1 and  qu_id=? GROUP BY qu_row_id,qu_col_id";
		List<Object[]> list=this.getSession().createSQLQuery(sql).setString(0, question.getId()).list();
		
		List<QuChenRow> quChenRows=question.getRows();
		List<QuChenColumn> quChenColumns=question.getColumns();
		
		int count=0;
		for (QuChenRow quChenRow : quChenRows) {
			
			String quChenRowId=quChenRow.getId();
			
			for (Object[] objects : list) {
				if(quChenRowId.equals(objects[0].toString())){
					int anCount=Integer.parseInt(objects[2].toString());
					count+=anCount;
					quChenRow.setAnCount(quChenRow.getAnCount()+anCount);
					//quChenColumn.setAnCount(anCount);;
					break;
				}
			}
			
		}
		question.setAnCount(count);
		
		
		List<AnChenFbk> anChenFbks=new ArrayList<AnChenFbk>();
		
		for (Object[] objects : list) {
			AnChenFbk anChenFbk=new AnChenFbk();
			//anChenRadio.setBelongId(belongId);
			anChenFbk.setQuRowId(objects[0].toString());
			anChenFbk.setQuColId(objects[1].toString());
			int anCount=Integer.parseInt(objects[2].toString());
			anChenFbk.setAnCount(anCount);
			anChenFbks.add(anChenFbk);
		}
		
		question.setAnChenFbks(anChenFbks);
	}
	
}
