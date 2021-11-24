package net.diaowen.dwsurvey.service;

import java.util.List;
import java.util.Map;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyAnswer;
import net.diaowen.dwsurvey.entity.SurveyDetail;
import net.diaowen.dwsurvey.entity.SurveyStats;

import javax.servlet.http.HttpServletRequest;

/**
 * 问卷回答
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface SurveyAnswerManager extends BaseService<SurveyAnswer, String>{

	public void saveAnswer(SurveyAnswer surveyAnswer, Map<String, Map<String, Object>> quMaps);

	public List<Question> findAnswerDetail(SurveyAnswer answer);
	
	public List<SurveyAnswer> answersByIp(String surveyId, String ip);
	
	public SurveyAnswer getTimeInByIp(SurveyDetail surveyDetail, String ip);
	
	public Long getCountByIp(String surveyId, String ip);

	public String exportXLS(String surveyId, String savePath);
	
	public SurveyStats surveyStatsData(SurveyStats surveyStats);
	
	public Page<SurveyAnswer> joinSurvey(Page<SurveyAnswer> page, User user) ;
	
	/**
	 * 取出某份问卷的答卷数据
	 * @param page
	 * @param surveyId
	 * @return
	 */
	public Page<SurveyAnswer> answerPage(Page<SurveyAnswer> page, String surveyId);

}
