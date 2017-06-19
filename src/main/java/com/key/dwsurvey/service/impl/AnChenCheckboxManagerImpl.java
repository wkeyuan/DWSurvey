package com.key.dwsurvey.service.impl;

import java.util.List;

import com.key.dwsurvey.entity.Question;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.dao.AnChenCheckboxDao;
import com.key.dwsurvey.entity.AnChenCheckbox;
import com.key.dwsurvey.service.AnChenCheckboxManager;

/**
 * 矩陈多选题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnChenCheckboxManagerImpl extends BaseServiceImpl<AnChenCheckbox, String> implements AnChenCheckboxManager{

	@Autowired
	private AnChenCheckboxDao anChenCheckboxDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=anChenCheckboxDao;
	}
	@Override
	public List<AnChenCheckbox> findAnswer(String belongAnswerId, String quId) {//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anChenCheckboxDao.find(criterion1,criterion2);
	}
	@Override
	public void findGroupStats(Question question) {
		anChenCheckboxDao.findGroupStats(question);
	}
}
