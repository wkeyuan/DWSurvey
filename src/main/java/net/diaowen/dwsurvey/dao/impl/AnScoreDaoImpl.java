package net.diaowen.dwsurvey.dao.impl;

import java.util.List;

import net.diaowen.dwsurvey.dao.AnScoreDao;
import net.diaowen.dwsurvey.entity.AnScore;
import net.diaowen.dwsurvey.entity.Question;
import org.springframework.stereotype.Repository;

import net.diaowen.common.dao.BaseDaoImpl;
import net.diaowen.dwsurvey.entity.QuScore;

/**
 * 评分题 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class AnScoreDaoImpl extends BaseDaoImpl<AnScore, String> implements AnScoreDao {

	@Override
	public void findGroupStats(Question question) {
		String sql="select qu_row_id,count(qu_row_id),AVG(answser_score) from t_an_score where  visibility=1 and qu_id=?  GROUP BY qu_row_id";
		List<Object[]> list=this.getSession().createSQLQuery(sql).setParameter(1,question.getId()).list();
		List<QuScore> quScores=question.getQuScores();

		int count=0;
		for (QuScore quScore : quScores) {

			String quScoreId=quScore.getId();
			for (Object[] objects : list) {
				if(quScoreId.equals(objects[0].toString())){
					int anCount=Integer.parseInt(objects[1].toString());
					count+=anCount;
					quScore.setAnCount(anCount);
					quScore.setAvgScore(Float.parseFloat(objects[2].toString()));;
					continue;
				}
			}
		}
		question.setAnCount(count);
	}

}
