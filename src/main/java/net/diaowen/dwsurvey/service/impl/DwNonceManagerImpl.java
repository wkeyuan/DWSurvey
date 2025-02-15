package net.diaowen.dwsurvey.service.impl;

import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.dwsurvey.dao.DwNonceDao;
import net.diaowen.dwsurvey.entity.DwNonce;
import net.diaowen.dwsurvey.service.DwNonceManager;
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
public class DwNonceManagerImpl extends BaseServiceImpl<DwNonce, String> implements DwNonceManager {

	@Autowired
	private DwNonceDao dwNonceDao;

	@Override
	public void setBaseDao() {
		this.baseDao=dwNonceDao;
	}

	public DwNonce findByNonce(String signature,String nonce){
		Criterion cri1 = Restrictions.eq("signature",signature);
		Criterion cri2 = Restrictions.eq("nonceStr",nonce);
		List<DwNonce> dwNonces = dwNonceDao.find(cri1,cri2);
		if(dwNonces!=null && dwNonces.size()>0){
			return dwNonces.get(0);
		}
		return null;
	}

	public boolean isContainNonce(String signature,String nonce) {
		DwNonce dwNonce = findByNonce(signature,nonce);
		if(dwNonce!=null){
			return true;
		}
		return false;
	}

}
