package net.diaowen.dwsurvey.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.dwsurvey.entity.SurveyDetail;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.SurveyStyle;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.SurveyStyleManager;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 问卷样式
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Controller
@RequestMapping("/design/my-survey-attr")
public class MySurveyAttrController {

	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private AccountManager accountManager;

	@RequestMapping("/save.do")
	@ResponseBody
	public String save(HttpServletRequest request, String surveyId) throws Exception  {
	    String effective=request.getParameter("effective");
		String effectiveIp=request.getParameter("effectiveIp");
		String rule=request.getParameter("rule");
		String ruleCode=request.getParameter("ruleCode");
		String mailOnly=request.getParameter("mailOnly");
		String ynEndNum=request.getParameter("ynEndNum");
		String ynEndTime=request.getParameter("ynEndTime");
		String endTime=request.getParameter("endTime");

		String ynStartTime=request.getParameter("ynStartTime");
		String startTime=request.getParameter("startTime");

		String endNum=request.getParameter("endNum");
		String showShareSurvey=request.getParameter("showShareSurvey");
		String showAnswerDa=request.getParameter("showAnswerDa");
		String refresh=request.getParameter("refresh");
		try{
			User user=accountManager.getCurUser();
	    	if(user!=null){
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
	    			    if(endNum!=null && endNum.matches("\\d*")){
	    			    	surveyDetail.setEndNum(Integer.parseInt(endNum));			
	    			    }
	    			}
	    			if(ynEndTime!=null && !"".equals(ynEndTime) && endTime!=null && !"".equals(endTime) ){
	    			    surveyDetail.setYnEndTime(Integer.parseInt(ynEndTime));
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try{
							surveyDetail.setEndTime(dateFormat.parse(endTime));
						}catch (Exception e){
							e.printStackTrace();
						}
	    			}
					if(ynStartTime!=null && !"".equals(ynStartTime)  && startTime!=null && !"".equals(startTime) ){
						surveyDetail.setYnStartTime(Integer.parseInt(ynStartTime));
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try{
							surveyDetail.setStartTime(dateFormat.parse(startTime));
						}catch (Exception e){
							e.printStackTrace();
						}
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
	    			return "true";
	    		}
	    	}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "error";
	}


}
