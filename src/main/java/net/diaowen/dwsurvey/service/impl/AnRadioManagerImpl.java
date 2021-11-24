package net.diaowen.dwsurvey.service.impl;

import java.util.List;

import net.diaowen.dwsurvey.entity.AnRadio;
import net.diaowen.dwsurvey.dao.AnRadioDao;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.service.AnRadioManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.dwsurvey.entity.DataCross;

/**
 * 单选题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnRadioManagerImpl extends BaseServiceImpl<AnRadio, String> implements AnRadioManager {

	@Autowired
	private AnRadioDao anRadioDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=anRadioDao;
	}
	
	//根据exam_user信息查询答案
	public AnRadio findAnswer(String belongAnswerId,String quId){
		//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anRadioDao.findUnique(criterion1,criterion2);
	}

	@Override
	public void findGroupStats(Question question) {
		anRadioDao.findGroupStats(question);
	}

	@Override
	public List<DataCross> findStatsDataCross(Question rowQuestion,
			Question colQuestion) {
		return anRadioDao.findStatsDataCross(rowQuestion, colQuestion);
	}

	@Override
	public List<DataCross> findStatsDataChart(Question question) {
		return anRadioDao.findStatsDataChart(question);
	}
}
