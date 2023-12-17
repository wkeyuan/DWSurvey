package net.diaowen.dwsurvey.service;

import java.util.List;

import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyStats;
import net.diaowen.dwsurvey.entity.DataCross;
import net.diaowen.dwsurvey.entity.SurveyDirectory;

/**
 * 统计报表
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface SurveyStatsManager extends BaseService<SurveyStats, String>{
	/**
	 * 根据 surveyid 获取全局统计信息
	 * @param surveyId
	 * @return
	 */
	public SurveyStats findBySurvey(String surveyId);
	/**
	 * 根据 问卷 获取全局统计信息
	 * @param surveyDirectory
	 * @return
	 */
	public SurveyStats findBySurvey(SurveyDirectory surveyDirectory);
	/**
	 * 获取最新全局统计信息
	 * @param survey
	 * @return
	 */
	public SurveyStats findNewStatsData(SurveyDirectory survey);
	/**
	 * 单个题目频数统计分析
	 */
	public List<Question> findFrequency(SurveyDirectory survey);
	/**
	 * 访问行状态参数
	 * @param survey
	 * @return
	 */
	public List<Question> findRowVarQus(SurveyDirectory survey);
	/**
	 * 访问列状态参数
	 * @param survey
	 * @return
	 */
	public List<Question> findColVarQus(SurveyDirectory survey);
	/**
	 * 均值交叉统计
	 */
	public List<DataCross> findStatsDataCross(String rowQuId, String colQuId);
	/**
	 * 访问结果保存表
	 * @param quId
	 * @return
	 */
	public List<DataCross> findDataChart(String quId);
	/**
	 * 将问卷所有结果保存表状态参数json保存到问题
	 * @param survey
	 * @return
	 */
	public List<Question> dataChart1s(SurveyDirectory survey);
	/**
	 * 将结果保存表状态参数json保存到问题
	 * @param question
	 */
	public void questionDateCross(Question question) ;
}
