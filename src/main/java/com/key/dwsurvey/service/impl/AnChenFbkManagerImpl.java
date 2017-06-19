package com.key.dwsurvey.service.impl;

import java.util.List;

import com.key.dwsurvey.dao.AnChenFbkDao;
import com.key.dwsurvey.entity.AnChenFbk;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.service.AnChenFbkManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.common.service.BaseServiceImpl;

/**
 * 矩陈填空题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnChenFbkManagerImpl extends BaseServiceImpl<AnChenFbk, String> implements AnChenFbkManager {
	
	@Autowired
	private AnChenFbkDao anChenFbkDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=anChenFbkDao;
	}
	
	@Override
	public List<AnChenFbk> findAnswer(String belongAnswerId, String quId) {//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anChenFbkDao.find(criterion1,criterion2);
	}
	
	@Override
	public void findGroupStats(Question question) {
		anChenFbkDao.findGroupStats(question);
	}
}
