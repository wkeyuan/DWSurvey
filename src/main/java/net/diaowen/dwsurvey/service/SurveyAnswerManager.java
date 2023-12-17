package net.diaowen.dwsurvey.service;

import java.util.List;
import java.util.Map;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.*;

/**
 * 问卷回答
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface SurveyAnswerManager extends BaseService<SurveyAnswer, String>{
	/**
	 * 保存答案
	 * @param surveyAnswer
	 * @param quMaps
	 */
	public void saveAnswer(SurveyAnswer surveyAnswer, Map<String, Map<String, Object>> quMaps);
	/**
	 * 获取问卷回答细节
	 * @param answer
	 * @return
	 */
	public List<Question> findAnswerDetail(SurveyAnswer answer);
	/**
	 * 根据ip获取回答？？
	 * @param surveyId
	 * @param ip
	 * @return
	 */
	public List<SurveyAnswer> answersByIp(String surveyId, String ip);
	/**
	 * 根据ip地址。。。。。。
	 * @param surveyDetail
	 * @param ip
	 * @return
	 */
	public SurveyAnswer getTimeInByIp(SurveyDetail surveyDetail, String ip);
	/**
	 * 根据ip获取答案数量
	 * @param surveyId
	 * @param ip
	 * @return
	 */
	public Long getCountByIp(String surveyId, String ip);
	/**
	 * 将问卷结果导出为xls
	 * @param surveyId
	 * @param savePath
	 * @param isExpUpQu
	 * @return
	 */
	public String exportXLS(String surveyId, String savePath, boolean isExpUpQu);
	/**
	 * 获取单个问卷的全局统计信息
	 * @param surveyStats
	 * @return
	 */
	public SurveyStats surveyStatsData(SurveyStats surveyStats);


	/**
	 * 取出某份问卷的答卷数据
	 * @param page
	 * @param surveyId
	 * @return
	 */
	public Page<SurveyAnswer> answerPage(Page<SurveyAnswer> page, String surveyId);
	/**
	 * 删除指定id 的数据
	 * @param ids
	 */
	public void deleteData(String[] ids);
	/**
	 * 判断题目类型，并取值
	 *
	 * @param surveyAnswerId
	 * @param question
	 * @return
	 */
	public int getquestionAnswer(String surveyAnswerId, Question question);
	/**
	 * 根据id更新回答数量
	 * @param surveyId
	 * @return
	 */
	public SurveyDirectory upAnQuNum(String surveyId);
	/**
	 * 根据id更新回答数量
	 * @param surveyDirectory
	 * @return
	 */
	public SurveyDirectory upAnQuNum(SurveyDirectory surveyDirectory);
	/**
	 * 为一组 surveyDirectory 对象更新回答数量
	 * @param result
	 * @return
	 */
	public List<SurveyDirectory> upAnQuNum(List<SurveyDirectory> result);
}
