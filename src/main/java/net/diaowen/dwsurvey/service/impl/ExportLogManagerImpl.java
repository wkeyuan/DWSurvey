package net.diaowen.dwsurvey.service.impl;

import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.dwsurvey.dao.ExportLogDao;
import net.diaowen.dwsurvey.entity.ExportLog;
import net.diaowen.dwsurvey.service.ExportLogManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keyuan
 * keyuan258@gmail.com
 *
 */

@Service
public class ExportLogManagerImpl extends BaseServiceImpl<ExportLog, String> implements ExportLogManager {

	@Autowired
	private ExportLogDao exportLogDao;

	@Override
	public void setBaseDao() {
		this.baseDao=exportLogDao;
	}

	@Override
	public ExportLog findById(String id) {
		return exportLogDao.findUniqueBy("id",id);
	}

	@Override
	public List<ExportLog> findByParam1(String surveyId,int exportType) {
		Criterion cri1 = Restrictions.eq("param1",surveyId);
		Criterion cri2 = Restrictions.eq("exportType",exportType);
//		Criterion cri3 = Restrictions.eq("progress",1);
		return exportLogDao.findByOrder("createDate",false,cri1,cri2);
	}

}
