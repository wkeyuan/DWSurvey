package net.diaowen.dwsurvey.controller.design;

import net.diaowen.common.QuType;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.PageResult;
import net.diaowen.dwsurvey.common.DesignSurveyToolbarTab;
import net.diaowen.dwsurvey.common.DesignSurveyToolbarTabQu;
import net.diaowen.dwsurvey.entity.*;
import net.diaowen.dwsurvey.service.SurveyJsonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/dwsurvey/app/dw-design-survey")
public class DwDeisgnSurveyController {

    @Autowired
    private SurveyJsonManager surveyJsonManager;

    /**
     * 拉取问卷支持的题型
     * @return
     */
    @RequestMapping(value = "/toolbar-qus.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult toolbarQuestions() {

        List<Question> questions11 = new ArrayList<Question>();
        Question radioQuestion = new Question(QuType.RADIO, null, null);
        List<QuRadio> quRadios = new ArrayList<>();
        quRadios.add(new QuRadio("选项1","选项1"));
        quRadios.add(new QuRadio("选项2", "选项2"));
        radioQuestion.setQuRadios(quRadios);

        Question checkboxQuestion = new Question(QuType.CHECKBOX, null, null);
        List<QuCheckbox> quCheckboxs = new ArrayList<>();
        quCheckboxs.add(new QuCheckbox("选项1","选项1"));
        quCheckboxs.add(new QuCheckbox("选项2", "选项2"));
        checkboxQuestion.setQuCheckboxs(quCheckboxs);

        Question mFbkQuestion = new Question(QuType.MULTIFILLBLANK, null, null);
        List<QuMultiFillblank> quMultiFillblanks = new ArrayList<>();
        quMultiFillblanks.add(new QuMultiFillblank("选项1","选项1"));
        quMultiFillblanks.add(new QuMultiFillblank("选项2", "选项2"));
        mFbkQuestion.setQuMultiFillblanks(quMultiFillblanks);

        Question scoreQuestion = new Question(QuType.SCORE, null, null);
        scoreQuestion.setParamInt02(5);
        List<QuScore> quScores = new ArrayList<>();
        quScores.add(new QuScore("选项1","选项1"));
        quScores.add(new QuScore("选项2", "选项2"));
        scoreQuestion.setQuScores(quScores);

        Question orderQuestion = new Question(QuType.ORDERQU, null, null);
        List<QuOrderby> quOrderbys = new ArrayList<>();
        quOrderbys.add(new QuOrderby("选项1","选项1"));
        quOrderbys.add(new QuOrderby("选项2", "选项2"));
        orderQuestion.setQuOrderbys(quOrderbys);

        Question selectQuestion = new Question(QuType.RADIO, null, null);
        selectQuestion.setHv(4);
        Question textareaQuestion = new Question(QuType.FILLBLANK, null, null);
        textareaQuestion.setAnswerInputRow(3);

        questions11.add(radioQuestion);
        questions11.add(checkboxQuestion);
        questions11.add(new Question(QuType.FILLBLANK, null, null));
        questions11.add(mFbkQuestion);
        questions11.add(scoreQuestion);
        questions11.add(orderQuestion);
        questions11.add(selectQuestion);
        questions11.add(textareaQuestion);
        questions11.add(new Question(QuType.UPLOADFILE, null, null));

        List<Question> questions12 = new ArrayList<Question>();
        //日期 时间 邮箱 性别 学历 收入
        Question dataFbk = new Question(QuType.FILLBLANK, "请输入日期", "");
        Question timeFbk = new Question(QuType.FILLBLANK, "请输入时间", "");
        Question emailFbk = new Question(QuType.FILLBLANK, "请输入邮箱", "");

        Question genderRadio = new Question(QuType.RADIO, "请选择性别", "");
        List<QuRadio> genderRadios = new ArrayList<>();
        genderRadios.add(new QuRadio("男",""));
        genderRadios.add(new QuRadio("女", ""));
        genderRadio.setQuRadios(genderRadios);

        Question eduRadio = new Question(QuType.RADIO, "请选择学历", "");
        List<QuRadio> eduRadios = new ArrayList<>();
        eduRadios.add(new QuRadio("博士","博士"));
        eduRadios.add(new QuRadio("硕士", "硕士"));
        eduRadios.add(new QuRadio("本科", "本科"));
        eduRadios.add(new QuRadio("专科", "专科"));
        eduRadios.add(new QuRadio("其它", "其它"));
        eduRadio.setQuRadios(eduRadios);

        Question incomeRadio = new Question(QuType.RADIO, "请选择月收入", "");
        List<QuRadio> incomeRadios = new ArrayList<>();
        incomeRadios.add(new QuRadio("3000元以下","3000元以下"));
        incomeRadios.add(new QuRadio("3000-5000元", "3000-5000元"));
        incomeRadios.add(new QuRadio("5000-10000元", "5000-10000元"));
        incomeRadios.add(new QuRadio("10000-20000元", "10000-20000元"));
        incomeRadios.add(new QuRadio("20000元以上", "20000元以上"));
        incomeRadio.setQuRadios(incomeRadios);

        dataFbk.setDwsurveyfont("icon-dwsurvey-riqi");
        timeFbk.setDwsurveyfont("icon-dwsurvey-shijian");
        emailFbk.setDwsurveyfont("icon-dwsurvey-Email");
        genderRadio.setDwsurveyfont("icon-dwsurvey-xingbie");
        eduRadio.setDwsurveyfont("icon-dwsurvey-xueli");
        incomeRadio.setDwsurveyfont("icon-dwsurvey-shouru");

        questions12.add(dataFbk);
        questions12.add(timeFbk);
        questions12.add(emailFbk);
        questions12.add(genderRadio);
        questions12.add(eduRadio);
        questions12.add(incomeRadio);

        List<Question> questions13 = new ArrayList<Question>();
        questions13.add(new Question(QuType.PAGETAG,"", ""));
        questions13.add(new Question(QuType.PARAGRAPH, "", ""));

        List<DesignSurveyToolbarTab> tabs = new ArrayList<>();
        List<DesignSurveyToolbarTabQu> tabQus1 = new ArrayList<>();
        tabQus1.add(new DesignSurveyToolbarTabQu("基本题型",questions11));
        tabQus1.add(new DesignSurveyToolbarTabQu("常用题",questions12));
        tabQus1.add(new DesignSurveyToolbarTabQu("辅助组件",questions13));

        List<DesignSurveyToolbarTabQu> tabQus2 = new ArrayList<>();
        tabQus2.add(new DesignSurveyToolbarTabQu("基本题型",questions11));
        tabQus2.add(new DesignSurveyToolbarTabQu("常用题型",questions11));

        tabs.add(new DesignSurveyToolbarTab("常用题型",tabQus1));
        tabs.add(new DesignSurveyToolbarTab("扩展题型",tabQus2));

        return HttpResult.SUCCESS(tabs);
    }

