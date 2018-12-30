package com.key.dwsurvey.action.survey;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 收集入口 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://www.dwsurvey.net
 *
 */
@Namespace("/design")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack")})
@Results({
	@Result(name=MyCollectAction.COLLECT1,location="/WEB-INF/page/content/diaowen-collect/collect_1.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=MyCollectAction.IFRAME,location="/WEB-INF/page/content/diaowen-collect/collect_iframe.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=MyCollectAction.SITECOMP,location="/WEB-INF/page/content/diaowen-collect/collect_website.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=MyCollectAction.WEIXIN,location="/WEB-INF/page/content/diaowen-collect/collect_weixin.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=MyCollectAction.SHARE,location="/WEB-INF/page/content/diaowen-collect/collect_2.jsp",type=Struts2Utils.DISPATCHER)
})
public class MyCollectAction extends ActionSupport{
	
	protected final static String COLLECT1="collect1";
	protected final static String IFRAME="iframe";
	protected final static String SITECOMP="sitecomp";
	protected final static String WEIXIN="weixin";
	protected final static String SHARE="share";
	
	private String surveyId;
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private AccountManager accountManager;

	
	@Override
	public String execute() throws Exception {
		HttpServletRequest request=Struts2Utils.getRequest();
		String tabId=request.getParameter("tabId");

		String baseUrl = "";
		baseUrl = request.getScheme() +"://" + request.getServerName()  
						+ (request.getServerPort() == 80 ? "" : ":" +request.getServerPort())
                        + request.getContextPath();

		request.setAttribute("baseUrl", baseUrl);

		User user=accountManager.getCurUser();
    	if(user!=null){
    		SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(surveyId, user.getId());
    		if(surveyDirectory!=null){
    			request.setAttribute("survey", surveyDirectory);
    			if(IFRAME.equals(tabId)){
    				return IFRAME;
    			}else if(SITECOMP.equals(tabId)){
    				return SITECOMP;
    			}else if(WEIXIN.equals(tabId)){
    				return WEIXIN;
    			}else if(SHARE.equals(tabId)){
    				return SHARE;
    			}
    			return COLLECT1;
    		}
    	}
		return null;
	}
	
	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	
	
}
