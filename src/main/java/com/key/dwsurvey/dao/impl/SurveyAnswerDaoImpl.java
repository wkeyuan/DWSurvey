package com.key.dwsurvey.dao.impl;

import java.util.Date;
import java.util.Map;

import com.key.dwsurvey.dao.SurveyAnswerDao;
import com.key.dwsurvey.service.SurveyStatsManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.key.common.dao.BaseDaoImpl;
import com.key.dwsurvey.entity.AnAnswer;
import com.key.dwsurvey.entity.AnCheckbox;
import com.key.dwsurvey.entity.AnChenCheckbox;
import com.key.dwsurvey.entity.AnChenFbk;
import com.key.dwsurvey.entity.AnChenRadio;
import com.key.dwsurvey.entity.AnChenScore;
import com.key.dwsurvey.entity.AnCompChenRadio;
import com.key.dwsurvey.entity.AnDFillblank;
import com.key.dwsurvey.entity.AnEnumqu;
import com.key.dwsurvey.entity.AnFillblank;
import com.key.dwsurvey.entity.AnOrder;
import com.key.dwsurvey.entity.AnRadio;
import com.key.dwsurvey.entity.AnScore;
import com.key.dwsurvey.entity.AnYesno;
import com.key.dwsurvey.entity.SurveyAnswer;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.SurveyStats;

