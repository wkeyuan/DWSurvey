package com.key.dwsurvey.service.impl;

import java.util.List;

import com.key.dwsurvey.dao.AnChenScoreDao;
import com.key.dwsurvey.entity.AnChenScore;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.service.AnChenScoreManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.common.service.BaseServiceImpl;

/**
 * 矩陈评分题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnChenScoreManagerImpl extends BaseServiceImpl<AnChenScore, String> implements AnChenScoreManager {

	@Autowired
	private AnChenScoreDao anChenScoreDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=anChenScoreDao;
	}

	@Override
	public List<AnChenScore> findAnswer(String belongAnswerId, String quId) {//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anChenScoreDao.find(criterion1,criterion2);
	}

	@Override
	public void findGroupStats(Question question) {
		anChenScoreDao.findGroupStats(question);
	}
	
}
