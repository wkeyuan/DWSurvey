package com.key.dwsurvey.service.impl;

import java.util.List;

import com.key.common.plugs.page.Page;
import com.key.dwsurvey.dao.AnDFillblankDao;
import com.key.dwsurvey.entity.AnFillblank;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.entity.AnDFillblank;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.service.AnDFillblankManager;

/**
 * 多行填空题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnDFillblankManagerImpl extends BaseServiceImpl<AnDFillblank, String> implements AnDFillblankManager{

	@Autowired
	private AnDFillblankDao anDFillblankDao;

	@Override
	public void setBaseDao() {
		this.baseDao=anDFillblankDao;
	}

	//根据exam_user信息查询答案
	public List<AnDFillblank> findAnswer(String belongAnswerId,String quId){
		//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anDFillblankDao.find(criterion1,criterion2);
	}

	@Override
	public void findGroupStats(Question question) {
		anDFillblankDao.findGroupStats(question);
	}

	public Page<AnDFillblank> findPage(Page<AnDFillblank> page, String quItemId){
		Criterion cri1 = Restrictions.eq("quItemId",quItemId);
		return findPage(page,cri1);
	}
	
}
