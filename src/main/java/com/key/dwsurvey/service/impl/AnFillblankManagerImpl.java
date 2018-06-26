package com.key.dwsurvey.service.impl;

import com.key.common.plugs.page.Page;
import com.key.dwsurvey.entity.AnFillblank;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.service.AnFillblankManager;
import com.key.dwsurvey.dao.AnFillblankDao;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.common.service.BaseServiceImpl;

/**
 * 填空题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnFillblankManagerImpl extends BaseServiceImpl<AnFillblank, String> implements AnFillblankManager {

	@Autowired
	private AnFillblankDao anFillblankDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=anFillblankDao;
	}

	//根据exam_user信息查询答案
	public AnFillblank findAnswer(String belongAnswerId,String quId){
		//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anFillblankDao.findUnique(criterion1,criterion2);
	}

	@Override
	public void findGroupStats(Question question) {
		anFillblankDao.findGroupStats(question);
	}


	@Override
	public Page<AnFillblank> findPage(Page<AnFillblank> page, String quId) {
		Criterion cri1 = Restrictions.eq("quId",quId);
		return findPage(page,cri1);
	}
}