/**
 * 问卷回答 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class SurveyAnswerDaoImpl extends BaseDaoImpl<SurveyAnswer, String> implements SurveyAnswerDao {

	@Autowired
	private SurveyStatsManager surveyStatsManager;
	
	@Override
	public void saveAnswer(SurveyAnswer surveyAnswer,
			Map<String, Map<String, Object>> quMaps) {
		Date curDate=new Date();
		
		Session session=this.getSession();
		//保存答案信息
		String surveyId=surveyAnswer.getSurveyId();
//		Survey survey=(Survey) session.get(Survey.class, surveyId);
		SurveyDirectory survey=(SurveyDirectory) session.get(SurveyDirectory.class, surveyId);
//		System.out.println("survey:"+survey);
		Integer answerNum = survey.getAnswerNum();
		if(answerNum==null){
			answerNum=0;
		}
		survey.setAnswerNum(answerNum+1);
		session.update(survey);//更新回答数
		int surveyQuAnItemNum=survey.getAnItemLeastNum();//可以回答的最少项目数
		
		surveyAnswer.setBgAnDate(curDate);
		surveyAnswer.setEndAnDate(new Date());
		
		//计算答卷用时
		long time=surveyAnswer.getEndAnDate().getTime()-surveyAnswer.getBgAnDate().getTime();
		surveyAnswer.setTotalTime(Float.parseFloat(time/(60*60)+""));
		session.save(surveyAnswer);
		
		int anCount=0;
		//保存答案
			//是非题
		Map<String,Object> yesnoMaps=quMaps.get("yesnoMaps");
		anCount+=saveAnYesnoMaps(surveyAnswer, yesnoMaps,session);
			//单选题
		Map<String,Object> radioMaps=quMaps.get("radioMaps");
		anCount+=saveAnRadioMaps(surveyAnswer, radioMaps,session);
			//多选题
		Map<String,Object> checkboxMaps=quMaps.get("checkboxMaps");
		anCount+=saveAnCheckboxMaps(surveyAnswer,checkboxMaps,session);
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
		
		//矩阵单选题
		Map<String,Object> chehRadioMaps=quMaps.get("chenRadioMaps");
		anCount+=saveChenRadioMaps(surveyAnswer,chehRadioMaps,session);
		//矩阵多选题
		Map<String,Object> chehCheckboxMaps=quMaps.get("chenCheckboxMaps");
		anCount+=saveChenCheckboxMaps(surveyAnswer,chehCheckboxMaps,session);
		//矩阵填空题
		Map<String,Object> chenFbkMaps=quMaps.get("chenFbkMaps");
		anCount+=saveChenFbkMaps(surveyAnswer,chenFbkMaps,session);
		//复合矩阵单选题
		Map<String,Object> compChehRadioMaps=quMaps.get("compChenRadioMaps");
		anCount+=saveCompChehRadioMaps(surveyAnswer,compChehRadioMaps,session);
		
		//矩阵填空题
		Map<String,Object> chenScoreMaps=quMaps.get("chenScoreMaps");
		anCount+=saveChenScoreMaps(surveyAnswer,chenScoreMaps,session);
		
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
		
		//更新统计状态
		SurveyStats surveyStats=surveyStatsManager.findBySurvey(surveyId);
		if(surveyStats!=null){
			int isNewData = surveyStats.getIsNewData();
			if(isNewData==1){
				surveyStats.setIsNewData(0);
				surveyStatsManager.save(surveyStats);
			}
		}else{
			surveyStats=new SurveyStats();
			surveyStats.setSurveyId(surveyId);
			surveyStatsManager.save(surveyStats);
		}
	
	}

	/**
	 * 矩陈评分题
	 * @param surveyAnswer
	 * @param chenScoreMaps
	 * @param session
	 * @return
	 */
	private int saveChenScoreMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> chenScoreMaps, Session session) {
		
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		int answerQuCount=0;
		if(chenScoreMaps!=null){
			for (String key : chenScoreMaps.keySet()) {
				String quId=key;
				Map<String,Object> mapRows=(Map<String, Object>) chenScoreMaps.get(key);
				for (String keyRow : mapRows.keySet()) {
					String rowId=keyRow;
					Map<String, Object> mapRow=(Map<String, Object>) mapRows.get(keyRow);
					for (String  keyCol : mapRow.keySet()) {
						answerQuCount++;
						String colId=keyCol;
						String answerValue=mapRow.get(keyCol).toString();
						AnChenScore anChenScore=new AnChenScore(surveyId,surveyAnswerId,quId,rowId,colId,answerValue);
						session.save(anChenScore);
					}
				}
			}
		}
		return answerQuCount;
	}

	private int saveQuOrderMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> quOrderMaps, Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		
		int answerQuCount=0;
		if(quOrderMaps!=null){
			for (String key : quOrderMaps.keySet()) {
				String quId=key;
				Map<String,Object> mapRows=(Map<String, Object>) quOrderMaps.get(key);
				for (String keyRow : mapRows.keySet()) {
					answerQuCount++;
					String rowId=keyRow;
					String orderNumValue=mapRows.get(keyRow).toString();
					AnOrder anScore=new AnOrder(surveyId,surveyAnswerId,quId,rowId,orderNumValue);
					session.save(anScore);
				}
			}
		}
		return answerQuCount;
	}

	private int saveCompChehRadioMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> compChenRadioMaps, Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		
		int answerQuCount=0;
		if(compChenRadioMaps!=null){
			for (String key : compChenRadioMaps.keySet()) {
				String quId=key;
				Map<String,Object> mapRows=(Map<String, Object>) compChenRadioMaps.get(key);
				for (String keyRow : mapRows.keySet()) {
					String rowId=keyRow;
					Map<String, Object> mapRow=(Map<String, Object>) mapRows.get(keyRow);
					for (String  keyCol : mapRow.keySet()) {
						answerQuCount++;
						String colId=keyCol;
						String optionId=mapRow.get(keyCol).toString();
						AnCompChenRadio anCompChenRadio=new AnCompChenRadio(surveyId,surveyAnswerId,quId,rowId,colId,optionId);
						session.save(anCompChenRadio);
					}
				}
			}
		}
		return answerQuCount;
	}

	private int saveChenFbkMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> chenFbkMaps, Session session) {
		
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		int answerQuCount=0;
		if(chenFbkMaps!=null){
			for (String key : chenFbkMaps.keySet()) {
				String quId=key;
				Map<String,Object> mapRows=(Map<String, Object>) chenFbkMaps.get(key);
				for (String keyRow : mapRows.keySet()) {
					String rowId=keyRow;
					Map<String, Object> mapRow=(Map<String, Object>) mapRows.get(keyRow);
					for (String  keyCol : mapRow.keySet()) {
						answerQuCount++;
						String colId=keyCol;
						String answerValue=mapRow.get(keyCol).toString();
						AnChenFbk anChenFbk=new AnChenFbk(surveyId,surveyAnswerId,quId,rowId,colId,answerValue);
						session.save(anChenFbk);
					}
				}
			}
		}
		return answerQuCount;
	}

	private int saveChenCheckboxMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> chenCheckboxMaps, Session session) {
		
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		
		int answerQuCount=0;
		if(chenCheckboxMaps!=null){
			for (String key : chenCheckboxMaps.keySet()) {
				String quId=key;
				Map<String,Object> mapRows=(Map<String, Object>) chenCheckboxMaps.get(key);
				for (String keyRow : mapRows.keySet()) {
					String rowId=keyRow;
					Map<String, Object> mapRow=(Map<String, Object>) mapRows.get(keyRow);
					for (String  keyCol : mapRow.keySet()) {
						answerQuCount++;
						String colId=keyCol;
						AnChenCheckbox anChenCheckbox=new AnChenCheckbox(surveyId,surveyAnswerId,quId,rowId,colId);
						session.save(anChenCheckbox);
					}
				}
			}
		}
		return answerQuCount;
	}

	/**
	 * 保存矩阵单选题
	 * @param surveyAnswer
	 * @param chehRadioMaps
	 * @param session
	 */
	private int saveChenRadioMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> chenRadioMaps, Session session) {
		
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		
		int answerQuCount=0;
		if(chenRadioMaps!=null){
			for (String key : chenRadioMaps.keySet()) {
				String quId=key;
				Map<String,Object> mapRows=(Map<String, Object>) chenRadioMaps.get(key);
				for (String keyRow : mapRows.keySet()) {
					answerQuCount++;
					String rowId=keyRow;
					String colId=mapRows.get(keyRow).toString();
					AnChenRadio anChenRadio=new AnChenRadio(surveyId,surveyAnswerId,quId,rowId,colId);
					session.save(anChenRadio);
				}
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
				for (String keyRow : mapRows.keySet()) {
					answerQuCount++;
					String rowId=keyRow;
					String scoreValue=mapRows.get(keyRow).toString();
					AnScore anScore=new AnScore(surveyId,surveyAnswerId,quId,rowId,scoreValue);
					session.save(anScore);
				}
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
	 * @param exambatchUser
	 * @param anAnswerMaps
	 * @param session
	 */
	private int saveAnAnswerMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> anAnswerMaps,Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		
		int answerQuCount=0;
		for (String key : anAnswerMaps.keySet()) {
			answerQuCount++;
			String quId=key;
			String answerValue=anAnswerMaps.get(key).toString();
			AnAnswer anAnswer=new AnAnswer(surveyId,surveyAnswerId,quId,answerValue);
			session.save(anAnswer);
		}
		return answerQuCount;
	}

	/**
	 * 保存单项填空题答案
	 * @param exambatchUser
	 * @param fillMaps
	 * @param session
	 */
	private int saveAnFillMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> fillMaps,Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		int answerQuCount=0;
		for (String key : fillMaps.keySet()) {
			answerQuCount++;
			String quId=key;
			String answerValue=fillMaps.get(key).toString();
			AnFillblank anFillblank=new AnFillblank(surveyId,surveyAnswerId,quId,answerValue);
			session.save(anFillblank);
		}
		return answerQuCount;
	}

	/**
	 * 保存多选题答案
	 * @param exambatchUser
	 * @param checkboxMaps
	 * @param session
	 */
	private int saveAnCheckboxMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> checkboxMaps,Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		
		int answerQuCount=0;
		if (checkboxMaps!=null)
		for (String key : checkboxMaps.keySet()) {
			String quId=key;
			Map<String, Object> map=(Map<String, Object>) checkboxMaps.get(key);
			for (String keyMap : map.keySet()) {
				answerQuCount++;
				String quItemId=map.get(keyMap).toString();
				AnCheckbox anCheckbox=new AnCheckbox(surveyId,surveyAnswerId,quId,quItemId);
				session.save(anCheckbox);
			}
		}
		return answerQuCount;
	}
	/**
	 * 保存复合多选题答案
	 * @param exambatchUser
	 * @param checkboxMaps
	 * @param session
	 */
	private int saveCompAnCheckboxMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> compCheckboxMaps, Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		int answerQuCount=0;
		for (String key : compCheckboxMaps.keySet()) {
			String quId=key;
			Map<String, Object> map=(Map<String, Object>) compCheckboxMaps.get(key);
			for (String keyMap : map.keySet()) {
				answerQuCount++;
//				String quItemId=map.get(keyMap).toString();
				AnCheckbox tempAnCheckbox=(AnCheckbox) map.get(keyMap);
				String quItemId=tempAnCheckbox.getQuItemId();
				String otherText=tempAnCheckbox.getOtherText();
				AnCheckbox anCheckbox=new AnCheckbox(surveyId,surveyAnswerId,quId,quItemId);
				anCheckbox.setOtherText(otherText);
				session.save(anCheckbox);
			}
		}
		return answerQuCount;
	}

	/**
	 * 保存多项填空题答案
	 * @param exambatchUser
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
			Map<String, Object> map=(Map<String, Object>) dfillMaps.get(key);
			if(map!=null && map.size()>0){
				for (String keyMap : map.keySet()) {
					answerQuCount++;
					//answerValue+=keyMap+Question.splitTag1+map.get(keyMap)+Question.splitTag;
					String quItemId=keyMap;
					String answerValue=map.get(keyMap).toString();
					AnDFillblank anDFillblank=new AnDFillblank(surveyId,surveyAnswerId,quId,quItemId,answerValue);
					session.save(anDFillblank);
				}
			}
		}
		return answerQuCount;
	}

	/**
	 * 保存单选题答案
	 * @param exambatchUser
	 * @param radioMaps
	 * @param session
	 */
	private int saveAnRadioMaps(SurveyAnswer surveyAnswer,
			Map<String, Object> radioMaps,Session session) {
		String surveyId=surveyAnswer.getSurveyId();
		String surveyAnswerId=surveyAnswer.getId();
		int answerQuCount=0;
		if (radioMaps!=null)
		for (String key : radioMaps.keySet()) {
			answerQuCount++;
			String quId=key;
			String quItemId=radioMaps.get(key).toString();
			AnRadio anRadio=new AnRadio(surveyId,surveyAnswerId,quId,quItemId);
			session.save(anRadio);
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
		for (String key : compRadioMaps.keySet()) {
			answerQuCount++;
			String quId=key;
			//String quItemId=compRadioMaps.get(key).toString();
			AnRadio tempAnRadio=(AnRadio) compRadioMaps.get(key);
			String quItemId=tempAnRadio.getQuItemId();
			String othertext=tempAnRadio.getOtherText();
			AnRadio anRadio=new AnRadio(surveyId,surveyAnswerId,quId,quItemId);
			anRadio.setOtherText(othertext);
			session.save(anRadio);
		}		
		return answerQuCount;
	}
	
	/**
	 * 保存是非题答案
	 * @param exambatchUser
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
	
	public SurveyStats surveyStatsData(SurveyStats surveyStats){
		try{
			StringBuffer sqlBuf=new StringBuffer("select MIN(bg_an_date) firstDate,MAX(bg_an_date) lastDate,count(id) anCount,min(total_time) minTime,avg(total_time) avgTime, ");
			sqlBuf.append("count(case when is_complete =1 then is_complete end) complete1, ");
			sqlBuf.append("count(case when is_effective =1 then is_effective end) effective1, ");
			sqlBuf.append("count(case when handle_state =0 then handle_state end) handle0, ");
			sqlBuf.append("count(case when handle_state =1 then handle_state end) handle1, ");
			sqlBuf.append("count(case when handle_state =2 then handle_state end) handle2, ");
			sqlBuf.append("count(case when data_source =0 then data_source end) datasource0, ");
			sqlBuf.append("count(case when data_source =1 then data_source end) datasource1, ");
			
			sqlBuf.append("count(case when data_source =2 then data_source end) datasource2, ");
			sqlBuf.append("count(case when data_source =3 then data_source end) datasource3 ");
			sqlBuf.append("from t_survey_answer where survey_id=? ");
			Object[] objects = (Object[]) this.getSession().createSQLQuery(sqlBuf.toString()).setString(0, surveyStats.getSurveyId()).uniqueResult();
			
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
}
