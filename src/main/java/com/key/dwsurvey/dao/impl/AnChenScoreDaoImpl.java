package com.key.dwsurvey.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.key.dwsurvey.dao.AnChenScoreDao;
import com.key.dwsurvey.entity.AnChenScore;
import com.key.dwsurvey.entity.Question;
import org.springframework.stereotype.Repository;

import com.key.common.dao.BaseDaoImpl;

/**
 * 矩陈评分 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class AnChenScoreDaoImpl extends BaseDaoImpl<AnChenScore, String> implements AnChenScoreDao {

	@Override
	public void findGroupStats(Question question) {
//		String sql="select qu_row_id,count(qu_row_id),AVG(answser_score) from t_an_score where qu_id=?  GROUP BY qu_row_id";
		String sql="select qu_row_id,qu_col_id,AVG(answser_score) from t_an_chen_score where  visibility=1 and  qu_id=? GROUP BY qu_row_id,qu_col_id";
		
		List<Object[]> list=this.getSession().createSQLQuery(sql).setString(0, question.getId()).list();
		
		List<AnChenScore> anChenScores=new ArrayList<AnChenScore>();
		
		for (Object[] objects : list) {
			AnChenScore anChenScore=new AnChenScore();
			//anChenRadio.setBelongId(belongId);
			anChenScore.setQuRowId(objects[0].toString());
			anChenScore.setQuColId(objects[1].toString());
			//int anCount=Integer.parseInt(objects[2].toString());
			//anChenScore.setAnCount(anCount);
			anChenScore.setAvgScore(Float.parseFloat(objects[2].toString()));
			anChenScores.add(anChenScore);
		}
		
		question.setAnChenScores(anChenScores);
	}

}
