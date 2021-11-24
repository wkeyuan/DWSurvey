package net.diaowen.dwsurvey.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.SurveyStats;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.SurveyStatsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分析报告 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Controller
@RequestMapping("/da/survey-report")
public class SurveyReportController{
	
	protected final static String DEFAULT_REPORT="default_report";
	protected final static String LINE_CHART="line_chart";
	protected final static String PIE_CHART="pie_chart";
	
	@Autowired
	private SurveyStatsManager surveyStatsManager;
	@Autowired
	private SurveyDirectoryManager directoryManager;
	@Autowired
	private AccountManager accountManager;


	@RequestMapping("/defaultReport.do")
	public ModelAndView defaultReport(String surveyId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// 得到频数分析数据
		User user = accountManager.getCurUser();
		if(user!=null){
			SurveyDirectory directory=directoryManager.getSurveyByUser(surveyId, user.getId());
			if(directory!=null){
				List<Question> questions = surveyStatsManager.findFrequency(directory);
				SurveyStats surveyStats = new SurveyStats();
				surveyStats.setQuestions(questions);
				modelAndView.addObject("surveyStats",surveyStats);
			}
			modelAndView.addObject("directory",directory);
		}
		modelAndView.addObject("surveyId",surveyId);
		modelAndView.setViewName("/diaowen-da/default-report");
		return modelAndView;
	}
	
	public String lineChart(String surveyId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		User user = accountManager.getCurUser();
		if(user!=null){
			SurveyDirectory directory=directoryManager.getSurveyByUser(surveyId, user.getId());
			if(directory!=null){
				List<Question> questions = surveyStatsManager.dataChart1s(directory);
				SurveyStats surveyStats = new SurveyStats();
				surveyStats.setQuestions(questions);
				modelAndView.addObject("surveyStats",surveyStats);
			}
			modelAndView.addObject("directory",directory);
		}
		modelAndView.setViewName("/diaowen-da/line-chart");
		return LINE_CHART;
	}
	
	public String pieChart(String surveyId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		User user = accountManager.getCurUser();
		if(user!=null){
			SurveyDirectory directory=directoryManager.getSurveyByUser(surveyId, user.getId());
			if(directory!=null){
				List<Question> questions = surveyStatsManager.dataChart1s(directory);
				SurveyStats surveyStats = new SurveyStats();
				surveyStats.setQuestions(questions);
				modelAndView.addObject("surveyStats",surveyStats);
			}
			modelAndView.addObject("directory",directory);
		}
		modelAndView.setViewName("/diaowen-da/pie-chart");
		return PIE_CHART;
	}
	
	//取得某一题的统计数据
	@RequestMapping("/chartData.do")
	public String chartData(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//取柱状图数据
		User user = accountManager.getCurUser();
		if(user!=null){
			String questionId=request.getParameter("quId");
			Question question=new Question();
			question.setId(questionId);
			surveyStatsManager.questionDateCross(question);
			response.getWriter().write(question.getStatJson());
		}
		return null;
	}

	
}
