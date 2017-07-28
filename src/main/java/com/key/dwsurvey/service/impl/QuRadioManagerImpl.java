package com.key.dwsurvey.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.key.dwsurvey.dao.QuRadioDao;
import com.key.dwsurvey.entity.QuRadio;
import com.key.dwsurvey.service.QuRadioManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.service.BaseServiceImpl;


/**
 * 单选题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service("quRadioManager")
public class QuRadioManagerImpl extends BaseServiceImpl<QuRadio, String> implements QuRadioManager {

	@Autowired
	private QuRadioDao quRadioDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=quRadioDao;
	}
	/*******************************************************************8
	 * 查询操作
	 */

	/**
	 * 得到某一题的选项
	 */
	public List<QuRadio> findByQuId(String quId){
		Page<QuRadio> page=new Page<QuRadio>();
		page.setOrderBy("orderById");
		page.setOrderDir("asc");
		
		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_quId", quId));
		filters.add(new PropertyFilter("EQI_visibility", "1"));
		return findAll(page, filters);
	}
	
	public int getOrderById(String quId){
		Criterion criterion=Restrictions.eq("quId", quId);
		QuRadio quRadio=quRadioDao.findFirst("orderById", false, criterion);
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
	public QuRadio upOptionName(String quId,String quItemId, String optionName) {
		if(quItemId!=null && !"".equals(quItemId)){
			QuRadio quRadio=quRadioDao.get(quItemId);
			quRadio.setOptionName(optionName);
			quRadioDao.save(quRadio);	
			return quRadio;
		}else{
			//取orderById
			int orderById=getOrderById(quId);
			//新加选项
			QuRadio quRadio=new QuRadio();
			quRadio.setQuId(quId);
			quRadio.setOptionName(optionName);
			//title
			quRadio.setOrderById(++orderById);
			quRadio.setOptionTitle(orderById+"");
			quRadioDao.save(quRadio);
			return quRadio;
		}
	}
	
	@Override
	@Transactional
	public List<QuRadio> saveManyOptions(String quId,List<QuRadio> quRadios) {
		//取orderById
		int orderById=getOrderById(quId);
		for (QuRadio quRadio : quRadios) {
			//新加选项
			quRadio.setOrderById(++orderById);
			quRadio.setOptionTitle(orderById+"");
			quRadioDao.save(quRadio);
		}
		return quRadios;
	}
	
	@Override
	@Transactional
	public void ajaxDelete(String quItemId) {
		QuRadio quRadio=get(quItemId);
		String quId=quRadio.getQuId();
		int orderById=quRadio.getOrderById();
		quRadioDao.delete(quItemId);
		//修改排序号
		quRadioDao.quOrderByIdDel1(quId, orderById);
	}
	
	@Override
	@Transactional
	public void saveAttr(String quItemId, String isNote) {
		QuRadio quRadio=get(quItemId);
		if(isNote!=null && "1".equals(isNote)){
			quRadio.setIsNote(1);
		}else{
			quRadio.setIsNote(0);
		}
		quRadioDao.save(quRadio);
	}
}
