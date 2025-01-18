package net.diaowen.dwsurvey.service.impl;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.dwsurvey.common.SysLogUtils;
import net.diaowen.dwsurvey.dao.SysLogDao;
import net.diaowen.dwsurvey.dao.SysPropertyDao;
import net.diaowen.dwsurvey.entity.SysLog;
import net.diaowen.dwsurvey.entity.SysProperty;
import net.diaowen.dwsurvey.service.SysLogManager;
import net.diaowen.dwsurvey.service.SysPropertyManager;
import net.diaowen.dwsurvey.service.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keyuan
 * keyuan258@gmail.com
 *
 */

@Service
public class SysPropertyManagerImpl extends BaseServiceImpl<SysProperty, String> implements SysPropertyManager {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysPropertyDao sysPropertyDao;

	@Override
	public void setBaseDao() {
		this.baseDao = sysPropertyDao;
	}

	public void save(SysProperty newEntity) {
		SysProperty dbSysProperty = sysPropertyDao.findFirst();
		if (dbSysProperty!=null) {
			dbSysProperty = mergeEntity(newEntity, dbSysProperty);
			sysPropertyDao.save(dbSysProperty);
		} else {
			// 没有则初始化个
			SysProperty toSysProperty = new SysProperty();
			toSysProperty = mergeEntity(newEntity, toSysProperty);
			sysPropertyDao.save(toSysProperty);
		}
	}

	public SysProperty mergeEntity(SysProperty newEntity,SysProperty toSysProperty) {
		if (StringUtils.isNotEmpty(newEntity.getSiteName())) toSysProperty.setSiteName(newEntity.getSiteName());
		if (StringUtils.isNotEmpty(newEntity.getSiteIcp())) toSysProperty.setSiteIcp(newEntity.getSiteIcp());
		if (StringUtils.isNotEmpty(newEntity.getSiteUrl())) toSysProperty.setSiteUrl(newEntity.getSiteUrl());
		if (StringUtils.isNotEmpty(newEntity.getSiteAdminName())) toSysProperty.setSiteAdminName(newEntity.getSiteAdminName());
		if (StringUtils.isNotEmpty(newEntity.getSiteMail())) toSysProperty.setSiteMail(newEntity.getSiteMail());
		if (StringUtils.isNotEmpty(newEntity.getSitePhone())) toSysProperty.setSitePhone(newEntity.getSitePhone());
		if (StringUtils.isNotEmpty(newEntity.getLogoImageUrl())) toSysProperty.setLogoImageUrl(newEntity.getLogoImageUrl());
		if (StringUtils.isNotEmpty(newEntity.getFooterContent())) toSysProperty.setFooterContent(newEntity.getFooterContent());
		if (StringUtils.isNotEmpty(newEntity.getMaxUpLevel())) toSysProperty.setMaxUpLevel(newEntity.getMaxUpLevel());
		if (StringUtils.isNotEmpty(newEntity.getNewVersionInfo())) toSysProperty.setNewVersionInfo(newEntity.getNewVersionInfo());
		// 设置默认值
		if (StringUtils.isEmpty(toSysProperty.getSiteName())) toSysProperty.setSiteName("调问网");
		if (StringUtils.isEmpty(toSysProperty.getSiteIcp())) toSysProperty.setSiteIcp("");
		if (StringUtils.isEmpty(toSysProperty.getSiteUrl())) toSysProperty.setSiteUrl("https://www.diaowen.net");
		if (StringUtils.isEmpty(toSysProperty.getSiteAdminName())) toSysProperty.setSiteAdminName("");
		if (StringUtils.isEmpty(toSysProperty.getSiteMail())) toSysProperty.setSiteMail("");
		if (StringUtils.isEmpty(toSysProperty.getSitePhone())) toSysProperty.setSitePhone("");
		if (StringUtils.isEmpty(toSysProperty.getLogoImageUrl())) toSysProperty.setLogoImageUrl("");
		if (StringUtils.isEmpty(toSysProperty.getFooterContent())) toSysProperty.setFooterContent("");
		if (StringUtils.isEmpty(toSysProperty.getMaxUpLevel())) toSysProperty.setMaxUpLevel("");
		if (StringUtils.isEmpty(toSysProperty.getNewVersionInfo())) toSysProperty.setNewVersionInfo("");
		return toSysProperty;
	}

	public SysProperty getEntity() {
		SysProperty sysProperty = sysPropertyDao.findFirst();
		return sysProperty;
	}

}

