package net.diaowen.dwsurvey.service.impl;

import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.dwsurvey.dao.AnUploadFileDao;
import net.diaowen.dwsurvey.entity.AnUplodFile;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.service.AnUploadFileManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 填空题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class AnUploadFileManagerImpl extends BaseServiceImpl<AnUplodFile, String> implements AnUploadFileManager {

	@Autowired
	private AnUploadFileDao anUploadFileDao;

	@Override
	public void setBaseDao() {
		this.baseDao=anUploadFileDao;
	}

	//根据exam_user信息查询答案
	public List<AnUplodFile> findAnswer(String belongAnswerId, String quId){
		//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongAnswerId", belongAnswerId);
		Criterion criterion2=Restrictions.eq("quId", quId);
		return anUploadFileDao.find(criterion1,criterion2);
	}

	@Override
	public void findGroupStats(Question question) {
		anUploadFileDao.findGroupStats(question);
	}

	public List<AnUplodFile> findAnswer(String surveyId){
		//belongAnswerId quId
		Criterion criterion1=Restrictions.eq("belongId", surveyId);
		return anUploadFileDao.find(criterion1);
	}

}
