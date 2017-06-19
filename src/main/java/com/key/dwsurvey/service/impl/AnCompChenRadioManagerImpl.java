package com.key.dwsurvey.service.impl;

import java.util.List;

import com.key.dwsurvey.dao.AnCompChenRadioDao;
import com.key.dwsurvey.entity.AnCompChenRadio;
import com.key.dwsurvey.service.AnCompChenRadioManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.common.service.BaseServiceImpl;

/**
 * 复合矩陈单选题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnCompChenRadioManagerImpl extends BaseServiceImpl<AnCompChenRadio, String> implements AnCompChenRadioManager {

	@Autowired
	private AnCompChenRadioDao anCompChenRadioDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=anCompChenRadioDao;
	}
	
	@Override
	public List<AnCompChenRadio> findAnswer(String belongAnswerId, String quId) {//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anCompChenRadioDao.find(criterion1,criterion2);
	}
}
