package net.diaowen.dwsurvey.service.impl;

import java.util.List;

import net.diaowen.dwsurvey.dao.AnEnumquDao;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.service.AnEnumquManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.dwsurvey.entity.AnEnumqu;

/**
 * 枚举题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnEnumquManagerImpl extends BaseServiceImpl<AnEnumqu, String> implements AnEnumquManager {

	@Autowired
	private AnEnumquDao anEnumquDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=anEnumquDao;
	}

	//根据exam_user信息查询答案
	public List<AnEnumqu> findAnswer(String belongAnswerId,String quId){
		//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anEnumquDao.find(criterion1,criterion2);
	}

	@Override
	public void findGroupStats(Question question) {
		anEnumquDao.findGroupStats(question);
	}
}
