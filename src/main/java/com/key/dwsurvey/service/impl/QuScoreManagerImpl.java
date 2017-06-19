package com.key.dwsurvey.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.key.dwsurvey.service.QuScoreManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.dao.QuScoreDao;
import com.key.dwsurvey.entity.QuScore;


/**
 * 评分题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class QuScoreManagerImpl extends BaseServiceImpl<QuScore, String> implements QuScoreManager {

	@Autowired
	private QuScoreDao quScoreDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=quScoreDao;
	}

	public List<QuScore> findByQuId(String quId){
		Page<QuScore> page=new Page<QuScore>();
		page.setOrderBy("orderById");
		page.setOrderDir("asc");
		
		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_quId", quId));
		filters.add(new PropertyFilter("EQI_visibility", "1"));
		return findAll(page, filters);
	}
	
	public int getOrderById(String quId){
		Criterion criterion=Restrictions.eq("quId", quId);
		QuScore quRadio=quScoreDao.findFirst("orderById", false, criterion);
		if(quRadio!=null){
			return quRadio.getOrderById();
		}
		return 0;
	}
	
	
	
	/*******************************************************************8
	 * 更新操作
	 */
	
	@Override
	@Transactional
	public QuScore upOptionName(String quId,String quItemId, String optionName) {
		if(quItemId!=null && !"".equals(quItemId)){
			QuScore quScore=quScoreDao.get(quItemId);
			quScore.setOptionName(optionName);
			quScoreDao.save(quScore);	
			return quScore;
		}else{
			//取orderById
			int orderById=getOrderById(quId);
			//新加选项
			QuScore quScore=new QuScore();
			quScore.setQuId(quId);
			quScore.setOptionName(optionName);
			//title
			quScore.setOrderById(++orderById);
			quScore.setOptionTitle(orderById+"");
			quScoreDao.save(quScore);
			return quScore;
		}
	}
	
	@Override
	@Transactional
	public List<QuScore> saveManyOptions(String quId,List<QuScore> quScores) {
		//取orderById
		int orderById=getOrderById(quId);
		for (QuScore quScore : quScores) {
			//新加选项
			quScore.setOrderById(++orderById);
			quScore.setOptionTitle(orderById+"");
			quScoreDao.save(quScore);
		}
		return quScores;
	}
	
	@Override
	@Transactional
	public void ajaxDelete(String quItemId) {
		QuScore quScore=get(quItemId);
		quScore.setVisibility(0);
		quScoreDao.save(quScore);
	}
	
	@Override
	@Transactional
	public void saveAttr(String quItemId) {
		QuScore quScore=get(quItemId);
		quScoreDao.save(quScore);
	}
}
