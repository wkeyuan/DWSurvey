package net.diaowen.dwsurvey.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DWSurveyConfig {

    public static String DWSURVEY_SITE = null;
    public static String DWSURVEY_WEB_FILE_PATH = null;
    public static String DWSURVEY_WEB_SITE_URL = null;
    public static String DWSURVEY_WEB_STATIC_TYPE = null;
    public static String DWSURVEY_WEIXIN_APP_ID = null;
    public static String DWSURVEY_WEIXIN_APP_SECRET = null;
    public static String DWSURVEY_WEIXIN_SERVER_TOKEN = null;
    public static String DWSURVEY_WEIXIN_SERVER_AESKEY = null;

    public static String DWSURVEY_VERSION_INFO = null;
    public static String DWSURVEY_VERSION_NUMBER = null;
    public static String DWSURVEY_VERSION_BUILT = null;
    public static String DWSURVEY_WEB_INFO_SITE_NAME = null;
    public static String DWSURVEY_WEB_INFO_SITE_URL = null;
    public static String DWSURVEY_WEB_INFO_SITE_ICP = null;
    public static String DWSURVEY_WEB_INFO_SITE_MAIL = null;
    public static String DWSURVEY_WEB_INFO_SITE_PHONE = null;


    @Value("${dwsurvey.web.file-path}")
    public void setWebFilePath(String webFilePath) {
        DWSurveyConfig.DWSURVEY_WEB_FILE_PATH = webFilePath;
    }

    @Value("${dwsurvey.web.site-url}")
    public void setWebSiteUrl(String webSiteUrl) {
        DWSurveyConfig.DWSURVEY_WEB_SITE_URL = webSiteUrl;
    }

    @Value("${dwsurvey.web.static-type}")
    public void setWebStaticType(String webStaticType) {
        DWSurveyConfig.DWSURVEY_WEB_STATIC_TYPE = webStaticType;
    }

    @Value("${dwsurvey.weixin.app-id}")
    public void setWeixinAppId(String weixinAppId) {
        DWSurveyConfig.DWSURVEY_WEIXIN_APP_ID = weixinAppId;
    }

    @Value("${dwsurvey.weixin.app-secret}")
    public void setWeixinAppSecret(String weixinAppSecret) {
        DWSurveyConfig.DWSURVEY_WEIXIN_APP_SECRET = weixinAppSecret;
    }

    @Value("${dwsurvey.weixin.server.token}")
    public void setDwsurveyWeixinServerToken(String weixinServerToken) {
        DWSurveyConfig.DWSURVEY_WEIXIN_SERVER_TOKEN = weixinServerToken;
    }

    @Value("${dwsurvey.weixin.server.encodingAESKey}")
    public void setDwsurveyWeixinServerAeskey(String weixinAppSecret) {
        DWSurveyConfig.DWSURVEY_WEIXIN_SERVER_AESKEY = weixinAppSecret;
    }

    @Value("${dwsurvey.site}")
    public void setDwsurveyDemo(String dwsurveySite) {
        DWSurveyConfig.DWSURVEY_SITE = dwsurveySite;
    }


    @Value("${dwsurvey.version.info}")
    public void setDwsurveyVersionInfo(String dwsurveyVersionInfo) {
        DWSURVEY_VERSION_INFO = dwsurveyVersionInfo;
    }

    @Value("${dwsurvey.version.number}")
    public void setDwsurveyVersionNumber(String dwsurveyVersionNumber) { DWSURVEY_VERSION_NUMBER = dwsurveyVersionNumber; }

    @Value("${dwsurvey.version.built}")
    public void setDwsurveyVersionBuilt(String dwsurveyVersionBuilt) {
        DWSURVEY_VERSION_BUILT = dwsurveyVersionBuilt;
    }

    @Value("${dwsurvey.web.info.site-name}")
    public void setDwsurveyWebInfoName(String dwsurveyWebInfoName) { DWSURVEY_WEB_INFO_SITE_NAME = dwsurveyWebInfoName;}

    @Value("${dwsurvey.web.info.site-url}")
    public void setDwsurveyWebInfoUrl(String dwsurveyWebInfoName) {
        DWSURVEY_WEB_INFO_SITE_URL = dwsurveyWebInfoName;
    }

    @Value("${dwsurvey.web.info.site-icp}")
    public void setDwsurveyWebInfoIcp(String dwsurveyWebInfoIcp) {
        DWSURVEY_WEB_INFO_SITE_ICP = dwsurveyWebInfoIcp;
    }

    @Value("${dwsurvey.web.info.site-mail}")
    public void setDwsurveyWebInfoMail(String dwsurveyWebInfoMail) { DWSURVEY_WEB_INFO_SITE_MAIL = dwsurveyWebInfoMail; }

    @Value("${dwsurvey.web.info.site-phone}")
    public void setDwsurveyWebInfoSitePhone(String dwsurveyWebInfoSitePhone) { DWSURVEY_WEB_INFO_SITE_PHONE = dwsurveyWebInfoSitePhone; }
}
