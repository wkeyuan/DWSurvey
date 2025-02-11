package net.diaowen.dwsurvey.service.impl;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.HttpStatus;
import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.common.utils.RandomUtils;
import net.diaowen.dwsurvey.dao.RandomCodeDao;
import net.diaowen.dwsurvey.entity.RandomCode;
import net.diaowen.dwsurvey.service.RandomCodeManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author keyuan
 * keyuan258@gmail.com
 *
 */

@Service
public class RandomCodeManagerImpl extends BaseServiceImpl<RandomCode, String> implements RandomCodeManager {

	@Autowired
	private RandomCodeDao randomCodeDao;
	@Autowired
	private AccountManager accountManager;

	@Override
	public void setBaseDao() {
		this.baseDao=randomCodeDao;
	}


	public HttpResult createSmsCode(String rdName,int rdType,int eventType) {
//		Object[] results = new Object[2];
		RandomCode randomCode = new RandomCode();
		randomCode.setRdName(rdName);
		randomCode.setRdType(rdType);
		randomCode.setRdEvent(eventType);
		randomCode.setRdStatus(1);

		if(rdName==null && "".equals(rdName)){
//			results[1] = "20000";
			// 用户名不合法
			return HttpResult.buildResult(HttpStatus.SERVER_30003);
		}


		if(rdType==1){
			if(eventType==1 ){
				//注册，手机：则判断手机是否已经注册过
				User user = accountManager.findUserByLoginNameOrEmail(rdName);
				if(user!=null){
//				results[1] = "10000";
					// 用户名存在
					return HttpResult.buildResult(HttpStatus.SERVER_30001);
				}
			}else if(eventType==2){
				//登录
				User user = accountManager.findUserByLoginNameOrEmail(rdName);
				if(user==null){
//				results[1] = "10002";//未
					// 用户名不存在
					return HttpResult.buildResult(HttpStatus.SERVER_30002);
				}
			}else if(eventType==3){
				//抽奖

			}else if(eventType==5){
				//解绑手机号
				User user = accountManager.findUserByLoginNameOrEmail(rdName);
				// 用户名不存在
				if(user==null) return HttpResult.buildResult(HttpStatus.SERVER_30002);
			}else if(eventType==6){
				//绑新手机号
				User user = accountManager.findUserByLoginNameOrEmail(rdName);
				// 绑定关系存在
				if(user!=null) return HttpResult.buildResult(HttpStatus.SERVER_30001);
			}
		}

		if(checkedRandom(randomCode)==null){
			String code = RandomUtils.getVerifyCode()+"";
			randomCode.setRdCode(code);
			randomCodeDao.save(randomCode);
			return HttpResult.buildResult(HttpStatus.SUCCESS,randomCode);
		}else{
			// 60秒内禁止重复提交
//				results[1]="10001";
			return HttpResult.buildResult(HttpStatus.SERVER_30008);
		}

	}

	/**
	 * 绑定邮箱时执行，其它的再根据情况加
	 * @param rdName
	 * @param rdType
	 * @param eventType
	 * @return
	 */
	public HttpResult createMailCode(String userId,String rdName,int rdType,int eventType) {
//		Object[] results = new Object[2];
		RandomCode randomCode = new RandomCode();
		randomCode.setRdName(rdName);
		randomCode.setRdType(rdType);
		randomCode.setRdEvent(eventType);
		randomCode.setRdStatus(1);
		randomCode.setUserId(userId);

		if(rdName==null && "".equals(rdName)){
//			results[1] = "20000";
			// 数据格式不合法，这里是邮箱
			return HttpResult.buildResult(HttpStatus.SERVER_30003);
		}

//		type=2 event = 3
		if(eventType==3 && rdType==2){
			//绑定时，邮箱：则判断邮箱是否已经注册过
			User user = accountManager.findUserByLoginNameOrEmail(rdName);
			if(user!=null){
//				results[1] = "10000";
				// 数据已经存在，这里是指邮箱
				return HttpResult.buildResult(HttpStatus.SERVER_30001);
			}
		}

		if(checkedRandom(randomCode)==null){
			String code = RandomUtils.getVerifyCode()+"";
			randomCode.setRdCode(code);
			randomCodeDao.save(randomCode);
			return HttpResult.buildResult(HttpStatus.SUCCESS,randomCode);
		}else{
			// 60秒内禁止重复提交
//				results[1]="10001";
			return HttpResult.buildResult(HttpStatus.SERVER_30008);
		}

	}

	public RandomCode checkedRandom(RandomCode randomCode){
		RandomCode lastRc = findLastRc(randomCode);
		if(lastRc!=null){
			Date createDate = lastRc.getCreateDate();
			Date curDate = new Date();

			long d = (curDate.getTime()-createDate.getTime())/1000;
			if(d<=60){
				return randomCode;
			}
		}
		return null;
	}

	public RandomCode findLastRc(RandomCode randomCode){
		return findLastRc(randomCode.getRdName(),randomCode.getRdType(),randomCode.getRdEvent());
	}

	public RandomCode findLastRc(String rdName, Integer rdType, Integer rdEvent){
		Criterion cri1 = Restrictions.eq("rdName",rdName);
		Criterion cri2 = Restrictions.eq("rdType",rdType);
		Criterion cri3 = Restrictions.eq("rdEvent",rdEvent);
		Criterion cri4 = Restrictions.eq("rdStatus",1);
		List<RandomCode> randomCodeList = randomCodeDao.findByOrder("createDate",false,cri1,cri2,cri3,cri4);
		if(randomCodeList!=null && randomCodeList.size()>0){
			RandomCode lastRC = randomCodeList.get(0);
			return lastRC;
		}
		return null;
	}

	public RandomCode findLastRcByUserId(String userId, String code, Integer rdType, Integer rdEvent) {
		Criterion cri1 = Restrictions.eq("userId",userId);
		Criterion cri2 = Restrictions.eq("rdType",rdType);
		Criterion cri3 = Restrictions.eq("rdEvent",rdEvent);
//		Criterion cri4 = Restrictions.eq("rdStatus",1);
		Criterion cri5 = Restrictions.eq("rdCode",code);
		List<RandomCode> randomCodeList = randomCodeDao.findByOrder("createDate",false,cri1,cri2,cri3,cri5);
		if(randomCodeList!=null && randomCodeList.size()>0){
			RandomCode lastRC = randomCodeList.get(0);
			return lastRC;
		}
		return null;
	}


}
