package net.diaowen.dwsurvey.common.design.toolbar.exam;

import net.diaowen.common.CheckType;
import net.diaowen.common.QuType;
import net.diaowen.dwsurvey.common.QuTemplateBankCommon;
import net.diaowen.dwsurvey.common.design.bank.DesignToolbarBankUtils;
import net.diaowen.dwsurvey.common.design.common.DesignCommonToolbarUtils;
import net.diaowen.dwsurvey.common.design.entity.DesignSurveyToolbarTab;
import net.diaowen.dwsurvey.common.design.entity.DesignSurveyToolbarTabQu;
import net.diaowen.dwsurvey.entity.QuRadio;
import net.diaowen.dwsurvey.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class DesignExamToolbarUtils {

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
        questions11.add(radioQuestion);
        questions11.add(checkboxQuestion);
        questions11.add(fbkQuestion);
        questions11.add(mFbkQuestion);
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
     * 重新定义
     * @return
     */
    public static DesignSurveyToolbarTab toolbarTab1 () {
        List<DesignSurveyToolbarTabQu> tabQus1 = new ArrayList<>();
        tabQus1.add(baseTabQu());
        tabQus1.add(DesignCommonToolbarUtils.ppTabQu());
        tabQus1.add(DesignCommonToolbarUtils.clickTabQu());
        return new DesignSurveyToolbarTab("考试题型",tabQus1);
    }

    /**
     * 引入考试题型的工具栏集合
     * @return List<DesignSurveyToolbarTab>
     */
    public static List<DesignSurveyToolbarTab> examToolbarTabsV1() {
        List<DesignSurveyToolbarTab> tabs = new ArrayList<>();
        tabs.add(toolbarTab1());
        tabs.add(DesignToolbarBankUtils.toolbarTab2());
        return tabs;
    }
}
