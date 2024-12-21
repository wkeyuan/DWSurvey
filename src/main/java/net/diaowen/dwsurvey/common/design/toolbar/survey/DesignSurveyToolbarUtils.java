package net.diaowen.dwsurvey.common.design.toolbar.survey;

import net.diaowen.common.CheckType;
import net.diaowen.common.QuType;
import net.diaowen.dwsurvey.common.QuTemplateBankCommon;
import net.diaowen.dwsurvey.common.design.bank.DesignToolbarBankUtils;
import net.diaowen.dwsurvey.common.design.common.DesignCommonToolbarUtils;
import net.diaowen.dwsurvey.common.design.common.DesignExtCommonToolbarUtils;
import net.diaowen.dwsurvey.common.design.entity.DesignSurveyToolbarTab;
import net.diaowen.dwsurvey.common.design.entity.DesignSurveyToolbarTabQu;
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

    public static List<Question> matrixQus() {
        List<Question> questionsMatrix = new ArrayList<Question>();
        Question matrixRadio = new Question(QuType.MATRIX_RADIO, "矩阵单选", "矩阵单选", "<i class=\"fa-solid fa-list-check\"></i>");
        Question matrixCheckbox = new Question(QuType.MATRIX_CHECKBOX, "矩阵多选", "矩阵多选", "<i class=\"fa-solid fa-table-cells\"></i>");
        Question matrixInput = new Question(QuType.MATRIX_INPUT, "矩阵填空", "矩阵填空", "<i class=\"fa-solid fa-grip\"></i>");
        Question matrixNumber = new Question(QuType.MATRIX_NUMBER, "矩阵单选", "矩阵数值", "<i class=\"fa-solid fa-border-all\"></i>");
        Question matrixSelect = new Question(QuType.MATRIX_SELECT, "矩阵单选", "矩阵下拉", "<i class=\"fa-solid fa-rectangle-list\"></i>");
        Question matrixScale = new Question(QuType.MATRIX_SCALE, "矩阵量表", "矩阵量表", "<i class=\"fa-solid fa-ruler-combined\"></i>");
        Question matrixSlider = new Question(QuType.MATRIX_SLIDER, "矩阵滑块", "矩阵滑块", "<i class=\"fa-solid fa-sliders\"></i>");
        Question matrixGroup = new Question(QuType.MATRIX_GROUP, "矩阵单选", "矩阵组合", "<i class=\"fa-solid fa-object-group\"></i>");
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
        questionsMatrix.add(matrixNumber);
        questionsMatrix.add(matrixSelect);
        questionsMatrix.add(matrixGroup);
        Question matrixAutoAdd = QuTemplateBankCommon.getMatrixAutoAdd();
        matrixAutoAdd.setDwsurveyfont("icon-dwsurvey-juzhenzuhe");
        questionsMatrix.add(matrixAutoAdd);
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
        tabQus1.add(DesignToolbarBankUtils.commonTabQu());
        tabQus1.add(DesignCommonToolbarUtils.ppTabQu());
        tabQus1.add(DesignCommonToolbarUtils.clickTabQu());
        return new DesignSurveyToolbarTab("常用题型",tabQus1);
    }

    public static  DesignSurveyToolbarTab matrixExtToolbarTab() {
        List<DesignSurveyToolbarTabQu> tabQus2 = new ArrayList<>();
        tabQus2.add(matrixTabQu());
        tabQus2.add(DesignCommonToolbarUtils.ppTabQu());
        tabQus2.add(DesignCommonToolbarUtils.clickTabQu());
        return new DesignSurveyToolbarTab("矩阵扩展",tabQus2);
    }

    /**
     * 获取标准问卷组件集合，默认的工作栏组件集合
     * @return List<DesignSurveyToolbarTab>
     */
    public static List<DesignSurveyToolbarTab> surveyToolbarTabs() {
        List<DesignSurveyToolbarTab> tabs = new ArrayList<>();
        tabs.add(commonToolbarTab());
        tabs.add(matrixExtToolbarTab());
        return tabs;
    }

    /**
     * 重新定义
     * @return
     */
    public static DesignSurveyToolbarTab toolbarTab1 () {
        List<DesignSurveyToolbarTabQu> tabQus1 = new ArrayList<>();
        tabQus1.add(baseTabQu());
        tabQus1.add(matrixTabQu());
        tabQus1.add(DesignCommonToolbarUtils.ppTabQu());
        tabQus1.add(DesignCommonToolbarUtils.clickTabQu());
        return new DesignSurveyToolbarTab("常用题型",tabQus1);
    }

    /**
     * 引入矩阵题后的工具栏集合
     * @return List<DesignSurveyToolbarTab>
     */
    public static List<DesignSurveyToolbarTab> surveyToolbarTabsV1() {
        List<DesignSurveyToolbarTab> tabs = new ArrayList<>();
        tabs.add(toolbarTab1());
        tabs.add(DesignToolbarBankUtils.toolbarTab2());
        tabs.add(DesignExtCommonToolbarUtils.extToolbarTab3());
        return tabs;
    }
}
