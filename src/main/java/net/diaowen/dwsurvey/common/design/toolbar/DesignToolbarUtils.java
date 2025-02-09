package net.diaowen.dwsurvey.common.design.toolbar;

import net.diaowen.dwsurvey.common.design.entity.DesignSurveyToolbarTab;
import net.diaowen.dwsurvey.common.design.toolbar.exam.DesignExamToolbarUtils;
import net.diaowen.dwsurvey.common.design.toolbar.survey.DesignSurveyToolbarUtils;

import java.util.ArrayList;
import java.util.List;

public class DesignToolbarUtils {

    public static List<DesignSurveyToolbarTab> surveyToolbarTabsByType (String surveyType) {
        List<DesignSurveyToolbarTab> tabs = DesignSurveyToolbarUtils.surveyToolbarTabsV1();
        if ("survey".equals(surveyType)) {
            tabs = DesignSurveyToolbarUtils.surveyToolbarTabsV1();
        } else if ("exam".equals(surveyType)) {
            tabs = DesignExamToolbarUtils.examToolbarTabsV1();
        }
        return tabs;
    }

}
