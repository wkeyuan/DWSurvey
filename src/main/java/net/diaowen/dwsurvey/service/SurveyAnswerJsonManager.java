package net.diaowen.dwsurvey.service;

import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyAnswer;
import net.diaowen.dwsurvey.entity.SurveyAnswerJson;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswer;

import java.util.List;
import java.util.Map;

/**
 * 答卷业务
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface SurveyAnswerJsonManager extends BaseService<SurveyAnswerJson, String> {

    public SurveyAnswerJson findByAnswerId(String answerId);

    public DwEsSurveyAnswer getDwEsSurveyAnswer(String answerId);

}
