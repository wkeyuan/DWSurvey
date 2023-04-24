package net.diaowen.dwsurvey.controller.design;

import net.diaowen.common.QuType;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.PageResult;
import net.diaowen.dwsurvey.entity.QuRadio;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/dwsurvey/app/dw-design-survey")
public class DwDeisgnSurveyController {

    /**
     * 拉取问卷支持的题型
     * @param pageResult
     * @return
     */
    @RequestMapping(value = "/questions.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult questions(PageResult<SurveyDirectory> pageResult, String surveyName, Integer visibility, Integer surveyState, Integer answerNum, String startTime, String endTime, String groupId1, String groupId2) {
        List<Question> questions = new ArrayList<Question>();
        Question radioQuestion = new Question(QuType.RADIO);
        QuRadio quRadio1 = new QuRadio();
        quRadio1.setOptionTitle("选项1");
        QuRadio quRadio2 = new QuRadio();
        quRadio2.setOptionTitle("选项2");
        List<QuRadio> quRadios = new ArrayList<>();
        quRadios.add(quRadio1);
        quRadios.add(quRadio2);
        radioQuestion.setQuRadios(quRadios);
        questions.add(radioQuestion);
        questions.add(new Question(QuType.CHECKBOX));
        questions.add(new Question(QuType.FILLBLANK));
        questions.add(new Question(QuType.MULTIFILLBLANK));
        questions.add(new Question(QuType.SCORE));
        questions.add(new Question(QuType.ORDERQU));
        Question selectQuestion = new Question(QuType.RADIO);
        selectQuestion.setHv(4);
        questions.add(selectQuestion);
        Question textareaQuestion = new Question(QuType.FILLBLANK);
        textareaQuestion.setAnswerInputRow(3);
        questions.add(textareaQuestion);
        questions.add(new Question(QuType.UPLOADFILE));
        return HttpResult.SUCCESS(questions);
    }

}
