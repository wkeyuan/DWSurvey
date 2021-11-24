package net.diaowen.dwsurvey.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyAnswer;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 答卷数据
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Controller
@RequestMapping("/da/my-survey-answer")
public class MySurveyAnswerController{
	@Autowired
	private SurveyAnswerManager surveyAnswerManager;
	@Autowired
	private SurveyDirectoryManager directoryManager;
	@Autowired
	private AccountManager accountManager;

	protected final static String RESPONSE_ANSWER = "responseAnswer";

	@RequestMapping("/answer.do")
	public ModelAndView execute(HttpServletRequest request,Page<SurveyAnswer> page,String surveyId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		User user=accountManager.getCurUser();
    	if(user!=null){
    		SurveyDirectory survey=directoryManager.getSurveyByUser(surveyId, user.getId());
    		if(survey!=null){
    			page=surveyAnswerManager.answerPage(page, surveyId);
    		}
			modelAndView.addObject("page",page);
			modelAndView.addObject("survey",survey);
    	}
		modelAndView.addObject("surveyId",surveyId);
    	modelAndView.setViewName("/diaowen-da/survey-answer-data");
		return modelAndView;
	}

	@RequestMapping("/responseAnswer.do")
	public ModelAndView responseAnswer(String answerId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		List<Question> questions = new ArrayList<Question>();
		try {
			SurveyDirectory directory=new SurveyDirectory();
			if (answerId != null) {
				SurveyAnswer answer = surveyAnswerManager.getModel(answerId);
				questions = surveyAnswerManager.findAnswerDetail(answer);
				User user=accountManager.getCurUser();
		    	if(answer.getSurveyId() != null && user!=null){
		    		SurveyDirectory survey=directoryManager.getSurveyByUser(answer.getSurveyId(), user.getId());
		    		if(survey!=null){
		    			modelAndView.addObject("questions", questions);
						modelAndView.addObject("directory", directory);
		    		}
		    	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelAndView.setViewName("/diaowen-answer/response-answer");
		return modelAndView;
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(String answerId) throws Exception {
		String result="error";
		try{
			if (answerId != null) {
				SurveyAnswer answer = surveyAnswerManager.getModel(answerId);
				User user=accountManager.getCurUser();
		    	if(answer.getSurveyId() != null && user!=null){
		    		SurveyDirectory survey=directoryManager.getSurveyByUser(answer.getSurveyId(), user.getId());
		    		if(survey!=null){
		    			surveyAnswerManager.delete(answer);
						Integer answerNum = survey.getAnswerNum();
						if(answerNum!=null && answerNum>=1){
							survey.setAnswerNum(answerNum-1);
							directoryManager.save(survey);
						}
		    		}
		    	}
			}
			result="true";
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("/exportXLS.do")
	@ResponseBody
	public String exportXLS(HttpServletRequest request,HttpServletResponse response,String surveyId) throws Exception{
		try{
//			String savePath = request.getSession().getServletContext().getRealPath("/");
			String savePath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
			User user=accountManager.getCurUser();
	    	if(user!=null){
	    		SurveyDirectory survey=directoryManager.getSurveyByUser(surveyId, user.getId());
	    		if(survey!=null){
	    			savePath=surveyAnswerManager.exportXLS(surveyId,savePath);
//					savePath = URLEncoder.encode(savePath, "utf-8");
//	    			response.sendRedirect(request.getContextPath()+savePath);
					response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode("dwsurvey_"+survey.getSid()+".xlsx", "UTF-8"));
					request.getServletContext().getRequestDispatcher(savePath).forward(request,response);
	    		}
	    	}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
