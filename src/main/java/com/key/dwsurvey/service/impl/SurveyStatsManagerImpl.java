package com.key.dwsurvey.service.impl;

import java.util.List;
import java.util.regex.Pattern;

import com.key.common.QuType;
import com.key.dwsurvey.dao.SurveyStatsDao;
import com.key.dwsurvey.entity.DataCross;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.SurveyStats;
import com.key.dwsurvey.service.AnScoreManager;
import net.sf.json.JSONArray;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.service.AnAnswerManager;
import com.key.dwsurvey.service.AnCheckboxManager;
import com.key.dwsurvey.service.AnChenCheckboxManager;
import com.key.dwsurvey.service.AnChenFbkManager;
import com.key.dwsurvey.service.AnChenRadioManager;
import com.key.dwsurvey.service.AnChenScoreManager;
import com.key.dwsurvey.service.AnCompChenRadioManager;
import com.key.dwsurvey.service.AnDFillblankManager;
import com.key.dwsurvey.service.AnEnumquManager;
import com.key.dwsurvey.service.AnFillblankManager;
import com.key.dwsurvey.service.AnOrderManager;
import com.key.dwsurvey.service.AnRadioManager;
import com.key.dwsurvey.service.AnYesnoManager;
import com.key.dwsurvey.service.QuestionManager;
import com.key.dwsurvey.service.SurveyAnswerManager;
import com.key.dwsurvey.service.SurveyStatsManager;


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
	@Autowired
	private AnYesnoManager anYesnoManager;
	@Autowired
	private AnRadioManager anRadioManager;
	@Autowired
	private AnFillblankManager anFillblankManager;
	@Autowired
	private AnEnumquManager anEnumquManager;
	@Autowired
	private AnDFillblankManager anDFillblankManager;
	@Autowired
	private AnCheckboxManager anCheckboxManager;
	@Autowired
	private AnAnswerManager anAnswerManager;
	@Autowired
	private AnChenRadioManager anChenRadioManager;
	@Autowired
	private AnChenCheckboxManager anChenCheckboxManager;
	@Autowired
	private AnChenScoreManager anChenScoreManager;
	@Autowired
	private AnChenFbkManager anChenFbkManager;
	@Autowired
	private AnCompChenRadioManager anCompChenRadioManager;
	@Autowired
	private AnScoreManager anScoreManager;
	@Autowired
	private AnOrderManager anOrderManager;
	
	@Override
	public void setBaseDao() {
		this.baseDao = surveyStatsDao;
	}

	@Override
	public SurveyStats findBySurvey(String surveyId) {
		// return surveyStatsDao.findUniqueBy("surveyId", surveyId);
		Criterion criterion = Restrictions.eq("surveyId", surveyId);
		return surveyStatsDao.findFirst(criterion);
	}

	@Override
	public SurveyStats findBySurvey(SurveyDirectory surveyDirectory) {
		if (surveyDirectory != null && surveyDirectory.getId() != null) {
			return findBySurvey(surveyDirectory.getId());
		}
		return null;
	}

	@Override
	public SurveyStats findNewStatsData(SurveyDirectory surveyDirectory) {
		SurveyStats surveyStats = null;
		if (surveyDirectory != null && surveyDirectory.getId() != null) {
			String surveyId = surveyDirectory.getId();
			surveyStats = findBySurvey(surveyId);
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
			} else if (QuType.CHENRADIO == quType) {// 矩阵单选题
				anChenRadioManager.findGroupStats(question);
			} else if (QuType.CHENFBK == quType) {// 矩阵填空题
				anChenFbkManager.findGroupStats(question);
			} else if (QuType.CHENCHECKBOX == quType) {// 矩阵多选题
				anChenCheckboxManager.findGroupStats(question);
			} else if (QuType.COMPCHENRADIO == quType) {// 复合矩陈单选题

			} else if (QuType.CHENSCORE == quType) {// 矩陈评分题
				anChenScoreManager.findGroupStats(question);
			} else if (QuType.ORDERQU == quType) {
				anOrderManager.findGroupStats(question);
			}
		}

		return questions;
	}

	@Override
	public List<Question> findRowVarQus(SurveyDirectory survey) {
		return questionManager.findStatsRowVarQus(survey);
	}

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
		// surveyStatsDao.findStatsDataCross(rowQuestion,colQuestion);
		return null;
	}

	

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
			} else if(QuType.CHENRADIO == quType){
				return anChenRadioManager.findStatsDataChart(question);
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
			} else if (QuType.CHENRADIO == quType) {// 矩阵单选题
				anChenRadioManager.findGroupStats(question);
			} else if (QuType.CHENFBK == quType) {// 矩阵填空题
				anChenFbkManager.findGroupStats(question);
			} else if (QuType.CHENCHECKBOX == quType) {// 矩阵多选题
				anChenCheckboxManager.findGroupStats(question);
			} else if (QuType.COMPCHENRADIO == quType) {// 复合矩陈单选题

			} else if (QuType.CHENSCORE == quType) {// 矩陈评分题
				anChenScoreManager.findGroupStats(question);
			}
		}

		return questions;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Question> dataChart1s(SurveyDirectory survey) {
		List<Question> questions = questionManager.findDetails(survey.getId(),
				"2");
		for (Question question : questions) {
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
		return questions;
	}
	

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
	
	public String removeTagFromText(String htmlStr) {
		if (htmlStr == null || "".equals(htmlStr))
			return "";
		String textStr = "";
		java.util.regex.Pattern pattern;
		java.util.regex.Matcher matcher;

		try {
			String regEx_remark = "<!--.+?-->";
			// 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; 
			
			// 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
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
