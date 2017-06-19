package com.key.dwsurvey.service.impl;

import java.util.List;

import com.key.dwsurvey.dao.AnChenRadioDao;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.service.AnChenRadioManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.entity.AnChenRadio;
import com.key.dwsurvey.entity.DataCross;

/**
 * 矩陈单选题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnChenRadioManagerImpl extends BaseServiceImpl<AnChenRadio, String> implements AnChenRadioManager {

	@Autowired
	private AnChenRadioDao anChenRadioDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=anChenRadioDao;
	}

	@Override
	public List<AnChenRadio> findAnswer(String belongAnswerId, String quId) {//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anChenRadioDao.find(criterion1,criterion2);
	}

	@Override
	public void findGroupStats(Question question) {
		anChenRadioDao.findGroupStats(question);
	}
	
	@Override
	public List<DataCross> findStatsDataChart(Question question) {
		// TODO Auto-generated method stub
		return null;
	}
}
