package net.diaowen.dwsurvey.controller.design.admin;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.diaowen.common.CheckType;
import net.diaowen.common.QuType;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.PageResult;
import net.diaowen.dwsurvey.common.DesignSurveyToolbarTab;
import net.diaowen.dwsurvey.common.DesignSurveyToolbarTabQu;
import net.diaowen.dwsurvey.entity.*;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
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
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
    @Autowired
    private AccountManager accountManager;

    /**
     * 拉取问卷支持的题型
     * @return
     */
    @RequestMapping(value = "/toolbar-qus.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult toolbarQuestions() {

        List<Question> questions11 = new ArrayList<Question>();
        Question radioQuestion = new Question(QuType.RADIO, "单选题标题", null);
        List<QuRadio> quRadios = new ArrayList<>();
        quRadios.add(new QuRadio("选项1","选项1"));
        quRadios.add(new QuRadio("选项2", "选项2"));
        radioQuestion.setQuRadios(quRadios);

        Question checkboxQuestion = new Question(QuType.CHECKBOX, "多选题标题", null);
        List<QuCheckbox> quCheckboxs = new ArrayList<>();
        quCheckboxs.add(new QuCheckbox("选项1","选项1"));
        quCheckboxs.add(new QuCheckbox("选项2", "选项2"));
        checkboxQuestion.setQuCheckboxs(quCheckboxs);

        Question mFbkQuestion = new Question(QuType.MULTIFILLBLANK, "多项填空题标题", null);
        List<QuMultiFillblank> quMultiFillblanks = new ArrayList<>();
        quMultiFillblanks.add(new QuMultiFillblank("选项1","选项1"));
        quMultiFillblanks.add(new QuMultiFillblank("选项2", "选项2"));
        mFbkQuestion.setQuMultiFillblanks(quMultiFillblanks);

        Question scoreQuestion = new Question(QuType.SCORE, "评分题标题", null);
        scoreQuestion.setParamInt02(5);
        List<QuScore> quScores = new ArrayList<>();
        quScores.add(new QuScore("选项1","选项1"));
        quScores.add(new QuScore("选项2", "选项2"));
        scoreQuestion.setQuScores(quScores);

        Question orderQuestion = new Question(QuType.ORDERQU, "排序题标题", null);
        List<QuOrderby> quOrderbys = new ArrayList<>();
        quOrderbys.add(new QuOrderby("选项1","选项1"));
        quOrderbys.add(new QuOrderby("选项2", "选项2"));
        orderQuestion.setQuOrderbys(quOrderbys);

        Question selectQuestion = new Question(QuType.RADIO, "下拉题标题", null);
        selectQuestion.setHv(4);
        List<QuRadio> selectQuRadios = new ArrayList<>();
        selectQuRadios.add(new QuRadio("选项1","选项1"));
        selectQuRadios.add(new QuRadio("选项2", "选项2"));
        selectQuestion.setQuRadios(selectQuRadios);

        Question textareaQuestion = new Question(QuType.FILLBLANK, "多行填空题标题", null);
        textareaQuestion.setAnswerInputRow(3);

        questions11.add(radioQuestion);
        questions11.add(checkboxQuestion);
        questions11.add(new Question(QuType.FILLBLANK, "填空题标题", null));
        questions11.add(mFbkQuestion);
        questions11.add(scoreQuestion);
        questions11.add(orderQuestion);
        questions11.add(selectQuestion);
        questions11.add(textareaQuestion);
        questions11.add(new Question(QuType.UPLOADFILE, "上传题标题", null));

        List<Question> questions12 = new ArrayList<Question>();
        //日期 时间 邮箱 性别 学历 收入
        Question dataFbk = new Question(QuType.FILLBLANK, "请输入日期", null);
        Question timeFbk = new Question(QuType.FILLBLANK, "请输入时间", null);
        Question emailFbk = new Question(QuType.FILLBLANK, "请输入邮箱", null);

        Question genderRadio = new Question(QuType.RADIO, "请选择性别", null);
        List<QuRadio> genderRadios = new ArrayList<>();
        genderRadios.add(new QuRadio("男",null));
        genderRadios.add(new QuRadio("女", null));
        genderRadio.setQuRadios(genderRadios);

        dataFbk.setCheckType(CheckType.DATE);
        emailFbk.setCheckType(CheckType.EMAIL);
        timeFbk.setCheckType(CheckType.TIME);

        Question eduRadio = new Question(QuType.RADIO, "请选择学历", null);
        List<QuRadio> eduRadios = new ArrayList<>();
        eduRadios.add(new QuRadio("博士","博士"));
        eduRadios.add(new QuRadio("硕士", "硕士"));
        eduRadios.add(new QuRadio("本科", "本科"));
        eduRadios.add(new QuRadio("专科", "专科"));
        eduRadios.add(new QuRadio("其它", "其它"));
        eduRadio.setQuRadios(eduRadios);

        Question incomeRadio = new Question(QuType.RADIO, "请选择月收入", null);
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
        questions13.add(new Question(QuType.PAGETAG,null, null));
        questions13.add(new Question(QuType.PARAGRAPH, "请输入段落标题", "请输入段落标题"));

        List<DesignSurveyToolbarTab> tabs = new ArrayList<>();
        List<DesignSurveyToolbarTabQu> tabQus1 = new ArrayList<>();
        tabQus1.add(new DesignSurveyToolbarTabQu("基本题型",questions11));
        tabQus1.add(new DesignSurveyToolbarTabQu("常用题",questions12));
        tabQus1.add(new DesignSurveyToolbarTabQu("辅助组件",questions13));

//        List<Question> questionsClick = new ArrayList<Question>();
//        questions13.add(new Question());
//        tabQus1.add(new DesignSurveyToolbarTabQu("功能配置",questionsClick, "", ""));

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
        Question nameFbk = new Question(QuType.FILLBLANK, "请输入姓名", "姓名");
        Question phoneFbk = new Question(QuType.FILLBLANK, "请输入手机号", "手机号");
        Question emailFbk = new Question(QuType.FILLBLANK, "请输入邮箱", "邮箱");
        Question genderRadio = new Question(QuType.RADIO, "请选择性别", "性别");
        List<QuRadio> genderRadios = new ArrayList<>();
        genderRadios.add(new QuRadio("男", null));
        genderRadios.add(new QuRadio("女", null));
        genderRadio.setQuRadios(genderRadios);
        Question codeFbk = new Question(QuType.FILLBLANK, "请输入身份证号", "身份证");
        Question ageFbk = new Question(QuType.FILLBLANK, "请输入年龄", "年龄");


        phoneFbk.setCheckType(CheckType.PHONENUM);
        emailFbk.setCheckType(CheckType.EMAIL);
        codeFbk.setCheckType(CheckType.IDENTCODE);
        ageFbk.setCheckType(CheckType.DIGITS);

        questions12.add(nameFbk);
        questions12.add(phoneFbk);
        questions12.add(emailFbk);
        questions12.add(genderRadio);
        questions12.add(codeFbk);
        questions12.add(ageFbk);
        tabQus1.add(new DesignSurveyToolbarTabQu("个人信息",questions12));

        List<Question> questions13 = new ArrayList<Question>();

        Question radio1 = new Question(QuType.RADIO, "对xxx满意度", "满意度");
        List<QuRadio> radioOptions1 = new ArrayList<>();
        radioOptions1.add(new QuRadio("很满意",null));
        radioOptions1.add(new QuRadio("满意", null));
        radioOptions1.add(new QuRadio("一般", null));
        radioOptions1.add(new QuRadio("不满意", null));
        radioOptions1.add(new QuRadio("很不满意", null));
        radio1.setQuRadios(radioOptions1);

        Question radio2 = new Question(QuType.RADIO, "对xxx适合", "适合");
        List<QuRadio> radioOptions2 = new ArrayList<>();
        radioOptions2.add(new QuRadio("非常适合",null));
        radioOptions2.add(new QuRadio("较合适", null));
        radioOptions2.add(new QuRadio("一般", null));
        radioOptions2.add(new QuRadio("不适合", null));
        radioOptions2.add(new QuRadio("很不适合", null));
        radio2.setQuRadios(radioOptions2);

        Question radio3 = new Question(QuType.RADIO, "对xxx难易度", "难易度");
        List<QuRadio> radioOptions3 = new ArrayList<>();
        radioOptions3.add(new QuRadio("非常容易",null));
        radioOptions3.add(new QuRadio("较容易", null));
        radioOptions3.add(new QuRadio("一般", null));
        radioOptions3.add(new QuRadio("较难", null));
        radioOptions3.add(new QuRadio("很难", null));
        radio3.setQuRadios(radioOptions3);

        Question radio4 = new Question(QuType.RADIO, "对xxx满意度", "合理性");
        List<QuRadio> radioOptions4 = new ArrayList<>();
        radioOptions4.add(new QuRadio("非常合理",null));
        radioOptions4.add(new QuRadio("较合理", null));
        radioOptions4.add(new QuRadio("一般", null));
        radioOptions4.add(new QuRadio("不合理", null));
        radioOptions4.add(new QuRadio("很不合理", null));
        radio4.setQuRadios(radioOptions4);

        questions13.add(radio1);
        questions13.add(radio2);
        questions13.add(radio3);
        questions13.add(radio4);

        tabQus1.add(new DesignSurveyToolbarTabQu("满意度",questions13));
        return HttpResult.SUCCESS(tabQus1);
    }


    /**
     * 获取问卷
     * @param surveyId
     * @return
     */
    @RequestMapping(value = "/survey-json-by-survey-id.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult surveyJsonBySurveyId(@RequestParam String surveyId){
        // 针对答卷场景需要考虑这些数据通过静态缓存获取。
        SurveyJson surveyJson = surveyJsonManager.findBySurveyId(surveyId);
        if (surveyJson==null) {
            SurveyDirectory survey = surveyDirectoryManager.getSurveyBySid(surveyId);
            if (survey!=null) surveyId = survey.getId();
            surveyJson = surveyJsonManager.findBySurveyId(surveyId);
        }
        return HttpResult.SUCCESS(surveyJson);
    }

    @RequestMapping(value = "/save-survey-json.do",method = RequestMethod.POST)
    @ResponseBody
    public HttpResult saveSurveyJson(@RequestBody SurveyJson surveyJson){
        User user= accountManager.getCurUser();
        if(user!=null){
            String userId = user.getId();
            String surveyId = surveyJson.getSurveyId();
            SurveyDirectory survey = surveyDirectoryManager.getSurvey(surveyId);
            if(!userId.equals(survey.getUserId())) return HttpResult.FAILURE_MSG("未登录或没有相应数据权限");
            // 同步更新问卷标题
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode surveyJsonSimpleNode = objectMapper.readTree(surveyJson.getSurveyJsonSimple());
                if (surveyJsonSimpleNode.has("surveyNameObj")){
                    String surveyText = surveyJsonSimpleNode.get("surveyNameObj").get("dwText").asText();
                    survey.setSurveyName(surveyText);
                    surveyDirectoryManager.save(survey);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            surveyJsonManager.saveNew(surveyJson);
            return HttpResult.SUCCESS();
        }
        return HttpResult.FAILURE();
    }

    @RequestMapping(value = "/dev-survey.do",method = RequestMethod.POST)
    @ResponseBody
    public HttpResult devSurvey(@RequestParam String surveyId){
        String jsonPath = surveyJsonManager.devSurvey(surveyId);
        if (jsonPath!=null) return HttpResult.SUCCESS();
        return HttpResult.FAILURE();
    }

}
