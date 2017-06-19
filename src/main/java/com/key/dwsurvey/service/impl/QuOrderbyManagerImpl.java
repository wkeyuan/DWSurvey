package com.key.dwsurvey.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.key.dwsurvey.dao.QuOrderbyDao;
import com.key.dwsurvey.entity.QuOrderby;
import com.key.dwsurvey.service.QuOrderbyManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.service.BaseServiceImpl;


/**
 * 排序题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class QuOrderbyManagerImpl extends BaseServiceImpl<QuOrderby, String> implements QuOrderbyManager {

	@Autowired
	private QuOrderbyDao quOrderbyDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=quOrderbyDao;
	}

	public List<QuOrderby> findByQuId(String quId){
		Page<QuOrderby> page=new Page<QuOrderby>();
		page.setOrderBy("orderById");
		page.setOrderDir("asc");
		
		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_quId", quId));
		filters.add(new PropertyFilter("EQI_visibility", "1"));
		return findAll(page, filters);
	}
	
	public int getOrderById(String quId){
		Criterion criterion=Restrictions.eq("quId", quId);
		QuOrderby quOrderby=quOrderbyDao.findFirst("orderById", false, criterion);
		if(quOrderby!=null){
			return quOrderby.getOrderById();
		}
		return 0;
	}
	
	
	
	/*******************************************************************8
	 * 更新操作
	 */
	
	@Override
	@Transactional
	public QuOrderby upOptionName(String quId,String quItemId, String optionName) {
		if(quItemId!=null && !"".equals(quItemId)){
			QuOrderby quOrderby=quOrderbyDao.get(quItemId);
			quOrderby.setOptionName(optionName);
			quOrderbyDao.save(quOrderby);	
			return quOrderby;
		}else{
			//取orderById
			int orderById=getOrderById(quId);
			//新加选项
			QuOrderby quOrderby=new QuOrderby();
			quOrderby.setQuId(quId);
			quOrderby.setOptionName(optionName);
			//title
			quOrderby.setOrderById(++orderById);
			quOrderby.setOptionTitle(orderById+"");
			quOrderbyDao.save(quOrderby);
			return quOrderby;
		}
	}
	
	@Override
	@Transactional
	public List<QuOrderby> saveManyOptions(String quId,List<QuOrderby> quOrderbys) {
		//取orderById
		int orderById=getOrderById(quId);
		for (QuOrderby quOrderby : quOrderbys) {
			//新加选项
			quOrderby.setOrderById(++orderById);
			quOrderby.setOptionTitle(orderById+"");
			quOrderbyDao.save(quOrderby);
		}
		return quOrderbys;
	}
	
	@Override
	@Transactional
	public void ajaxDelete(String quItemId) {
		QuOrderby quOrderby=get(quItemId);
		quOrderby.setVisibility(0);
		quOrderbyDao.save(quOrderby);
	}
	
	@Override
	@Transactional
	public void saveAttr(String quItemId) {
		QuOrderby quOrderby=get(quItemId);
		quOrderbyDao.save(quOrderby);
	}
}
