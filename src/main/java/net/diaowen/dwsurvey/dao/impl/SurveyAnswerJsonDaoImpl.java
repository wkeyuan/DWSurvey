package net.diaowen.dwsurvey.dao.impl;

import net.diaowen.common.dao.BaseDaoImpl;
import net.diaowen.dwsurvey.dao.SurveyAnswerJsonDao;
import net.diaowen.dwsurvey.entity.SurveyAnswerJson;
import org.springframework.stereotype.Repository;

@Repository
public class SurveyAnswerJsonDaoImpl extends BaseDaoImpl<SurveyAnswerJson, String> implements SurveyAnswerJsonDao {

    public int countByJsonKey(String surveyId,String keyword) {
        //select count(*) from t_an_chen_checkbox where visibility=1 and  qu_id='4028a0ee722adb9f01722adc1dae0003' and qu_row_id = '4028a0ee722adb9f01722adc1daf0004' and qu_col_ids LIKE '%4028a0ee722adb9f01722adc1daf0006%';
        String sql = "select count(*) from t_survey_answer_json where visibility=1 and survey_id=? and answer_json LIKE ? ";
        Object countObj=this.getSession().createSQLQuery(sql).setParameter(1, surveyId).setParameter(2,"%"+keyword+"%").uniqueResult();
        return Integer.parseInt(countObj.toString());
    }

}
