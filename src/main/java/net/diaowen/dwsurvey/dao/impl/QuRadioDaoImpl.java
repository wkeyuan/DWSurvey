package net.diaowen.dwsurvey.dao.impl;

import net.diaowen.dwsurvey.dao.QuRadioDao;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import net.diaowen.common.dao.BaseDaoImpl;
import net.diaowen.dwsurvey.entity.QuRadio;

/**
 * 单选题 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

//@Repository("quRadioDao")
@Repository
public class QuRadioDaoImpl extends BaseDaoImpl<QuRadio, String> implements QuRadioDao {

	public void quOrderByIdDel1(String quId,Integer orderById){
		if(quId!=null && !"".equals(quId)){
			String sql="update t_qu_radio set order_by_id=order_by_id-1 where qu_id=? and order_by_id>=?";
			//更新排序号
			SQLQuery query=this.getSession().createSQLQuery(sql);
			query.setParameter(1, quId);
			query.setParameter(2, orderById);
			query.executeUpdate();
		}
	}

}
