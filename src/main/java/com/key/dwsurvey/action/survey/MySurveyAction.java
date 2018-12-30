package com.key.dwsurvey.action.survey;

import com.key.common.base.action.CrudActionSupport;
import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 我的问卷 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Namespace("/design")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack")})
@Results({
    @Result(name=MySurveyAction.SUCCESS,location="/WEB-INF/page/content/diaowen-design/list.jsp",type=Struts2Utils.DISPATCHER),
    @Result(name="design",location="/design/my-survey-design.action?surveyId=${id}",type=Struts2Utils.REDIRECT),
})
@AllowedMethods({"surveyState","attrs"})
public class MySurveyAction extends CrudActionSupport<SurveyDirectory, String>{
	
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private AccountManager accountManager;

	@Override
	public String list() throws Exception {
		HttpServletRequest request=Struts2Utils.getRequest();
		String surveyState = request.getParameter("surveyState");
		if(surveyState==null||"".equals(surveyState)){
			entity.setSurveyState(null);
		}
	    page=surveyDirectoryManager.findByUser(page,entity);
	    return SUCCESS;
	}
	
	public String delete() throws Exception {
	    HttpServletResponse response=Struts2Utils.getResponse();
	    String result="false";
	    try{
		User user = accountManager.getCurUser();
		if(user!=null){
		    String userId=user.getId();
		    SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(id,userId);
		    if(surveyDirectory!=null){
		    	surveyDirectoryManager.delete(id);
		    	result="true";
		    }
		}
	    }catch (Exception e) {
			result="false";
	    }
	    response.getWriter().write(result);
	    return null;
	}
	
	//问卷壮态设置
	public String surveyState() throws Exception{
		HttpServletResponse resp=Struts2Utils.getResponse();
		String result="";
		try{
			User user= accountManager.getCurUser();
			if(user!=null){
				String userId=user.getId();
				SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(id, userId);
				if(surveyDirectory!=null){
					int surveyState=entity.getSurveyState();
					surveyDirectory.setSurveyState(surveyState);
				}
			}
			result="true";
		}catch(Exception e){
			e.printStackTrace();
			result="error";
		}
		resp.getWriter().write(result);
		return null;
	}
	


	public String attrs() throws Exception {
		HttpServletRequest request=Struts2Utils.getRequest();
		HttpServletResponse response=Struts2Utils.getResponse();
		try{
			SurveyDirectory survey=surveyDirectoryManager.getSurvey(id);
			JsonConfig cfg = new JsonConfig();
			cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
			JSONObject jsonObject=JSONObject.fromObject(survey,cfg);
			response.getWriter().write(jsonObject.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	protected void prepareModel() throws Exception {
		entity=surveyDirectoryManager.getModel(id);
	}
	
	public void prepareSurveyState() throws Exception {
		prepareModel();
	}

	public void prepareExecute() throws Exception {
		prepareModel();
	}
	
}
