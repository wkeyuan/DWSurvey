package net.diaowen.common.plugs.footer;

import net.diaowen.dwsurvey.config.DWSurveyConfig;

public class FooterInfo {

    public static String getVersionInfo() {
        return DWSurveyConfig.DWSURVEY_VERSION_INFO;
    }

    public static String getVersionNumber() {
        return DWSurveyConfig.DWSURVEY_VERSION_NUMBER;
    }

    public static String getWebInfoSiteName() {
        return DWSurveyConfig.DWSURVEY_WEB_INFO_SITE_NAME;
    }

    public static String getWebInfoSiteUrl() {
        return DWSurveyConfig.DWSURVEY_WEB_INFO_SITE_URL;
    }

    public static String getWebInfoSiteMail() {
        return DWSurveyConfig.DWSURVEY_WEB_INFO_SITE_MAIL;
    }

    public static String getWebInfoSiteICP() {
        return "（"+DWSurveyConfig.DWSURVEY_WEB_INFO_SITE_ICP+"）";
    }

    public static String getWebInfoSiteICP1() {
        return DWSurveyConfig.DWSURVEY_WEB_INFO_SITE_ICP;
    }

    public static String getWebInfoSitePhone() {
        return DWSurveyConfig.DWSURVEY_WEB_INFO_SITE_PHONE;
    }


}
