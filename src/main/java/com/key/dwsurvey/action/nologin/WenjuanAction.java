package com.key.dwsurvey.action.nologin;

import com.key.common.utils.web.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;

/**
 * WenjuanAction
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://www.dwsurvey.net
 *
 */
@Namespace("/")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack")})
@Results({
	@Result(name=SurveyAction.ANSERSURVEY_MOBILE,location="/WEB-INF/page/content/diaowen-design/answer-survey-mobile.jsp",type=Struts2Utils.DISPATCHER)
})
public class WenjuanAction extends ActionSupport{

	@Override
	public String execute() throws Exception {
		return super.execute();
	}
}
