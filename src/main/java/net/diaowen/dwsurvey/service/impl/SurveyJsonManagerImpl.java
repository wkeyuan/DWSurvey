package net.diaowen.dwsurvey.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author keyuan
 * keyuan258@gmail.com
 *
 */

@Service
public class SurveyJsonManagerImpl extends BaseServiceImpl<SurveyJson, String> implements SurveyJsonManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());

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

	@Transactional
	public String devSurveyJson(String surveyId) {
		try {
			Date date = new Date();
			Long dateTime = date.getTime();
			ObjectMapper objectMapper = new ObjectMapper();
			// 发布时生成版本号
			SurveyDirectory survey = surveyManager.findUniqueBy(surveyId);
			survey.setSurveyState(1);
			surveyManager.save(survey);
			SurveyJson surveyJson = findBySurveyId(surveyId);
			String sId = survey.getSid();
			ObjectMapper mapper = new ObjectMapper();
			String surveyJsonSimple = surveyJson.getSurveyJsonSimple();
			String surveyJsonText = surveyJson.getSurveyJsonText();
			JsonNode surveyJsonSimpleNode = objectMapper.readTree(surveyJsonSimple);
			JsonNode surveyJsonTextNode = objectMapper.readTree(surveyJsonText);
			((ObjectNode) surveyJsonSimpleNode).put("dwVersion", dateTime);
			((ObjectNode) surveyJsonTextNode).put("dwVersion", dateTime);

			// 继续完善发布到答卷使用缓存方法
			String infoJsonString = mapper.writeValueAsString(surveyJsonSimpleNode);
			String savePath = File.separator+"v6"+File.separator+"file"+File.separator+"survey"+File.separator+sId+File.separator;;
			DwSurveyUtils.writerJson(infoJsonString, savePath, sId+"_info.json");

			String jsonString = mapper.writeValueAsString(surveyJsonTextNode);
			String fileName = sId+".json";
			DwSurveyUtils.writerJson(jsonString, savePath, fileName);


			// 同步更新到数据库中
			surveyJson.setSurveyJsonSimple(infoJsonString);
			surveyJson.setSurveyJsonText(jsonString);

			logger.info("savePath {}", savePath);
			surveyJson.setSaveDate(new Date());
			super.save(surveyJson);
			return savePath+fileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
