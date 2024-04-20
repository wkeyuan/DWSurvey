package net.diaowen.dwsurvey.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DWSurveyConfig {

    public static String DWSURVEY_SITE = null;
    public static String DWSURVEY_WEB_FILE_PATH = null;
    public static String DWSURVEY_WEB_SITE_URL = null;
    public static String DWSURVEY_WEB_RESOURCE_URL = null;
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

    public static String DWSURVEY_FILE_STORAGE_PREFIX = null;
    public static String DWSURVEY_FILE_STORAGE_TYPE = null;

    //云存储
    public static String DWSURVEY_ALIYUN_ENDPOINT = "";
    public static String DWSURVEY_ALIYUN_ACCESS_KEY_ID = "";
    public static String DWSURVEY_ALIYUN_SECRET_ACCESS_KEY = "";
    public static String DWSURVEY_ALIYUN_BUCKET_PREFIX = "";


    public static String DWSURVEY_ALIYUN_WEB_PUBLIC_BUCKET = "web-pub";
    public static String DWSURVEY_ALIYUN_WEB_PRIVATE_BUCKET  = "web-pri";

    @Value("${dwsurvey.web.file-path}")
    public void setWebFilePath(String webFilePath) {
        DWSurveyConfig.DWSURVEY_WEB_FILE_PATH = webFilePath;
    }

    @Value("${dwsurvey.web.site-url}")
    public void setWebSiteUrl(String webSiteUrl) {
        DWSurveyConfig.DWSURVEY_WEB_SITE_URL = webSiteUrl;
    }

    @Value("${dwsurvey.web.resource-url}")
    public void setWebResourceUrl(String dwsurveyWebResourceUrl) {
        DWSURVEY_WEB_RESOURCE_URL = dwsurveyWebResourceUrl;
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


    @Value("${dwsurvey.file-storage.prefix}")
    public void setDwsurveyFileStoragePrefix(String dwsurveyFileStoragePrefix) {
        DWSURVEY_FILE_STORAGE_PREFIX = dwsurveyFileStoragePrefix;
    }

    @Value("${dwsurvey.file-storage.type}")
    public void setDwsurveyFileStorageType(String dwsurveyFileStorageType) {
        DWSURVEY_FILE_STORAGE_TYPE = dwsurveyFileStorageType;
    }

    @Value("${dwsurvey.file-storage.aliyun.endpoint}")
    public void setDwsurveyAliyunEndpoint(String dwsurveyAliyunEndpoint) {
        DWSURVEY_ALIYUN_ENDPOINT = dwsurveyAliyunEndpoint;
    }

    @Value("${dwsurvey.file-storage.aliyun.access-keyid}")
    public void setDwsurveyAliyunAccessKeyId(String dwsurveyAliyunAccessKeyId) {
        DWSURVEY_ALIYUN_ACCESS_KEY_ID = dwsurveyAliyunAccessKeyId;
    }

    @Value("${dwsurvey.file-storage.aliyun.secret-accesskey}")
    public void setDwsurveyAliyunSecretAccessKey(String dwsurveyAliyunSecretAccessKey) {
        DWSURVEY_ALIYUN_SECRET_ACCESS_KEY = dwsurveyAliyunSecretAccessKey;
    }

    @Value("${dwsurvey.file-storage.aliyun.bucket.prefix}")
    public void setDwsurveyAliyunBucketPrefix(String dwsurveyAliyunBucketPrefix) {
        DWSURVEY_ALIYUN_BUCKET_PREFIX = dwsurveyAliyunBucketPrefix;
    }

    @Value("${dwsurvey.file-storage.aliyun.bucket.web-pub}")
    public void setDwsurveyAliyunWebPublicBucket(String dwsurveyAliyunWebPublicBucket) {
        DWSURVEY_ALIYUN_WEB_PUBLIC_BUCKET = dwsurveyAliyunWebPublicBucket;
    }

    @Value("${dwsurvey.file-storage.aliyun.bucket.web-pri}")
    public void setDwsurveyAliyunWebPrivateBucket(String dwsurveyAliyunWebPrivateBucket) {
        DWSURVEY_ALIYUN_WEB_PRIVATE_BUCKET = dwsurveyAliyunWebPrivateBucket;
    }
}
