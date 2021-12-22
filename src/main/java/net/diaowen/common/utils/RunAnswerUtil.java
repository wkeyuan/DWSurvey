package net.diaowen.common.utils;

import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.impl.SurveyAnswerManagerImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RunAnswerUtil {

    /**
     * 返回新question Map
     * @param questions
     * @param surveyAnswerId
     * @return
     */
    public Map<Integer,Question> getQuestionMap(List<Question> questions,String surveyAnswerId) {
        int quIndex = 0;
        Map<Integer,Question> questionMap = new ConcurrentHashMap<Integer,Question>();
        for (Question question : questions) {
            new Thread(new RAnswerQuestionMap(quIndex++,questionMap,surveyAnswerId,question)).start();
        }
        while (questionMap.size() != questions.size()){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return questionMap;
    }


    public class RAnswerQuestionMap implements Runnable{
        private int quIndex;
        private Map<Integer,Question> questionMap;
        private String surveyAnswerId;
        private Question question;
        public RAnswerQuestionMap(int quIndex, Map<Integer,Question> questionMap, String surveyAnswerId, Question question){
            this.quIndex = quIndex;
            this.questionMap = questionMap;
            this.surveyAnswerId = surveyAnswerId;
            this.question = question;
        }

        @Override
        public void run() {
            SurveyAnswerManager surveyManager = SpringContextHolder.getBean(SurveyAnswerManagerImpl.class);
            surveyManager.getquestionAnswer(surveyAnswerId, question);
            questionMap.put(quIndex, question);
        }
    }

}
