package com.key.dwsurvey.service.impl;

import com.key.dwsurvey.dao.SurveyReqUrlDao;
import com.key.dwsurvey.entity.SurveyReqUrl;
import com.key.dwsurvey.service.SurveyReqUrlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key.common.utils.RandomUtils;


/**
 * 短链地址
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class SurveyReqUrlManagerImpl  implements SurveyReqUrlManager {
	
	@Autowired
	private SurveyReqUrlDao surveyReqUrlDao;
	
	@Transactional
	public void save(SurveyReqUrl entity){
		String sId=entity.getSid();
		if(sId==null || "".equals(sId)){
			sId=RandomUtils.randomStr(6, 12);
			entity.setSid(sId);
		}
		surveyReqUrlDao.save(entity);
	}
	
	public void getByShortId(String shortId){
		
	}
	
}
