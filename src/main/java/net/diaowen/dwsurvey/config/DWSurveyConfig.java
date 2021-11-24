package net.diaowen.dwsurvey.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DWSurveyConfig {

    public static String DWSURVEY_SITE = null;
    public static String DWSURVEY_WEB_FILE_PATH = null;
    public static String DWSURVEY_WEB_BACK_SITE_MODE = null;
    public static String DWSURVEY_WEB_BACK_SITE_URL = null;
    public static String DWSURVEY_WEB_UP_FILE_TYPE_ANON_ = null;
    public static String DWSURVEY_WEB_UP_FILE_TYPE_USER = null;
    public static String DWSURVEY_VERSION_INFO = null;
    public static String DWSURVEY_VERSION_NUMBER = null;
    public static String DWSURVEY_VERSION_BUILT = null;
    public static String DWSURVEY_WEB_INFO_SITE_NAME = null;
    public static String DWSURVEY_WEB_INFO_SITE_URL = null;
    public static String DWSURVEY_WEB_INFO_SITE_ICP = null;
    public static String DWSURVEY_WEB_INFO_SITE_MAIL = null;
    public static String DWSURVEY_WEB_INFO_SITE_PHONE = null;

    @Value("${dwsurvey.site}")
    public void setDwsurveySite(String dwsurveySite) {
        DWSurveyConfig.DWSURVEY_SITE = dwsurveySite;
    }

    @Value("${dwsurvey.web.file-path}")
    public void setDwsurveyWebFilePath(String dwsurveyWebFilePath) {
        DWSurveyConfig.DWSURVEY_WEB_FILE_PATH = dwsurveyWebFilePath;
    }

    @Value("${dwsurvey.web.back-site.mode}")
    public void setDwsurveyWebBackSiteMode(String dwsurveyWebBackSiteMode) {
        DWSurveyConfig.DWSURVEY_WEB_BACK_SITE_MODE = dwsurveyWebBackSiteMode;
    }

    @Value("${dwsurvey.web.back-site.url}")
    public void setDwsurveyWebBackSiteUrl(String dwsurveyWebBackSiteUrl) {
        DWSurveyConfig.DWSURVEY_WEB_BACK_SITE_URL = dwsurveyWebBackSiteUrl;
    }

    @Value("${dwsurvey.web.up.file-type.anon}")
    public void setDwsurveyWebUpFileTypeAnon(String dwsurveyWebUpFileTypeAnon) {
        DWSURVEY_WEB_UP_FILE_TYPE_ANON_ = dwsurveyWebUpFileTypeAnon;
    }

    @Value("${dwsurvey.web.up.file-type.user}")
    public void setDwsurveyWebUpFileTypeUser(String dwsurveyWebUpFileTypeUser) {
        DWSURVEY_WEB_UP_FILE_TYPE_USER = dwsurveyWebUpFileTypeUser;
    }


    @Value("${dwsurvey.version.info}")
    public void setDwsurveyVersionInfo(String dwsurveyVersionInfo) {
        DWSURVEY_VERSION_INFO = dwsurveyVersionInfo;
    }

    @Value("${dwsurvey.version.number}")
    public void setDwsurveyVersionNumber(String dwsurveyVersionNumber) {
        DWSURVEY_VERSION_NUMBER = dwsurveyVersionNumber;
    }

    @Value("${dwsurvey.version.built}")
    public void setDwsurveyVersionBuilt(String dwsurveyVersionBuilt) {
        DWSURVEY_VERSION_BUILT = dwsurveyVersionBuilt;
    }

    @Value("${dwsurvey.web.info.site-name}")
    public void setDwsurveyWebInfoName(String dwsurveyWebInfoName) {
        DWSURVEY_WEB_INFO_SITE_NAME = dwsurveyWebInfoName;
    }

    @Value("${dwsurvey.web.info.site-url}")
    public void setDwsurveyWebInfoUrl(String dwsurveyWebInfoName) {
        DWSURVEY_WEB_INFO_SITE_URL = dwsurveyWebInfoName;
    }

    @Value("${dwsurvey.web.info.site-icp}")
    public void setDwsurveyWebInfoIcp(String dwsurveyWebInfoIcp) {
        DWSURVEY_WEB_INFO_SITE_ICP = dwsurveyWebInfoIcp;
    }

    @Value("${dwsurvey.web.info.site-mail}")
    public void setDwsurveyWebInfoMail(String dwsurveyWebInfoMail) {
        DWSURVEY_WEB_INFO_SITE_MAIL = dwsurveyWebInfoMail;
    }

    @Value("${dwsurvey.web.info.site-phone}")
    public void setDwsurveyWebInfoSitePhone(String dwsurveyWebInfoSitePhone) {
        DWSURVEY_WEB_INFO_SITE_PHONE = dwsurveyWebInfoSitePhone;
    }
}
