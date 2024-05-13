package net.diaowen.dwsurvey.common;

import net.diaowen.common.CheckType;
import net.diaowen.common.QuType;
import net.diaowen.dwsurvey.entity.*;

import java.util.ArrayList;
import java.util.List;

public class DesignSurveyToolbarUtils {

    /**
     * 获取基本题型列表
     * @return List<Question>
     */
    public static List<Question> baseQus () {
        List<Question> questions11 = new ArrayList<>();
        Question radioQuestion = QuTemplateBankCommon.getRadioQuestion();
        Question checkboxQuestion = QuTemplateBankCommon.getCheckboxQuestion();
        Question fbkQuestion = QuTemplateBankCommon.getInputQuestion();
        Question mFbkQuestion = QuTemplateBankCommon.getmFbkQuestion();
        Question scoreQuestion = QuTemplateBankCommon.getScoreQuestion();
        Question orderQuestion = QuTemplateBankCommon.getOrderQuestion();
        Question selectQuestion = QuTemplateBankCommon.getSelectQuestion();
        Question textareaQuestion = QuTemplateBankCommon.getTextareaQuestion();
        Question upQuestion = QuTemplateBankCommon.getUploadQuestion();
        questions11.add(radioQuestion);
        questions11.add(checkboxQuestion);
        questions11.add(fbkQuestion);
        questions11.add(mFbkQuestion);
        questions11.add(scoreQuestion);
        questions11.add(orderQuestion);
        questions11.add(selectQuestion);
        questions11.add(textareaQuestion);
        questions11.add(upQuestion);
        return questions11;
    }

    /**
     * 获取基本题型Tab封装
     * @return DesignSurveyToolbarTabQu
     */
    public static DesignSurveyToolbarTabQu baseTabQu () {
        return new DesignSurveyToolbarTabQu("基本题型",baseQus());
    }

    /**
     * 获取常用题列表
     * @return List<Question>
     */
    public static List<Question> commonQus () {
        List<Question> questions12 = new ArrayList<Question>();
        //日期 时间 邮箱 性别 学历 收入
        Question dataFbk = new Question(QuType.FILLBLANK, "请输入日期", null);
        dataFbk.setCheckType(CheckType.DATE);
        Question timeFbk = new Question(QuType.FILLBLANK, "请输入时间", null);
        timeFbk.setCheckType(CheckType.TIME);
        Question emailFbk = new Question(QuType.FILLBLANK, "请输入邮箱", null);
        emailFbk.setCheckType(CheckType.EMAIL);

        Question genderRadio = new Question(QuType.RADIO, "请选择性别", null);
        List<QuRadio> genderRadios = new ArrayList<>();
        genderRadios.add(new QuRadio("男",null));
        genderRadios.add(new QuRadio("女", null));
        genderRadio.setQuRadios(genderRadios);

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
        return questions12;
    }

    /**
     * 常用题Tab集
     * @return DesignSurveyToolbarTabQu
     */
    public static DesignSurveyToolbarTabQu commonTabQu () {
        return new DesignSurveyToolbarTabQu("常用题",commonQus());
    }

    /**
     * 辅助组件列表
     * @return List<Question>
     */
    public static List<Question> ppQus() {
        List<Question> questions13 = new ArrayList<Question>();
        questions13.add(new Question(QuType.PAGETAG,null, null));
        questions13.add(new Question(QuType.PARAGRAPH, "请输入段落标题", "请输入段落标题"));
        return questions13;
    }

    /**
     * 辅助组件Tab集
     * @return DesignSurveyToolbarTabQu
     */
    public static DesignSurveyToolbarTabQu ppTabQu () {
        return new DesignSurveyToolbarTabQu("辅助组件",ppQus());
    }

