package com.key.common.dao;

import java.io.Serializable;


/**
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface BaseDao<T,ID extends Serializable> extends IHibernateDao<T, ID>{
	
}
