package net.diaowen.dwsurvey.service.impl;

import java.util.List;

import net.diaowen.dwsurvey.dao.AnOrderDao;
import net.diaowen.dwsurvey.entity.AnOrder;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.service.AnOrderManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.diaowen.common.service.BaseServiceImpl;

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
		return anOrderDao.findByOrder("orderyNum",true,criterion1,criterion2);
//		return anOrderDao.find(criterion1,criterion2);
	}

	@Override
	public void findGroupStats(Question question) {
		anOrderDao.findGroupStats(question);
	}
	
}
