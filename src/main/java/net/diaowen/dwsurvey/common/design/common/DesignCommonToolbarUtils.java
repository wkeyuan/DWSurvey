package net.diaowen.dwsurvey.common.design.common;

import net.diaowen.common.QuType;
import net.diaowen.dwsurvey.common.design.entity.DesignSurveyToolbarTabQu;
import net.diaowen.dwsurvey.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class DesignCommonToolbarUtils {

    /**
     * 辅助组件列表
     * @return List<Question>
     */
    public static List<Question> ppQus() {
        List<Question> questions13 = new ArrayList<Question>();
        questions13.add(new Question(QuType.PAGETAG,null, "分页题", "<i class=\"fa-solid fa-pager\"></i>"));
        questions13.add(new Question(QuType.PARAGRAPH, "请输入段落标题", "分段题", "<i class=\"fa-solid fa-indent\"></i>"));
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
        Question importQus = new Question(null, "样式", null);
        importQus.setEventName("SurveyStyleEvent");
        importQus.setDwsurveyfont("icon-dwsurvey-yangshi");
        questionsClick.add(importQus);
        return new DesignSurveyToolbarTabQu("功能事件",questionsClick, "click");
    }
}
