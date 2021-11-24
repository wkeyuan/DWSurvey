package net.diaowen.dwsurvey.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 收集入口 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Controller
@RequestMapping("/collect/my-collect")
public class MyCollectController {
	
	protected final static String COLLECT1="collect1";
	protected final static String IFRAME="iframe";
	protected final static String SITECOMP="sitecomp";
	protected final static String WEIXIN="weixin";
	protected final static String SHARE="share";

	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private AccountManager accountManager;

	@RequestMapping("/collect.do")
	public ModelAndView collect(HttpServletRequest request,String surveyId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String tabId=request.getParameter("tabId");
		String baseUrl = "";
		baseUrl = request.getScheme() +"://" + request.getServerName()  
						+ (request.getServerPort() == 80 ? "" : ":" +request.getServerPort())
                        + request.getContextPath();
		modelAndView.addObject("baseUrl", baseUrl);
		User user=accountManager.getCurUser();
    	if(user!=null){
    		SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(surveyId, user.getId());
    		if(surveyDirectory!=null){
				modelAndView.addObject("survey", surveyDirectory);
    			if(IFRAME.equals(tabId)){
					modelAndView.setViewName("/diaowen-collect/collect_iframe");
    			}else if(SITECOMP.equals(tabId)){
					modelAndView.setViewName("/diaowen-collect/collect_website");
    			}else if(WEIXIN.equals(tabId)){
					modelAndView.setViewName("/diaowen-collect/collect_weixin");
    			}else if(SHARE.equals(tabId)){
					modelAndView.setViewName("/diaowen-collect/collect_2");
    			}else{
					modelAndView.setViewName("/diaowen-collect/collect_1");
				}
    		}
    	}
		modelAndView.addObject("surveyId", surveyId);
		return modelAndView;
	}

}
