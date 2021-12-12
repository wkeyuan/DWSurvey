package net.diaowen.dwsurvey.service.impl;

import java.util.List;

import net.diaowen.dwsurvey.dao.SurveyDetailDao;
import net.diaowen.dwsurvey.entity.SurveyDetail;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.common.utils.ReflectionUtils;
import net.diaowen.dwsurvey.service.SurveyDetailManager;


/**
 * 问卷详情
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class SurveyDetailManagerImpl extends BaseServiceImpl<SurveyDetail, String> implements SurveyDetailManager{

	@Autowired
	private SurveyDetailDao surveyDetailDao;

	@Override
	public void setBaseDao() {
		this.baseDao=surveyDetailDao;
	}

	@Transactional
	@Override
	public void save(SurveyDetail t) {
		//判断有无，有则更新
		SurveyDetail surveyDetail=findUn(t.getDirId());
		if(surveyDetail==null){
			surveyDetail=new SurveyDetail();
		}
		ReflectionUtils.copyAttr(t,surveyDetail);
		super.save(surveyDetail);
	}

	private SurveyDetail findUn(String dirId){
		Criterion criterion=Restrictions.eq("dirId", dirId);
		 List<SurveyDetail> details=surveyDetailDao.find(criterion);
		 if(details!=null && details.size()>0){
			 return details.get(0);
		 }
		 return null;
	}

	@Override
	public SurveyDetail getBySurveyId(String surveyId) {
		 return findUn(surveyId);
	}

	@Transactional
	@Override
	public void saveBaseUp(SurveyDetail t) {
		//判断有无，有则更新
		SurveyDetail surveyDetail=findUn(t.getDirId());
		if(surveyDetail!=null){

			surveyDetail.setEffective(t.getEffective());
			surveyDetail.setEffectiveIp(t.getEffectiveIp());
			surveyDetail.setRefresh(t.getRefresh());
			surveyDetail.setRule(t.getRule());
			surveyDetail.setRuleCode(t.getRuleCode());
			surveyDetail.setYnEndTime(t.getYnEndTime());
			surveyDetail.setYnEndNum(t.getYnEndNum());
			surveyDetail.setEndNum(t.getEndNum());
			surveyDetail.setEndTime(t.getEndTime());


			super.save(surveyDetail);
		}

	}
}
