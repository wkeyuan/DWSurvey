package net.diaowen.dwsurvey.common;

import java.util.List;

public class DesignSurveyToolbarTab {

    private String tabName;

    private List<DesignSurveyToolbarTabQu> tabQus;


    public DesignSurveyToolbarTab(){

    }

    public DesignSurveyToolbarTab(String tabName, List<DesignSurveyToolbarTabQu> tabQus){
        this.tabName = tabName;
        this.tabQus = tabQus;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public List<DesignSurveyToolbarTabQu> getTabQus() {
        return tabQus;
    }

    public void setTabQus(List<DesignSurveyToolbarTabQu> tabQus) {
        this.tabQus = tabQus;
    }
}
