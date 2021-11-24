package net.diaowen.dwsurvey.service.impl;

import java.util.List;

import net.diaowen.dwsurvey.service.AnScoreManager;
import net.diaowen.dwsurvey.dao.AnScoreDao;
import net.diaowen.dwsurvey.entity.AnScore;
import net.diaowen.dwsurvey.entity.Question;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.diaowen.common.service.BaseServiceImpl;

/**
 * 评分题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnScoreManagerImpl extends BaseServiceImpl<AnScore, String> implements AnScoreManager {

	@Autowired
	private AnScoreDao anScoreDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=anScoreDao;
	}

	@Override
	public List<AnScore> findAnswer(String belongAnswerId, String quId) {//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anScoreDao.find(criterion1,criterion2);
	}

	@Override
	public void findGroupStats(Question question) {
		anScoreDao.findGroupStats(question);
	}
	
}