    /**
     * 功能配置，一般用于工具栏click触发事件
     * @return DesignSurveyToolbarTabQu
     */
    public static DesignSurveyToolbarTabQu clickTabQu () {
        List<Question> questionsClick = new ArrayList<Question>();
        /*
        // click示例
        Question importQus = new Question(null, "导入题目", null);
        importQus.setEventName("ImportQuEvent");
        importQus.setDwsurveyfont("icon-dwsurvey-daoru");
        */
        return new DesignSurveyToolbarTabQu("功能配置",questionsClick, "click");
    }



    public static List<Question> matrixQus() {
        List<Question> questionsMatrix = new ArrayList<Question>();
        Question matrixRadio = new Question(QuType.MATRIX_RADIO, "矩阵单选", null);
        Question matrixCheckbox = new Question(QuType.MATRIX_CHECKBOX, "矩阵多选", null);
        Question matrixInput = new Question(QuType.MATRIX_INPUT, "矩阵填空", null);
        Question matrixNumber = new Question(QuType.MATRIX_NUMBER, "矩阵单选", null);
        Question matrixSelect = new Question(QuType.MATRIX_SELECT, "矩阵单选", null);
        Question matrixScale = new Question(QuType.MATRIX_SCALE, "矩阵量表", null);
        Question matrixSlider = new Question(QuType.MATRIX_SLIDER, "矩阵滑块", null);
        Question matrixGroup = new Question(QuType.MATRIX_GROUP, "矩阵单选", null);
        matrixRadio.setDwsurveyfont("icon-dwsurvey-juzhendanxuan");
        matrixCheckbox.setDwsurveyfont("icon-dwsurvey-juzhenduoxuan");
        matrixInput.setDwsurveyfont("icon-dwsurvey-juzhentiankong");
        matrixNumber.setDwsurveyfont("icon-dwsurvey-juzhenshuzhi");
        matrixSelect.setDwsurveyfont("icon-dwsurvey-juzhenxiala");
        matrixScale.setDwsurveyfont("icon-dwsurvey-juzhenliangbiao");
        matrixSlider.setDwsurveyfont("icon-dwsurvey-juzhenhuakuai");
        matrixGroup.setDwsurveyfont("icon-dwsurvey-juzhenzuhe");
        questionsMatrix.add(matrixRadio);
        questionsMatrix.add(matrixCheckbox);
        questionsMatrix.add(matrixInput);
        questionsMatrix.add(matrixScale);
        questionsMatrix.add(matrixSlider);
//        questionsMatrix.add(matrixNumber);
//        questionsMatrix.add(matrixSelect);
//        questionsMatrix.add(matrixGroup);
        return questionsMatrix;
    }

    public static DesignSurveyToolbarTabQu matrixTabQu() {
        return new DesignSurveyToolbarTabQu("矩阵题型", matrixQus());
    }

    /**
     * 常用Tab下的集合
     * @return DesignSurveyToolbarTab
     */
    public static DesignSurveyToolbarTab commonToolbarTab () {
        List<DesignSurveyToolbarTabQu> tabQus1 = new ArrayList<>();
        tabQus1.add(baseTabQu());
        tabQus1.add(commonTabQu());
        tabQus1.add(ppTabQu());
        tabQus1.add(clickTabQu());
        return new DesignSurveyToolbarTab("常用题型",tabQus1);
    }

    public static  DesignSurveyToolbarTab matrixExtToolbarTab() {
        List<DesignSurveyToolbarTabQu> tabQus2 = new ArrayList<>();
        tabQus2.add(matrixTabQu());
        tabQus2.add(ppTabQu());
        tabQus2.add(clickTabQu());
        return new DesignSurveyToolbarTab("矩阵扩展",tabQus2);
    }

    /**
     * 获取标准问卷组件集合
     * @return List<DesignSurveyToolbarTab>
     */
    public static List<DesignSurveyToolbarTab> surveyToolbarTabs() {
        List<DesignSurveyToolbarTab> tabs = new ArrayList<>();
        tabs.add(commonToolbarTab());
        tabs.add(matrixExtToolbarTab());
        return tabs;
    }
}
