package com.key.dwsurvey.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.key.dwsurvey.entity.SurveyDetail;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.service.SurveyStyleManager;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.key.common.base.action.CrudActionSupport;
import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.SurveyStyle;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 问卷样式
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */

@Namespaces({@Namespace("/design")})
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack")})
@Results({
	@Result(name=ActionSupport.SUCCESS,location="/WEB-INF/page/content/diaowen-design/survey_html_model.jsp",type=Struts2Utils.DISPATCHER)
})
@AllowedMethods({"ajaxGetStyle"})
public class MySurveyStyleAction extends CrudActionSupport<SurveyStyle, String>{
	
	private String surveyId;
	@Autowired
	private SurveyStyleManager surveyStyleManager;
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private AccountManager accountManager;
	
	public String save() throws Exception  {
	    HttpServletRequest request=Struts2Utils.getRequest();
		HttpServletResponse response=Struts2Utils.getResponse();
		//保存收集规则 
		String effective=request.getParameter("effective");
		String effectiveIp=request.getParameter("effectiveIp");
		String rule=request.getParameter("rule");
		String ruleCode=request.getParameter("ruleCode");
		String mailOnly=request.getParameter("mailOnly");
		String ynEndNum=request.getParameter("ynEndNum");
		String ynEndTime=request.getParameter("ynEndTime");
		String endTime=request.getParameter("endTime");
		String endNum=request.getParameter("endNum");
		String showShareSurvey=request.getParameter("showShareSurvey");
		String showAnswerDa=request.getParameter("showAnswerDa");
		String refresh=request.getParameter("refresh"); 
		//保存属性
		
		try{
			User user=accountManager.getCurUser();
	    	if(user!=null){
	    		//SurveyDirectory survey=surveyDirectoryManager.getSurveyByUser(surveyId, user.getId());
	    		SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
	    		if(survey!=null && user.getId().equals(survey.getUserId())){
	    			
	    			SurveyDetail surveyDetail=survey.getSurveyDetail();
	    			if(effective!=null && !"".equals(effective)){
	    			    surveyDetail.setEffective(Integer.parseInt(effective));
	    			}
	    			if(effectiveIp!=null && !"".equals(effectiveIp)){
	    			    surveyDetail.setEffectiveIp(Integer.parseInt(effectiveIp));
	    			}
	    			if(rule!=null && !"".equals(rule)){
	    			    surveyDetail.setRule(Integer.parseInt(rule));
	    			    surveyDetail.setRuleCode(ruleCode);
	    			}
	    			if(refresh!=null && !"".equals(refresh)){
	    			    surveyDetail.setRefresh(Integer.parseInt(refresh));
	    			}
	    			if(mailOnly!=null && !"".equals(mailOnly)){
	    			    surveyDetail.setMailOnly(Integer.parseInt(mailOnly));
	    			}
	    			if(ynEndNum!=null && !"".equals(ynEndNum)){
	    			    surveyDetail.setYnEndNum(Integer.parseInt(ynEndNum));
	    			    //surveyDetail.setEndNum(Integer.parseInt(endNum));
	    			    if(endNum!=null && endNum.matches("\\d*")){
	    			    	surveyDetail.setEndNum(Integer.parseInt(endNum));			
	    			    }
	    			}
	    			if(ynEndTime!=null && !"".equals(ynEndTime)){
	    			    surveyDetail.setYnEndTime(Integer.parseInt(ynEndTime));
//	    				surveyDetail.setEndTime(endTime);
	    			    surveyDetail.setEndTime(new Date());
	    			}
	    			if(showShareSurvey!=null && !"".equals(showShareSurvey)){
	    			    surveyDetail.setShowShareSurvey(Integer.parseInt(showShareSurvey));
	    			    survey.setIsShare(Integer.parseInt(showShareSurvey));
	    			}
	    			if(showAnswerDa!=null && !"".equals(showAnswerDa)){
	    			    surveyDetail.setShowAnswerDa(Integer.parseInt(showAnswerDa));
	    			    survey.setViewAnswer(Integer.parseInt(showAnswerDa));
	    			}
	    			surveyDirectoryManager.save(survey);
//	    			surveyDirectoryManager.saveUserSurvey(survey);
	    			
	    			//保存样式 
	    			surveyStyleManager.save(entity);
	    			response.getWriter().write("true");
	    			response.getWriter().close();
	    		}
	    	}
		}catch(Exception e){
			e.printStackTrace();
		}
		return NONE;
	}
	
	public String ajaxGetStyle() throws Exception {
	    try{
			HttpServletResponse response=Struts2Utils.getResponse();
			entity=surveyStyleManager.get(id);
			String jsonObj=JSONObject.fromObject(entity).toString();
			response.getWriter().write(jsonObj);
		 }catch (Exception e) {
			e.printStackTrace();
		 }
	    return null;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		if(surveyId!=null && !"".equals(surveyId)){
			entity=surveyStyleManager.getBySurveyId(surveyId);
		}
		if(entity==null){
			entity=new SurveyStyle();
		}
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	
}
