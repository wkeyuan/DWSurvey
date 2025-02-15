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
    public static String DWSURVEY_WEB_ROUTER_HISTORY = null;

    public static Boolean DWSURVEY_WEB_LOGIN_WX = null;
    public static Boolean DWSURVEY_WEB_LOGIN_PHONE = null;
    public static Boolean DWSURVEY_WEB_LOGIN_PWD = null;
    public static Boolean DWSURVEY_WEB_REGISTER = null;

    public static Boolean DWSURVEY_WEIXIN_OPEN = false;
    public static String DWSURVEY_WEIXIN_APP_ID = null;
    public static String DWSURVEY_WEIXIN_APP_SECRET = null;
    public static String DWSURVEY_WEIXIN_SERVER_TOKEN = null;
    public static String DWSURVEY_WEIXIN_SERVER_AESKEY = null;

    public static String DWSURVEY_SMS_CODE_OPEN = null;
    public static String DWSURVEY_SMS_SC_SMS_USER = null;
    public static String DWSURVEY_SMS_SC_SMS_KEY = null;

    public static Integer DWSURVEY_OPEN_API_TIMESTAMP = 3;
    public static String DWSURVEY_OPEN_API_STATUS = null;


    public static String DWSURVEY_VERSION_API = null;
    public static String DWSURVEY_VERSION_NAME = null;
    public static String DWSURVEY_VERSION_NAME_CODE = null;
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

    public static String DWSURVEY_RSA_PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChgg8NqHmDZPf1cAepeeEoIJPW1lBAVzIdZjKPQxQ/cFkwiJyTVp5Nt4dAjtPKIZ0SxhaedbgpaZDaL3/CgY6pHFKEaYOK5thk+qwZrJr1PSppsIEO6cXduszCeOwxZoeQKvCl8+IzIuA7mgqzxCdzurpkrVQNY4s/05JOK2vnUwIDAQAB";
    public static String DWSURVEY_RSA_PRIVATEKEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJlrwlgXUWqr6Twm2LnbZd1z2FZljAz/w3nCOmCyELgJC+yP2v7DsDpcMatyq83OZhyQzseQF6pmfTdiv1wxaCWfJhltTlngd0V+NYjnC4T7s9UOBp1W1tHkhHl+Kis+HdJbyUN50wE//e5weA5YUeDHy/ni/Ne0Rszo+SUp8X4TAgMBAAECgYBgZZ7U7sHPPnjrvJTMIphcjp9NmbxnpoRSCsHXY8qxd62wU6sMDDS8VpU23nQoehkatbnXFihofsEKfdcyvDBtB/H6vaLCT1gKW9Iw53Yiq0mZ0PGCbIQ4qteV4injS6SmYR3RSvhiZBcDgARlj7ErGYoowxQCN6a6FK5w5fTtoQJBAOGRUwAc8ilVyJogauycnXnMwnB8lM4Gt30naLqhYAhVPr5lYnGspn1nqw6meRjoZr9W34v4NKgYKPdtFR7yeykCQQCuHp/osFemdHK7vmYr04r6fyGrW3wrODEWwORjmKzWUSMdceOwEhQ1J+pjBoEEbxEmOoZ4HLaSIDs0Zoo9d1LbAkEApWzNoTSZSEU4RAhoQjStrMjFBHfpLZtxb+C6Br0Yz6OsyzdRViqXQzsHEUx3XRP7XnHYqi8SdwCY8rRWHAca2QJAOZ743FA/MV6n+Wlw9l/zRiJ8SfDkjojx9c2vxyXnTKVdMYlXCBVzioPDFTI5z/XRfw0vIuCdqo3wCc9FQEIUsQJBAMMmQ5pm+JQc5xXL97CVJDjxRZKxVCJ2utjMDBV/Z3kPuPaBD8XeTvVP8vAHzwNuYVl8ZdR9TUodTPZ2CPVi3oE=";

    //是否压缩 A:不压缩  B: 压缩
    public static String DW_SURVEY_ANSWER_ISZIP = "B";

    @Value("${dwsurvey.web.file-path}")
    public void setWebFilePath(String webFilePath) {
        DWSurveyConfig.DWSURVEY_WEB_FILE_PATH = webFilePath;
    }

    @Value("${dwsurvey.web.site-url}")
    public void setWebSiteUrl(String webSiteUrl) {
        DWSurveyConfig.DWSURVEY_WEB_SITE_URL = webSiteUrl;
    }

    @Value("${dwsurvey.web.router-history}")
    public void setWebRouterHistory(String routerHistory) {
        DWSurveyConfig.DWSURVEY_WEB_ROUTER_HISTORY = routerHistory;
    }


    @Value("${dwsurvey.web.static-type}")
    public void setWebStaticType(String webStaticType) {
        DWSurveyConfig.DWSURVEY_WEB_STATIC_TYPE = webStaticType;
    }

    @Value("${dwsurvey.web.resource-url}")
    public void setWebResourceUrl(String dwsurveyWebResourceUrl) {
        DWSurveyConfig.DWSURVEY_WEB_RESOURCE_URL = dwsurveyWebResourceUrl;
    }

    @Value("${dwsurvey.weixin.open}")
    public void setDwsurveyWeixinOpen(boolean dwsurveyWeixinOpen) {
        DWSURVEY_WEIXIN_OPEN = dwsurveyWeixinOpen;

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

    @Value("${dwsurvey.sms.code-open}")
    public void setDwsurveySmsCodeOpen(String smsCodeOpen) {
        DWSurveyConfig.DWSURVEY_SMS_CODE_OPEN = smsCodeOpen;
    }


    @Value("${dwsurvey.sms.sendcloud.smsUser}")
    public static void setDwsurveySmsScSmsUser(String dwsurveySmsScSmsUser) {
        DWSurveyConfig.DWSURVEY_SMS_SC_SMS_USER = dwsurveySmsScSmsUser;
    }

    @Value("${dwsurvey.sms.sendcloud.smsKey}")
    public static void setDwsurveySmsScSmsKey(String dwsurveySmsScSmsKey) {
        DWSurveyConfig.DWSURVEY_SMS_SC_SMS_KEY = dwsurveySmsScSmsKey;
    }

    @Value("${dwsurvey.dw-open.api.status}")
    public void setDwsurveyOpenApiStatus(String dwsurveyOpenApiStatus) {
        DWSurveyConfig.DWSURVEY_OPEN_API_STATUS = dwsurveyOpenApiStatus;
    }

    @Value("${dwsurvey.dw-open.api.timestamp}")
    public void setDwsurveyApiTimestamp(Integer apiTimestamp) {
        DWSurveyConfig.DWSURVEY_OPEN_API_TIMESTAMP = apiTimestamp;
    }

    @Value("${dwsurvey.version.api}")
    public void setDwsurveyApiUrl(String dwsurveyApiUrl) {
        DWSURVEY_VERSION_API = dwsurveyApiUrl;
    }

    @Value("${dwsurvey.version.name}")
    public void setDwsurveyVersionName(String dwsurveyVersionName) {
        DWSURVEY_VERSION_NAME = dwsurveyVersionName;
    }

    @Value("${dwsurvey.version.name-code}")
    public void setDwsurveyVersionNameCode(String dwsurveyVersionNameCode) {
        DWSURVEY_VERSION_NAME_CODE = dwsurveyVersionNameCode;
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

    @Value("${dwsurvey.web.login-register.login.wx-login}")
    public void setDwsurveyWebLoginWx(boolean dwsurveyWebLoginWx) {
        DWSURVEY_WEB_LOGIN_WX = dwsurveyWebLoginWx;
    }

    @Value("${dwsurvey.web.login-register.login.phone-login}")
    public void setDwsurveyWebLoginPhone(boolean dwsurveyWebLoginPhone) {
        DWSURVEY_WEB_LOGIN_PHONE = dwsurveyWebLoginPhone;
    }

    @Value("${dwsurvey.web.login-register.login.pwd-login}")
    public void setDwsurveyWebLoginPwd(boolean dwsurveyWebLoginPwd) {
        DWSURVEY_WEB_LOGIN_PWD = dwsurveyWebLoginPwd;
    }

    @Value("${dwsurvey.web.login-register.register}")
    public void setDwsurveyWebRegister(boolean dwsurveyWebRegister) {
        DWSURVEY_WEB_REGISTER = dwsurveyWebRegister;
    }
}
