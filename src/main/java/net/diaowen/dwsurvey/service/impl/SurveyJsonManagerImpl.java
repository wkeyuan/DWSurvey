package net.diaowen.dwsurvey.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.common.utils.DwSurveyUtils;
import net.diaowen.dwsurvey.dao.AnAnswerDao;
import net.diaowen.dwsurvey.dao.SurveyJsonDao;
import net.diaowen.dwsurvey.entity.AnAnswer;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.SurveyJson;
import net.diaowen.dwsurvey.service.AnAnswerManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.SurveyJsonManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author keyuan
 * keyuan258@gmail.com
 *
 */

@Service
public class SurveyJsonManagerImpl extends BaseServiceImpl<SurveyJson, String> implements SurveyJsonManager {

	@Autowired
	private SurveyJsonDao surveyJsonDao;
	@Autowired
	private SurveyDirectoryManager surveyManager;

	@Override
	public void setBaseDao() {
		this.baseDao=surveyJsonDao;
	}

	@Override
	public void saveNew(SurveyJson surveyJson) {
		/*String surveyId = surveyJson.getSurveyId();
		SurveyJson surveyJsonDb = findBySurveyId(surveyId);
		if(surveyJsonDb!=null){
			surveyJsonDb.setSurveyJsonText(surveyJson.getSurveyJsonText());
			surveyJsonDb.setSaveDate(new Date());
			super.save(surveyJson);
		}else{
			surveyJson.setSaveDate(new Date());
			super.save(surveyJson);
		}*/
		//如果这样直接保存，则需要考虑库中保存的历史数据可能过多，需要定量清除
		surveyJson.setSaveDate(new Date());
		super.save(surveyJson);
		//每份问卷仅保留最近的1000次操作记录
		//前端每30秒检查下问卷是否有变动，如果有变动则自动保存一次并生成一次历史记录
	}

	public SurveyJson findBySurveyId(String surveyId) {
		Criterion cri1 = Restrictions.eq("surveyId",surveyId);
		return surveyJsonDao.findFirst("saveDate",false, cri1);
	}

	@Override
	public String devSurvey(String surveyId) {
		return devSurveyJson(surveyId);
	}

	public String devSurveyJson(String surveyId) {
		try {
			SurveyDirectory survey = surveyManager.findUniqueBy(surveyId);
			SurveyJson surveyJson = findBySurveyId(surveyId);
			String sId = survey.getSid();
			ObjectMapper mapper = new ObjectMapper();

			String infoJsonString = mapper.writeValueAsString(HttpResult.SUCCESS(survey));
			String savePath = File.separator+"v6"+File.separator+"file"+File.separator+"survey"+File.separator+sId+File.separator;;
			DwSurveyUtils.writerJson(infoJsonString, savePath, sId+"_info.json");

			String jsonString = mapper.writeValueAsString(HttpResult.SUCCESS(surveyJson));
			String fileName = sId+".json";
			DwSurveyUtils.writerJson(jsonString, savePath, fileName);

			return savePath+fileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
