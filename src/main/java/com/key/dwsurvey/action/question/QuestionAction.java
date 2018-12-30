package com.key.dwsurvey.action.question;

import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.service.QuestionManager;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 题目 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Namespaces({@Namespace("/design")})
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack") })
@Results({})
@AllowedMethods({"ajaxDelete"})
public class QuestionAction extends ActionSupport{
	@Autowired
	private QuestionManager questionManager;
	/**
	 * ajax删除
	 * @return
	 * @throws Exception
	 */
	public String ajaxDelete() throws Exception {
		
		HttpServletRequest request=Struts2Utils.getRequest();
		HttpServletResponse response=Struts2Utils.getResponse();
		String responseStr="";
		try{
			String delQuId=request.getParameter("quId");
			questionManager.delete(delQuId);	
			responseStr="true";
		}catch (Exception e) {
			responseStr="false";
		}
		response.getWriter().write(responseStr);
		return null;
	}
	
}
