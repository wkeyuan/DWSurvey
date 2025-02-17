package net.diaowen.dwsurvey.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.diaowen.common.QuType;
import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.common.utils.RandomUtils;
import net.diaowen.dwsurvey.dao.SurveyAnswerJsonDao;
import net.diaowen.dwsurvey.entity.*;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswer;
import net.diaowen.dwsurvey.service.SurveyAnswerJsonManager;
import net.diaowen.dwsurvey.service.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author keyuan
 * keyuan258@gmail.com
 *
 */

@Service
public class SurveyAnswerJsonManagerImpl extends BaseServiceImpl<SurveyAnswerJson, String> implements SurveyAnswerJsonManager {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SurveyAnswerJsonDao surveyAnswerJsonDao;
	@Autowired
	private UserManager userManager;

	@Override
	public void setBaseDao() {
		this.baseDao=surveyAnswerJsonDao;
	}

	public SurveyAnswerJson findByAnswerId(String answerId) {
		return surveyAnswerJsonDao.findUniqueBy("answerId",answerId);
	}

	@Override
	public DwEsSurveyAnswer getDwEsSurveyAnswer(String answerId) {
		SurveyAnswerJson surveyAnswerJson = findByAnswerId(answerId);
		if (surveyAnswerJson!=null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				DwEsSurveyAnswer dwEsSurveyAnswer = objectMapper.readValue(surveyAnswerJson.getAnswerJson(), DwEsSurveyAnswer.class);
				dwEsSurveyAnswer.getAnswerCommon().setAnswerId(answerId);
				return dwEsSurveyAnswer;
			} catch (JsonProcessingException e) {
				logger.error("dwEsSurveyAnswer 转换错误 {}", e.getMessage());
			}
		}
		return null;
	}
}
