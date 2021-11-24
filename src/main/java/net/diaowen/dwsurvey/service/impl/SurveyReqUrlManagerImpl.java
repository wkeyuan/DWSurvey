package net.diaowen.dwsurvey.service.impl;

import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.dwsurvey.dao.SurveyReqUrlDao;
import net.diaowen.dwsurvey.entity.SurveyReqUrl;
import net.diaowen.dwsurvey.service.SurveyReqUrlManager;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.diaowen.common.utils.RandomUtils;

import java.util.List;


/**
 * 短链地址
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class SurveyReqUrlManagerImpl extends BaseServiceImpl<SurveyReqUrl, String> implements SurveyReqUrlManager    {
	
	@Autowired
	private SurveyReqUrlDao surveyReqUrlDao;

	@Override
	public void setBaseDao() {
		this.baseDao = surveyReqUrlDao;
	}

	@Transactional
	public void save(SurveyReqUrl entity){
		String sId=entity.getSid();
		if(sId==null || "".equals(sId)){
			sId=RandomUtils.randomStr(6, 12);
			entity.setSid(sId);
		}
		surveyReqUrlDao.save(entity);
	}

	@Override
	public SurveyReqUrl findBySurveyId(String surveyId, int type) {
		Criterion cri1 = Restrictions.eq("surveyId",surveyId);
		Criterion cri2 = Restrictions.eq("reqUrlType",type);
		List<SurveyReqUrl> surveyReqUrls = findList(cri1,cri2);
		if(surveyReqUrls.size()>0){
			return surveyReqUrls.get(0);
		}
		return null;
	}

	@Override
	public SurveyReqUrl newSurveyReqUrl(String surveyId, int type) {
		SurveyReqUrl surveyReqUrl = findBySurveyId(surveyId,3);
		if(surveyReqUrl==null){
			SurveyReqUrl newSurveyReqUrl = new SurveyReqUrl();
			newSurveyReqUrl.setReqUrlType(3);
			newSurveyReqUrl.setSurveyId(surveyId);
			newSurveyReqUrl.setSid(RandomUtils.randomStr(8,13));
			save(newSurveyReqUrl);
			return newSurveyReqUrl;
		}
		return surveyReqUrl;
	}
}
