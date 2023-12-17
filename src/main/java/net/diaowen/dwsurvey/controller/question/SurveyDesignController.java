package net.diaowen.dwsurvey.controller.question;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyDetail;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.service.QuestionManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * 设计问卷
 * @author keyuan(keyuan258@gmail.com)
 *
 */
@RestController
@RequestMapping("/api/dwsurvey/app/design/survey-design")
public class SurveyDesignController {

	private final QuestionManager questionManager;
	private final SurveyDirectoryManager surveyDirectoryManager;
	private final AccountManager accountManager;

	@Autowired
	public SurveyDesignController(QuestionManager questionManager, SurveyDirectoryManager surveyDirectoryManager, AccountManager accountManager) {
		this.questionManager = questionManager;
		this.surveyDirectoryManager = surveyDirectoryManager;
		this.accountManager = accountManager;
	}


	/**
	 * 获取问卷
	 * @param surveyId
	 * @param sid
	 * @return
	 */
	@RequestMapping("/surveyAll.do")
	public HttpResult surveyAll(String surveyId,String sid) {
		try{
			return buildSurvey(surveyId,sid);
		}catch (Exception e){
			e.printStackTrace();
		}
		return HttpResult.FAILURE();
	}

	/**
	 * 根据问卷 id 保存问卷
	 * @param request
	 * @param surveyId
	 * @return
	 */
	@RequestMapping("/devSurvey.do")
	public HttpResult devSurvey(HttpServletRequest request, String surveyId) {

		if(accountManager.getCurUser()!=null){
			SurveyDirectory survey=surveyDirectoryManager.get(surveyId);
			try{
				surveyDirectoryManager.devSurvey(survey);
				return HttpResult.SUCCESS();
			}catch (Exception e) {
				e.printStackTrace();
			}

			return HttpResult.FAILURE();
		}else{
			return HttpResult.NOLOGIN();
		}
	}

	/**
	 * 根据问卷 id 保存问卷
	 * @param request
	 * @param response
	 * @param surveyId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajaxSave.do")
	public String ajaxSave(HttpServletRequest request,HttpServletResponse response,String surveyId) throws Exception {
		String svyName=request.getParameter("svyName");
		String svyNameText=request.getParameter("svyNameText");
		String svyNote=request.getParameter("svyNote");
		//属性
		String effective=request.getParameter("effective");
		String effectiveIp=request.getParameter("effectiveIp");
		String rule=request.getParameter("rule");
		String ruleCode=request.getParameter("ruleCode");
		String refresh=request.getParameter("refresh");
		String mailOnly=request.getParameter("mailOnly");
		String ynEndNum=request.getParameter("ynEndNum");
		String endNum=request.getParameter("endNum");
		String ynEndTime=request.getParameter("ynEndTime");
		String endTime=request.getParameter("endTime");
		String showShareSurvey=request.getParameter("showShareSurvey");
		String showAnswerDa=request.getParameter("showAnswerDa");

		SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
		SurveyDetail surveyDetail=survey.getSurveyDetail();
		User user= accountManager.getCurUser();
		if(user!=null && survey!=null){
			String userId=user.getId();
			if(userId.equals(survey.getUserId())){

				if( svyNote!=null){
					svyNote=URLDecoder.decode(svyNote,"utf-8");
					surveyDetail.setSurveyNote(svyNote);
				}
				if(svyName!=null && !"".equals(svyName)){
					svyName=URLDecoder.decode(svyName,"utf-8");
					survey.setSurveyName(svyName);
				}
				if(StringUtils.isNotEmpty(svyNameText)){
					svyNameText=URLDecoder.decode(svyNameText,"utf-8");
					survey.setSurveyNameText(svyNameText);
				}

				//保存属性
				if(effective!=null && !effective.isEmpty()){
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
				if(ynEndTime!=null && !"".equals(ynEndTime)){
					surveyDetail.setYnEndTime(Integer.parseInt(ynEndTime));
					if(org.apache.commons.lang3.StringUtils.isNotEmpty(endTime)){
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						surveyDetail.setEndTime(simpleDateFormat.parse(endTime));
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

				response.getWriter().write("true");

			}
		}
		return null;
	}

	/**
	 * 判断用户权限，若通过则返回成功和问卷，否则返回提示信息
	 * @param surveyId
	 * @param sid
	 * @return
	 */
	private HttpResult buildSurvey(String surveyId,String sid) {
		//判断是否拥有权限
		SurveyDirectory surveyDirectory
				= StringUtils.isEmpty(surveyId) && StringUtils.isNotEmpty(sid)
				? surveyDirectoryManager.getSurveyBySid(sid)
				: surveyDirectoryManager.getSurvey(surveyId);
		User user= accountManager.getCurUser();
		if(user!=null && user.getId().equals(surveyDirectory.getUserId())){
			List<Question> questions=questionManager.findDetails(surveyDirectory.getId(), "2");
			surveyDirectory.setQuestions(questions);
			surveyDirectory.setSurveyQuNum(questions.size());
			surveyDirectoryManager.save(surveyDirectory);
			return HttpResult.SUCCESS(surveyDirectory);
		}else{
			return HttpResult.FAILURE_MSG("未登录或没有相应数据权限");
		}

	}

}
