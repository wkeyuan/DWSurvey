package net.diaowen.dwsurvey.service.impl;

import java.util.List;

import net.diaowen.dwsurvey.dao.AnYesnoDao;
import net.diaowen.dwsurvey.service.AnYesnoManager;
import net.diaowen.dwsurvey.entity.AnYesno;
import net.diaowen.dwsurvey.entity.Question;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.dwsurvey.entity.DataCross;

/**
 * 枚举题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnYesnoManagerImpl extends BaseServiceImpl<AnYesno, String> implements AnYesnoManager {
	
	@Autowired
	private AnYesnoDao anYesnoDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=anYesnoDao;
	}
	
	//根据exam_user信息查询答案
	public AnYesno findAnswer(String belongAnswerId,String quId){
		//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anYesnoDao.findUnique(criterion1,criterion2);
	}

	@Override
	public void findGroupStats(Question question) {
		anYesnoDao.findGroupStats(question);
	}

	@Override
	public List<DataCross> findStatsDataCross(Question rowQuestion, Question colQuestion) {
		return anYesnoDao.findStatsDataCross(rowQuestion,colQuestion);
	}

	@Override
	public List<DataCross> findStatsDataChart(Question question) {
		return anYesnoDao.findStatsDataChart(question);
	}
	
}
