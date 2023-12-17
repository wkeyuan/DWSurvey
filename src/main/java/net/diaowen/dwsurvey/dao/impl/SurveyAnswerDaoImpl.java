package net.diaowen.dwsurvey.dao.impl;

import net.diaowen.common.dao.BaseDaoImpl;
import net.diaowen.common.utils.RandomUtils;
import net.diaowen.dwsurvey.dao.SurveyAnswerDao;
import net.diaowen.dwsurvey.entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.Map;

/**
 * 问卷回答 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class SurveyAnswerDaoImpl extends BaseDaoImpl<SurveyAnswer, String> implements SurveyAnswerDao {

	/**
	 * 保存回答
	 * @param surveyAnswer
	 * @param quMaps
	 */
	@Override
	public void saveAnswer(SurveyAnswer surveyAnswer,
			Map<String, Map<String, Object>> quMaps) {

		Session session=this.getSession();
		//保存答案信息
		String surveyId=surveyAnswer.getSurveyId();
		SurveyDirectory survey=session.get(SurveyDirectory.class, surveyId);
		Integer answerNum = survey.getAnswerNum();
		if(answerNum==null){
			answerNum=0;
		}
		survey.setAnswerNum(answerNum+1);
		session.update(survey);//更新回答数
		int surveyQuAnItemNum=survey.getAnItemLeastNum();//可以回答的最少项目数

		surveyAnswer.setEndAnDate(new Date());

		Date endAnDate = surveyAnswer.getEndAnDate();
		Date bgAnDate = surveyAnswer.getBgAnDate();
		surveyAnswer.setTotalTime(0f);
		if(endAnDate!=null && bgAnDate!=null){
			//计算答卷用时
			long time=endAnDate.getTime()-bgAnDate.getTime();
			surveyAnswer.setTotalTime(Float.parseFloat(time/1000+""));
		}
		session.save(surveyAnswer);

		int anCount=0;
		//保存答案
			//是非题
		Map<String,Object> yesnoMaps=quMaps.get("yesnoMaps");
		anCount+=saveAnYesnoMaps(surveyAnswer, yesnoMaps,session);
			//单选题
		Map<String,Object> radioMaps=quMaps.get("radioMaps");
		anCount+=saveCompAnRadioMaps(surveyAnswer, radioMaps,session);
			//多选题
		Map<String,Object> checkboxMaps=quMaps.get("checkboxMaps");
		anCount+=saveCompAnCheckboxMaps(surveyAnswer,checkboxMaps,session);
			//填空题
		Map<String,Object> fillblankMaps=quMaps.get("fillblankMaps");
		anCount+=saveAnFillMaps(surveyAnswer, fillblankMaps,session);
			//多项填空题
		Map<String,Object> multifillblankMaps=quMaps.get("multifillblankMaps");
		anCount+=saveAnMultiFillMaps(surveyAnswer, multifillblankMaps,session);
			//问答题
		Map<String,Object> answerMaps=quMaps.get("answerMaps");
		anCount+=saveAnAnswerMaps(surveyAnswer, answerMaps,session);
			//复合单选题
		Map<String,Object> compRadioMaps=quMaps.get("compRadioMaps");
		anCount+=saveCompAnRadioMaps(surveyAnswer, compRadioMaps,session);
			//复合多选题
		Map<String,Object> compCheckboxMaps=quMaps.get("compCheckboxMaps");
		anCount+=saveCompAnCheckboxMaps(surveyAnswer, compCheckboxMaps,session);
			//枚举题
		Map<String, Object> enumMaps=quMaps.get("enumMaps");
		anCount+=saveEnumMaps(surveyAnswer, enumMaps, session);
		//评分题
		Map<String,Object> scoreMaps=quMaps.get("scoreMaps");
		anCount+=saveScoreMaps(surveyAnswer,scoreMaps,session);

		//排序题 quOrderMaps
		Map<String,Object> quOrderMaps=quMaps.get("quOrderMaps");
		anCount+=saveQuOrderMaps(surveyAnswer,quOrderMaps,session);

		Map<String,Object> uploadFileMaps=quMaps.get("uploadFileMaps");
		anCount+=saveUploadFileMaps(surveyAnswer,uploadFileMaps,session);

		//保存anCount
		surveyAnswer.setCompleteItemNum(anCount);
		int isComplete=0;
		if(anCount>=surveyQuAnItemNum){
			isComplete=1;//表示回完
		}
		surveyAnswer.setIsComplete(isComplete);
		int isEffective=0;
		if(anCount>0){
			isEffective=1;//暂时只要答过一题就表示回答有效
		}
		surveyAnswer.setIsEffective(isEffective);
		session.save(surveyAnswer);

	}

	/**
	 * 保存评分题答案
	 * @param surveyAnswer
	 * @param quOrderMaps
	 * @param session
	 * @return
	 */
	private int saveQuOrderMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> quOrderMaps, Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();

		int answerQuCount=0;
		if(quOrderMaps!=null){
			for (String quId : quOrderMaps.keySet()) {
				boolean isAn = false;
				Map<String,Object> mapRows=(Map<String, Object>) quOrderMaps.get(quId);
				for (String rowId : mapRows.keySet()) {
					String orderNumValue=mapRows.get(rowId).toString();
					if(orderNumValue!=null && !orderNumValue.isEmpty()){
						session.save(new AnOrder(surveyId, surveyAnswerId, quId, rowId, orderNumValue));
						isAn = true;
					}
				}
				if (isAn) answerQuCount++;
			}
		}
		return answerQuCount;
	}

	/**
	 * 保存评分题
	 * @param surveyAnswer
	 * @param scoreMaps
	 * @param session
	 */
	private int saveScoreMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> scoreMaps, Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();

		int answerQuCount=0;
		if(scoreMaps!=null){
			for (String key : scoreMaps.keySet()) {
				String quId=key;
				Map<String,Object> mapRows=(Map<String, Object>) scoreMaps.get(key);
				boolean isAn = false;
				for (String keyRow : mapRows.keySet()) {
					String rowId=keyRow;
					String scoreValue=mapRows.get(keyRow).toString();
					if(scoreValue!=null && !"".equals(scoreValue)){
						AnScore anScore=new AnScore(surveyId,surveyAnswerId,quId,rowId,scoreValue);
						session.save(anScore);
						isAn = true;
					}
				}
				if(isAn) answerQuCount++;
			}
		}
		return answerQuCount;
	}

	/**
	 * 保存枚举题
	 * @param surveyAnswer
	 * @param enumMaps
	 * @param session
	 */
	private int saveEnumMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> enumMaps, Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();

		int answerQuCount=0;
		for (String key : enumMaps.keySet()) {
			answerQuCount++;
			String[] splitKey=key.split("_");
			String quId=splitKey[0];
			Integer quItemNum=Integer.parseInt(splitKey[1]);
			String answerValue=enumMaps.get(key).toString();
			AnEnumqu anAnswer=new AnEnumqu(surveyId,surveyAnswerId,quId,quItemNum,answerValue);
			session.save(anAnswer);
		}
		return answerQuCount;
	}


	/**
	 * 保存判断题答案
	 * @param anAnswerMaps
	 * @param session
	 */
	private int saveAnAnswerMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> anAnswerMaps,Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();

		int answerQuCount=0;
		for (String key : anAnswerMaps.keySet()) {
			String quId=key;
			String answerValue=anAnswerMaps.get(key).toString();
			if(answerValue!=null && !"".equals(answerValue)){
				AnAnswer anAnswer=new AnAnswer(surveyId,surveyAnswerId,quId,answerValue);
				session.save(anAnswer);
				answerQuCount++;
			}
		}
		return answerQuCount;
	}

	/**
	 * 保存单项填空题答案
	 * @param fillMaps
	 * @param session
	 */
	private int saveAnFillMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> fillMaps,Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		int answerQuCount=0;
		for (String key : fillMaps.keySet()) {
			String quId=key;
			String answerValue=fillMaps.get(key).toString();
			if(answerValue!=null && !"".equals(answerValue)){
				AnFillblank anFillblank=new AnFillblank(surveyId,surveyAnswerId,quId,answerValue);
				session.save(anFillblank);
				answerQuCount++;
			}
		}
		return answerQuCount;
	}

	/**
	 * 保存多选题答案
	 * @param checkboxMaps
	 * @param session
	 */
	private int saveAnCheckboxMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> checkboxMaps,Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();

		int answerQuCount=0;
		if (checkboxMaps!=null) {
			for (String key : checkboxMaps.keySet()) {
				String quId = key;
				Map<String, Object> map = (Map<String, Object>) checkboxMaps.get(key);
				for (String keyMap : map.keySet()) {
					answerQuCount++;
					String quItemId = map.get(keyMap).toString();
					AnCheckbox anCheckbox = new AnCheckbox(surveyId, surveyAnswerId, quId, quItemId);
					session.save(anCheckbox);
				}
			}
		}
		return answerQuCount;
	}
	/**
	 * 保存复合多选题答案
	 * @param session
	 */
	private int saveCompAnCheckboxMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> compCheckboxMaps, Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		int answerQuCount=0;
		if(compCheckboxMaps!=null){
			for (String key : compCheckboxMaps.keySet()) {
				String quId=key;
				boolean isAn = false;
				Map<String, Object> map=(Map<String, Object>) compCheckboxMaps.get(key);
				for (String keyMap : map.keySet()) {
					AnCheckbox tempAnCheckbox=(AnCheckbox) map.get(keyMap);
					String quItemId=tempAnCheckbox.getQuItemId();
					String otherText=tempAnCheckbox.getOtherText();
					if(quItemId!=null){
						AnCheckbox anCheckbox=new AnCheckbox(surveyId,surveyAnswerId,quId,quItemId);
						anCheckbox.setOtherText(otherText);
						session.save(anCheckbox);
						isAn = true;
					}
				}
				if(isAn) answerQuCount++;
			}
		}
		return answerQuCount;
	}

	/**
	 * 保存多项填空题答案
	 * @param dfillMaps
	 * @param session
	 */
	private int saveAnMultiFillMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> dfillMaps,Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();

		int answerQuCount=0;
		for (String key : dfillMaps.keySet()) {
			String quId=key;
			boolean isAn = false;
			Map<String, Object> map=(Map<String, Object>) dfillMaps.get(key);
			if(map!=null && !map.isEmpty()){
				for (String keyMap : map.keySet()) {
					String quItemId=keyMap;
					String answerValue=map.get(keyMap).toString();
					if(answerValue!=null && !"".equals(answerValue)){
						AnDFillblank anDFillblank=new AnDFillblank(surveyId,surveyAnswerId,quId,quItemId,answerValue);
						session.save(anDFillblank);
						isAn = true;
					}
				}
			}
			if(isAn) answerQuCount++;
		}
		return answerQuCount;
	}

	/**
	 * 保存单选题答案
	 * @param radioMaps
	 * @param session
	 */
	private int saveAnRadioMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> radioMaps,Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		int answerQuCount=0;
		if (radioMaps!=null) {
			for (String key : radioMaps.keySet()) {
				answerQuCount++;
				String quId = key;
				String quItemId = radioMaps.get(key).toString();
				AnRadio anRadio = new AnRadio(surveyId, surveyAnswerId, quId, quItemId);
				session.save(anRadio);
			}
		}
		return answerQuCount;
	}

	/**
	 * 复合单选题
	 * @param surveyAnswer
	 * @param compRadioMaps
	 * @param session
	 */
	private int saveCompAnRadioMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> compRadioMaps, Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		int answerQuCount=0;
		if(compRadioMaps!=null){
			for (String key : compRadioMaps.keySet()) {
				answerQuCount++;
				String quId=key;
				AnRadio tempAnRadio=(AnRadio) compRadioMaps.get(key);
				String quItemId=tempAnRadio.getQuItemId();
				String othertext=tempAnRadio.getOtherText();
				AnRadio anRadio=new AnRadio(surveyId,surveyAnswerId,quId,quItemId);
				anRadio.setOtherText(othertext);
				session.save(anRadio);
			}
		}
		return answerQuCount;
	}

	/**
	 * 保存是非题答案
	 * @param yesnoMaps
	 * @param session
	 */
	public int saveAnYesnoMaps(SurveyAnswer surveyAnswer,Map<String,Object> yesnoMaps,Session session){
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		int answerQuCount=0;
		for (String key : yesnoMaps.keySet()) {
			answerQuCount++;
			String quId=key;
			String yesnoAnswer=yesnoMaps.get(key).toString();
			AnYesno anYesno=new AnYesno(surveyId,surveyAnswerId,quId,yesnoAnswer);
			session.save(anYesno);
		}
		return answerQuCount;
	}

	/**
	 * 保存填空题答案
	 * @param surveyAnswer
	 * @param fillMaps
	 * @param session
	 * @return
	 */
	private int saveUploadFileMaps(SurveyAnswer surveyAnswer,
								   Map<String, Object> fillMaps,Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		int answerQuCount=0;
		for (String key : fillMaps.keySet()) {
			answerQuCount++;
			String quId=key.split("_")[0];
			String answerValue=fillMaps.get(key).toString();
			String[] answerValues = answerValue.split("___");
			String randomCode = RandomUtils.randomWordNum(6);
			AnUplodFile anUplodFile=new AnUplodFile(surveyId,surveyAnswerId,quId,answerValues[0],answerValues[1],randomCode);
			session.save(anUplodFile);
		}
		return answerQuCount;
	}

	/**
	 * 获取单个问卷的全局统计信息
	 * @param surveyStats
	 * @return
	 */
	public SurveyStats surveyStatsData(SurveyStats surveyStats){
		try{

			String sqlBuf = "select MIN(bg_an_date) firstDate,MAX(bg_an_date) lastDate,count(id) anCount,min(total_time) minTime,avg(total_time) avgTime, " + "count(case when is_complete =1 then is_complete end) complete1, " +
					"count(case when is_effective =1 then is_effective end) effective1, " +
					"count(case when handle_state =0 then handle_state end) handle0, " +
					"count(case when handle_state =1 then handle_state end) handle1, " +
					"count(case when handle_state =2 then handle_state end) handle2, " +
					"count(case when data_source =0 then data_source end) datasource0, " +
					"count(case when data_source =1 then data_source end) datasource1, " +
					"count(case when data_source =2 then data_source end) datasource2, " +
					"count(case when data_source =3 then data_source end) datasource3 " +
					"from t_survey_answer where survey_id=? ";
			Object[] objects = (Object[]) this.getSession().createSQLQuery(sqlBuf).setParameter(1, surveyStats.getSurveyId()).uniqueResult();

			surveyStats.setFirstAnswer((Date)objects[0]);
			surveyStats.setLastAnswer((Date)objects[1]);
			surveyStats.setAnswerNum(Integer.parseInt(objects[2].toString()));
			String minTime=objects[3].toString();

			int minIndex=minTime.indexOf(".");
			if(minIndex>0){
				minTime=minTime.substring(0,minIndex);
			}
			surveyStats.setAnMinTime(Integer.parseInt(minTime));//Min Time

			String avgTime=objects[4].toString();
			int avgIndex=avgTime.indexOf(".");
			if(avgIndex>0){
				avgTime=avgTime.substring(0,avgIndex);
			}
			surveyStats.setAnAvgTime(Integer.parseInt(avgTime));//Avg Time

			surveyStats.setCompleteNum(Integer.parseInt(objects[5].toString()));
			surveyStats.setEffectiveNum(Integer.parseInt(objects[6].toString()));
			surveyStats.setUnHandleNum(Integer.parseInt(objects[7].toString()));
			surveyStats.setHandlePassNum(Integer.parseInt(objects[8].toString()));
			surveyStats.setHandleUnPassNum(Integer.parseInt(objects[9].toString()));

			surveyStats.setOnlineNum(Integer.parseInt(objects[10].toString()));
			surveyStats.setInputNum(Integer.parseInt(objects[11].toString()));
			surveyStats.setMobileNum(Integer.parseInt(objects[12].toString()));
			surveyStats.setImportNum(Integer.parseInt(objects[13].toString()));
		}catch (Exception e) {
			e.printStackTrace();
		}
//		0网调  1录入数据 2移动数据 3导入数据
		return surveyStats;
	}

	/**
	 * count 结果数量
	 * @param surveyId
	 * @return
	 */
	@Override
	public Long countResult(String surveyId) {
		Criterion cri2= Restrictions.lt("handleState", 3);
		Criterion cri3=Restrictions.eq("isEffective", 1);
		Criteria c = null;
		if(surveyId!=null){
			Criterion cri1 = Restrictions.eq("surveyId",surveyId);
			c = createCriteria(cri1,cri2,cri3);
		}else{
			c = createCriteria(cri2,cri3);
		}
		return countCriteriaResult(c);
	}

}
