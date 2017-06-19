package com.key.dwsurvey.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.web.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户中心 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Namespace("/ic")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack")})
@Results({
	@Result(name=UserAction.MYACCOUNT,location="/WEB-INF/page/content/diaowen-center/my-account.jsp",type=Struts2Utils.DISPATCHER)
})
@AllowedMethods({"myaccount"})
public class UserAction extends ActionSupport{
	
	public final static String MYACCOUNT="myaccount";
	
	@Autowired
	private AccountManager accountManager;
	
	public String myaccount() throws Exception {
		HttpServletRequest request=Struts2Utils.getRequest();
		User user=accountManager.getCurUser();
		request.setAttribute("user", user);
		return MYACCOUNT;
	}
	
	
}
