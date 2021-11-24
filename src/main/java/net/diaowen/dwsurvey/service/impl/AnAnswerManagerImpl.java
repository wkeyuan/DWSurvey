package net.diaowen.dwsurvey.service.impl;

import net.diaowen.dwsurvey.dao.AnAnswerDao;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.service.AnAnswerManager;
import net.diaowen.dwsurvey.entity.AnAnswer;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.diaowen.common.service.BaseServiceImpl;

/**
 * @author keyuan
 * keyuan258@gmail.com
 *
 */

@Service
public class AnAnswerManagerImpl extends BaseServiceImpl<AnAnswer, String> implements AnAnswerManager {

	@Autowired
	private AnAnswerDao anAnswerDao;

	@Override
	public void setBaseDao() {
		this.baseDao=anAnswerDao;
	}
	
	
	//根据exam_user信息查询答案
		public AnAnswer findAnswer(String belongAnswerId,String quId){
			Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
			Criterion criterion2=Restrictions.eq("quId", quId);
			return anAnswerDao.findUnique(criterion1,criterion2);
		}


		@Override
		public void findGroupStats(Question question) {
			anAnswerDao.findGroupStats(question);
		}
			
}
