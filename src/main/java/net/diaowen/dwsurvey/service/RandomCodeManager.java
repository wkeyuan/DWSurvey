package net.diaowen.dwsurvey.service;

import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.RandomCode;

/**
 * 答卷业务
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface RandomCodeManager extends BaseService<RandomCode, String> {

	public HttpResult createSmsCode(String rdName, int rdType, int eventType);

	public RandomCode findLastRc(RandomCode randomCode);

	public RandomCode findLastRc(String rdName, Integer rdType, Integer rdEvent);

	public HttpResult createMailCode(String userId,String rdName,int rdType,int eventType);

	public RandomCode findLastRcByUserId(String userId,String code, Integer rdType, Integer rdEvent);

}
