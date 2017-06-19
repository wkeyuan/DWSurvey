package com.key.dwsurvey.service.impl;

import java.util.List;

import com.key.dwsurvey.dao.AnOrderDao;
import com.key.dwsurvey.entity.AnOrder;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.service.AnOrderManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.common.service.BaseServiceImpl;

/**
 * 排序题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnOrderManagerImpl extends BaseServiceImpl<AnOrder, String> implements AnOrderManager {

	@Autowired
	private AnOrderDao anOrderDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=anOrderDao;
	}

	@Override
	public List<AnOrder> findAnswer(String belongAnswerId, String quId) {//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anOrderDao.find(criterion1,criterion2);
	}

	@Override
	public void findGroupStats(Question question) {
		anOrderDao.findGroupStats(question);
	}
	
}
