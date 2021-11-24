package net.diaowen.dwsurvey.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.jsp2html.DWSurveyBuild;
import net.diaowen.common.utils.DiaowenProperty;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyDetail;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.service.QuestionManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.SurveyReqUrlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 设计问卷
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Controller
@RequestMapping("/design/my-survey-design")
public class MySurveyDesignController {

	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private SurveyReqUrlManager surveyReqUrlManager;
	@Autowired
	private AccountManager accountManager;

	@RequestMapping("/survey.do")
	public ModelAndView survey(String surveyId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		buildSurvey(surveyId,modelAndView);
		modelAndView.setViewName("/diaowen-design/survey");
		return modelAndView;
	}

	@RequestMapping("/previewDev.do")
	public ModelAndView previewDev(String surveyId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		buildSurvey(surveyId,modelAndView);
		modelAndView.setViewName("/diaowen-design/survey_preview_dev");
		return modelAndView;
	}


	@RequestMapping("/devSurvey.do")
	public String devSurvey(HttpServletRequest request,String surveyId,RedirectAttributes attr) throws Exception {
		SurveyDirectory survey=surveyDirectoryManager.get(surveyId);
		Date createDate=survey.getCreateDate();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd/");
		try{
			String filePath="/wjHtml/"+dateFormat.format(createDate);
			String fileName=surveyId+".html";
			survey.setHtmlPath(filePath+fileName);
			DWSurveyBuild dwSurveyBuild = new DWSurveyBuild();
			dwSurveyBuild.devSurvey(request,surveyId,"p");
			dwSurveyBuild.devSurvey(request,surveyId,"m");

			List<Question> questions=questionManager.find(surveyId, "2");
			survey.setSurveyQuNum(questions.size());
			survey.setSurveyState(1);
			surveyDirectoryManager.save(survey);
		}catch (Exception e) {
			e.printStackTrace();
		}
		attr.addAttribute("surveyId",surveyId);
		return "redirect:/collect/my-collect/collect.do";
	}

	private void buildSurvey(String surveyId,ModelAndView modelAndView) {
		//判断是否拥有权限
		User user= accountManager.getCurUser();
		if(user!=null){
			String userId=user.getId();
			SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(surveyId, userId);
			if(surveyDirectory!=null){
				surveyDirectoryManager.getSurveyDetail(surveyId, surveyDirectory);
//				SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
				List<Question> questions=questionManager.findDetails(surveyId, "2");
				surveyDirectory.setQuestions(questions);
				surveyDirectory.setSurveyQuNum(questions.size());
				surveyDirectoryManager.save(surveyDirectory);

				modelAndView.addObject("survey",surveyDirectory);
				modelAndView.addObject("prevHost",DiaowenProperty.STORAGE_URL_PREFIX);
			}else{
				modelAndView.addObject("msg","未登录或没有相应数据权限");
			}
		}else{
			modelAndView.addObject("msg","未登录或没有相应数据权限");
		}
	}

	@RequestMapping("/ajaxSave.do")
	public String ajaxSave(HttpServletRequest request,HttpServletResponse response,String surveyId) throws Exception {
		String svyName=request.getParameter("svyName");
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

		String ynStartTime=request.getParameter("ynStartTime");
		String startTime=request.getParameter("startTime");

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

				//保存属性
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
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try{
						surveyDetail.setEndTime(dateFormat.parse(endTime));
					}catch (Exception e){
						e.printStackTrace();
					}
				}
				if(ynStartTime!=null && !"".equals(ynStartTime)){
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
				survey.setEditDate(new Date());
				surveyDirectoryManager.save(survey);

				response.getWriter().write("true");

			}
		}
		return null;
	}

	@RequestMapping("/copySurvey.do")
	public String copySurvey(HttpServletRequest request,HttpServletResponse response,String surveyId,RedirectAttributes attrs) throws Exception {
		//引用问卷
		String fromBankId=request.getParameter("fromBankId");
		String surveyName=request.getParameter("surveyName");
		surveyName=URLDecoder.decode(surveyName,"utf-8");
		String tag=request.getParameter("tag");
		tag="2";
		SurveyDirectory directory=surveyDirectoryManager.createBySurvey(fromBankId,surveyName,tag);
		surveyId=directory.getId();
		attrs.addAttribute("surveyId",surveyId);
		return "redirect:/design/my-survey-design/survey.do";
	}


	@RequestMapping("/save.do")
	public String save(String surveyName,RedirectAttributes attr) throws Exception {
		SurveyDirectory survey = new SurveyDirectory();
		try{
			survey.setDirType(2);
			if(surveyName==null || "".equals(surveyName.trim())){
				surveyName="请输入问卷标题";
			}else{
				surveyName=URLDecoder.decode(surveyName,"utf-8");
			}
			survey.setSurveyName(surveyName);
			surveyDirectoryManager.save(survey);
//			attr.addAttribute("surveyId",survey.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/design/my-survey-design/survey.do?surveyId="+survey.getId();
	}
}
