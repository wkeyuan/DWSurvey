package net.diaowen.dwsurvey.service.impl;

import java.util.List;

import net.diaowen.dwsurvey.entity.AnCheckbox;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.service.AnCheckboxManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.dwsurvey.dao.AnCheckboxDao;
import net.diaowen.dwsurvey.entity.DataCross;

/**
 * 多选题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Service
public class AnCheckboxManagerImpl extends BaseServiceImpl<AnCheckbox, String> implements AnCheckboxManager {

	@Autowired
	private AnCheckboxDao anCheckboxDao;

	@Override
	public void setBaseDao() {
		this.baseDao=anCheckboxDao;
	}
	
	//根据exam_user信息查询答案
		public List<AnCheckbox> findAnswer(String belongAnswerId,String quId){
			//belongAnswerId quId
			Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
			Criterion criterion2=Restrictions.eq("quId", quId);
			return anCheckboxDao.find(criterion1,criterion2);
		}

		@Override
		public void findGroupStats(Question question) {
			anCheckboxDao.findGroupStats(question);
		}

		@Override
		public List<DataCross> findStatsDataCross(Question rowQuestion,
				Question colQuestion) {
			return anCheckboxDao.findStatsDataCross(rowQuestion,colQuestion);
		}

		@Override
		public List<DataCross> findStatsDataChart(Question question) {
			return anCheckboxDao.findStatsDataChart(question);
		}
		
	
}