    @RequestMapping(value = "/bank-qus.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult bankQuestions() {
        List<DesignSurveyToolbarTabQu> tabQus1 = new ArrayList<>();

        List<Question> questions12 = new ArrayList<Question>();
        //日期 时间 邮箱 性别 学历 收入
        Question dataFbk = new Question(QuType.FILLBLANK, "请输入日期", "日期");
        Question timeFbk = new Question(QuType.FILLBLANK, "请输入时间", "时间");
        Question emailFbk = new Question(QuType.FILLBLANK, "请输入邮箱", "邮箱");
        Question genderRadio = new Question(QuType.RADIO, "请选择性别", "性别");
        List<QuRadio> genderRadios = new ArrayList<>();
        genderRadios.add(new QuRadio("男", ""));
        genderRadios.add(new QuRadio("女", ""));
        genderRadio.setQuRadios(genderRadios);

        questions12.add(dataFbk);
        questions12.add(timeFbk);
        questions12.add(emailFbk);
        questions12.add(genderRadio);

        tabQus1.add(new DesignSurveyToolbarTabQu("联系人",questions12));
        tabQus1.add(new DesignSurveyToolbarTabQu("满意度",questions12));
        tabQus1.add(new DesignSurveyToolbarTabQu("市场调研",questions12));
        tabQus1.add(new DesignSurveyToolbarTabQu("意见反馈",questions12));
        return HttpResult.SUCCESS(tabQus1);
    }


    @RequestMapping(value = "/save-survey-json.do",method = RequestMethod.POST)
    @ResponseBody
    public HttpResult saveSurveyJson(@RequestBody SurveyJson surveyJson){
        surveyJsonManager.saveNew(surveyJson);
        return HttpResult.SUCCESS();
    }

}
