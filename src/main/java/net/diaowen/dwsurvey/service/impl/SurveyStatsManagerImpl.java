package net.diaowen.dwsurvey.service.impl;

import java.util.List;
import java.util.regex.Pattern;

import net.diaowen.common.QuType;
import net.diaowen.dwsurvey.dao.SurveyStatsDao;
import net.diaowen.dwsurvey.entity.DataCross;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.SurveyStats;
import net.diaowen.dwsurvey.service.*;
import net.sf.json.JSONArray;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.diaowen.common.service.BaseServiceImpl;


/**
 * 问卷统计
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class SurveyStatsManagerImpl extends
		BaseServiceImpl<SurveyStats, String> implements SurveyStatsManager {

	@Autowired
	private SurveyStatsDao surveyStatsDao;
	@Autowired
	private SurveyAnswerManager surveyAnswerManager;
	@Autowired
	private QuestionManager questionManager;
	private final AnYesnoManager anYesnoManager;
	private final AnRadioManager anRadioManager;
	private final AnFillblankManager anFillblankManager;
	private final AnEnumquManager anEnumquManager;
	private final AnDFillblankManager anDFillblankManager;
	private final AnCheckboxManager anCheckboxManager;
	private final AnAnswerManager anAnswerManager;
	private final AnScoreManager anScoreManager;
	private final AnOrderManager anOrderManager;
	private final AnUploadFileManager anUploadFileManager;

	@Autowired
	public SurveyStatsManagerImpl(AnYesnoManager anYesnoManager, AnRadioManager anRadioManager, AnFillblankManager anFillblankManager, AnEnumquManager anEnumquManager, AnDFillblankManager anDFillblankManager, AnCheckboxManager anCheckboxManager, AnAnswerManager anAnswerManager, AnScoreManager anScoreManager, AnOrderManager anOrderManager, AnUploadFileManager anUploadFileManager) {
		this.anYesnoManager = anYesnoManager;
		this.anRadioManager = anRadioManager;
		this.anFillblankManager = anFillblankManager;
		this.anEnumquManager = anEnumquManager;
		this.anDFillblankManager = anDFillblankManager;
		this.anCheckboxManager = anCheckboxManager;
		this.anAnswerManager = anAnswerManager;
		this.anScoreManager = anScoreManager;
		this.anOrderManager = anOrderManager;
		this.anUploadFileManager = anUploadFileManager;
	}



	@Override
	public void setBaseDao() {
		this.baseDao = surveyStatsDao;
	}

	/**
	 * 根据 surveyid 获取全局统计信息
	 * @param surveyId
	 * @return
	 */
	@Override
	public SurveyStats findBySurvey(String surveyId) {
		Criterion criterion = Restrictions.eq("surveyId", surveyId);
		return surveyStatsDao.findFirst(criterion);
	}

	/**
	 * 根据 问卷 获取全局统计信息
	 * @param surveyDirectory
	 * @return
	 */
	@Override
	public SurveyStats findBySurvey(SurveyDirectory surveyDirectory) {
		if (surveyDirectory != null && surveyDirectory.getId() != null) {
			return findBySurvey(surveyDirectory.getId());
		}
		return null;
	}

	/**
	 * 获取最新全局统计信息
	 * @param surveyDirectory
	 * @return
	 */
	@Override
	public SurveyStats findNewStatsData(SurveyDirectory surveyDirectory) {
		SurveyStats surveyStats = null;
		if (surveyDirectory != null && surveyDirectory.getId() != null) {
			surveyStats = findBySurvey(surveyDirectory.getId());
			if (surveyStats != null) {
				int isNew = surveyStats.getIsNewData();
				if (isNew != 1) {
					// 则更新数据
					upStats(surveyStats);
				}
			}
		}
		return surveyStats;
	}

	/**
	 * 更新全局统计信息
	 * @param surveyStats
	 * @return
	 */
	private void upStats(SurveyStats surveyStats) {
		surveyAnswerManager.surveyStatsData(surveyStats);
		// 更新stats
		int anCount = surveyStats.getAnswerNum();
		int effectiveNum = surveyStats.getEffectiveNum();
		int unEffectiveNum = anCount - effectiveNum;
		surveyStats.setUnEffectiveNum(unEffectiveNum);

		int completeNum = surveyStats.getCompleteNum();
		int unCompleteNum = anCount - completeNum;
		surveyStats.setUnCompleteNum(unCompleteNum);

		surveyStats.setIsNewData(1);
		surveyStatsDao.save(surveyStats);
	}

	/**
	 * 单个题目频数统计分析
	 */
	@Override
	public List<Question> findFrequency(SurveyDirectory survey) {
		List<Question> questions = questionManager.findDetails(survey.getId(),
				"2");

		for (Question question : questions) {
			QuType quType = question.getQuType();

			if (QuType.YESNO == quType) {// 是非题
				anYesnoManager.findGroupStats(question);
			} else if (QuType.RADIO == quType || QuType.COMPRADIO == quType) {// 单选
				anRadioManager.findGroupStats(question);
			} else if (QuType.CHECKBOX == quType
					|| QuType.COMPCHECKBOX == quType) {// 多选 复合多选
				anCheckboxManager.findGroupStats(question);
			} else if (QuType.FILLBLANK == quType) {// 填空题
				anFillblankManager.findGroupStats(question);
			} else if (QuType.ANSWER == quType) {// 多行填空题
				anAnswerManager.findGroupStats(question);
			} else if (QuType.MULTIFILLBLANK == quType) {// 组合填空
				anDFillblankManager.findGroupStats(question);
			} else if (QuType.ENUMQU == quType) {// 枚举题
				anEnumquManager.findGroupStats(question);
			} else if (QuType.SCORE == quType) {// 评分题
				anScoreManager.findGroupStats(question);
			} else if (QuType.ORDERQU == quType) {
				anOrderManager.findGroupStats(question);
			} else if (QuType.UPLOADFILE == quType) {
				anUploadFileManager.findGroupStats(question);
			}
		}

		return questions;
	}

	/**
	 * 访问行状态参数
	 * @param survey
	 * @return
	 */
	@Override
	public List<Question> findRowVarQus(SurveyDirectory survey) {
		return questionManager.findStatsRowVarQus(survey);
	}

	/**
	 * 访问列状态参数
	 * @param survey
	 * @return
	 */
	@Override
	public List<Question> findColVarQus(SurveyDirectory survey) {
		return questionManager.findStatsColVarQus(survey);
	}

	/**
	 * 均值交叉统计
	 */
	@Override
	public List<DataCross> findStatsDataCross(String rowQuId, String colQuId) {

		if (rowQuId != null && !"".equals(rowQuId) && colQuId != null
				&& !"".equals(colQuId)) {
			Question rowQuestion = questionManager.getDetail(rowQuId);
			Question colQuestion = questionManager.getDetail(colQuId);

			QuType quType = rowQuestion.getQuType();
			if (QuType.YESNO == quType) {// 是非题
				return anYesnoManager.findStatsDataCross(rowQuestion,
						colQuestion);
			} else if (QuType.RADIO == quType || QuType.COMPRADIO == quType) {// 单选题
				return anRadioManager.findStatsDataCross(rowQuestion,
						colQuestion);
			} else if (QuType.CHECKBOX == quType
					|| QuType.COMPCHECKBOX == quType) {
				return anCheckboxManager.findStatsDataCross(rowQuestion,
						colQuestion);
			}
		}
		return null;
	}


	/**
	 * 保存状态参数
	 * @param t
	 */
	@Override
	public void save(SurveyStats t) {
		String surveyId = t.getSurveyId();
		if (surveyId != null && !"".equals(surveyId)) {
			SurveyStats surveyStats = findBySurvey(surveyId);
			if (surveyStats == null) {
				super.save(t);
			}
		}
	}

	/**
	 * 访问结果保存表
	 * @param quId
	 * @return
	 */
	@Override
	public List<DataCross> findDataChart(String quId) {
		if (quId != null && !"".equals(quId)) {
			Question question = questionManager.getDetail(quId);

			QuType quType = question.getQuType();
			if (QuType.YESNO == quType) {// 是非题
				return anYesnoManager.findStatsDataChart(question);
			} else if (QuType.RADIO == quType || QuType.COMPRADIO == quType) {// 单选题
				return anRadioManager.findStatsDataChart(question);
			} else if (QuType.CHECKBOX == quType || QuType.COMPCHECKBOX == quType) {
				return anCheckboxManager.findStatsDataChart(question);
			} else if (QuType.FILLBLANK == quType){
				anFillblankManager.findGroupStats(question);
			} else if (QuType.MULTIFILLBLANK == quType){
				anDFillblankManager.findGroupStats(question);
			}
		}
		return null;
	}

	/**
	 * 单个题目频数统计分析
	 */
	public List<Question> findFrequency_temp(SurveyDirectory survey) {
		List<Question> questions = questionManager.findDetails(survey.getId(),
				"2");

		for (Question question : questions) {
			QuType quType = question.getQuType();

			if (QuType.YESNO == quType) {// 是非题
				anYesnoManager.findGroupStats(question);
			} else if (QuType.RADIO == quType || QuType.COMPRADIO == quType) {// 单选
																				// 复合单选
				anRadioManager.findGroupStats(question);
			} else if (QuType.CHECKBOX == quType
					|| QuType.COMPCHECKBOX == quType) {// 多选 复合多选
				anCheckboxManager.findGroupStats(question);
			} else if (QuType.FILLBLANK == quType) {// 填空题
				anFillblankManager.findGroupStats(question);
			} else if (QuType.ANSWER == quType) {// 多行填空题
				anAnswerManager.findGroupStats(question);
			} else if (QuType.MULTIFILLBLANK == quType) {// 组合填空
				anDFillblankManager.findGroupStats(question);
			} else if (QuType.ENUMQU == quType) {// 枚举题
				anEnumquManager.findGroupStats(question);
			} else if (QuType.SCORE == quType) {// 评分题
				anScoreManager.findGroupStats(question);
			}
		}

		return questions;
	}

	/**
	 * 将问卷所有结果保存表状态参数json保存到问题
	 * @param survey
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Question> dataChart1s(SurveyDirectory survey) {
		List<Question> questions = questionManager.findDetails(survey.getId(),
				"2");
		for (Question question : questions) {
			questionDateCross(question);
		}
		return questions;
	}

	/**
	 * 将结果保存表状态参数json保存到问题
	 * @param question
	 */
	public void questionDateCross(Question question) {
		List<DataCross> crosses = findDataChart(question.getId());
		if(crosses!=null){
			for (DataCross dataCross : crosses) {
				// 去掉optionName中的html
				String optionName = dataCross.getOptionName();
				if(optionName==null){
						optionName="";
				}
				optionName = removeTagFromText(optionName);
				if(optionName.length()>15){
					optionName=optionName.substring(0, 15)+"...";
				}
				dataCross.setOptionName(optionName);
			}
			String statJson = JSONArray.fromObject(crosses).toString();
			question.setStatJson(statJson);
		}
	}

	/**
	 * 过滤 html 字符串中的标签，返回 raw text
	 * @param htmlStr
	 * @return
	 */
	public String removeTagFromText(String htmlStr) {
		if (htmlStr == null || "".equals(htmlStr))
			return "";
		String textStr = "";
		Pattern pattern;
		java.util.regex.Matcher matcher;

		try {
			String regEx_remark = "<!--.+?-->";
			// 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

			// 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			htmlStr = htmlStr.replaceAll("\n", "");
			htmlStr = htmlStr.replaceAll("\t", "");
			htmlStr = htmlStr.replaceAll("\r", "");

			pattern = Pattern.compile(regEx_remark);// 过滤注释标签
			matcher = pattern.matcher(htmlStr);
			htmlStr = matcher.replaceAll("");

			pattern = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(htmlStr);
			htmlStr = matcher.replaceAll(""); // 过滤script标签

			pattern = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(htmlStr);
			htmlStr = matcher.replaceAll(""); // 过滤style标签

			pattern = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(htmlStr);
			htmlStr = matcher.replaceAll(""); // 过滤html标签

			pattern = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(htmlStr);
			htmlStr = matcher.replaceAll(""); // 过滤html标签

			htmlStr = htmlStr.replaceAll("\n[\\s| ]*\r", "");
			htmlStr = htmlStr.replaceAll("<(.*)>(.*)<\\/(.*)>|<(.*)\\/>", "");

			textStr = htmlStr.trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;// 返回文本字符串
	}
}
