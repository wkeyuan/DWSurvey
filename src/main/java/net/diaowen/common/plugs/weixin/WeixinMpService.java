package net.diaowen.common.plugs.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.dao.SuperHttpDao;
import net.diaowen.common.plugs.httpclient.HttpClientUtils;
import net.diaowen.common.plugs.weixin.aes.AccessToken;
import net.diaowen.common.plugs.weixin.aes.TemplateData;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.WxUserinfo;
import net.diaowen.dwsurvey.service.WxUserinfoManager;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by keyuan on 2019/8/5.
 */
@Service
public class WeixinMpService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static String USER_BIND_WEIXIN = "UserBindWx";
    public static String USER_LOGIN_WEIXIN = "UserLoginWx";
    private String STATE = "189922";
//    private static AccessToken accessToken=new AccessToken();
//    private static String jsApiTicket = "O3SMpm8bG7kJnF36aXbe8wa1RPCPMdPIjJ27MLyeDapZjX0biZEWvFiEvxIu-r0HTPCMdk7l30zFFwkUmTTK-g";
    private static AccessToken accessToken = null;
    private static String jsApiTicket = null;
    @Autowired
    private SuperHttpDao superHttpDao;
    @Autowired
    private WxUserinfoManager wxUserinfoManager;
    @Autowired
    private TaskExecutor taskExecutor;

    public String authorizeCodeUrl(String REDIRECT_URI,Integer scope){
        try {
            REDIRECT_URI = URLEncoder.encode(REDIRECT_URI,"UTF-8");
            String SCOPE = "snsapi_base";
            if(scope==2){
                SCOPE = "snsapi_userinfo";
            }

            String authorizeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ DWSurveyConfig.DWSURVEY_WEIXIN_APP_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code&scope="+SCOPE+"&state="+STATE+"#wechat_redirect ";
            return authorizeUrl;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WxUserinfo accessToken(String weixinCode) {
        //获取code查询有无记录，有直接返回。
        WxUserinfo wxUserinfo = wxUserinfoManager.findByWxCode(weixinCode);
        if(wxUserinfo!=null){
            return wxUserinfo;
        }
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+DWSurveyConfig.DWSURVEY_WEIXIN_APP_ID+"&secret="+DWSurveyConfig.DWSURVEY_WEIXIN_APP_SECRET+"&code="+weixinCode+"&grant_type=authorization_code";
        try {
//            HttpPost httpPost = HttpClientUtils.buildHttpPost(accessTokenUrl);
            URI uri = new URI(accessTokenUrl);
            HttpPost httpPost = new HttpPost(uri);
            String result = superHttpDao.doPost(httpPost);
            if(result!=null){
//                JSONObject jsonObject = JSONObject.fromObject(result);
                JSONObject jsonObject = JSON.parseObject(result);
                if(jsonObject.get("errcode")==null){
                    String access_token = jsonObject.getString("access_token");;
                    String openid = "";
                    if(jsonObject.get("openid")!=null){
                        openid = jsonObject.getString("openid");;
                    }
                    String expires_in = jsonObject.getString("expires_in");
                    String refresh_token = jsonObject.getString("refresh_token");
                    String scope = jsonObject.getString("scope");
                    //生成wxUserinfo对象

                    wxUserinfo = new WxUserinfo();
                    wxUserinfo.setOpenid(openid);
                    wxUserinfo.setAccess_token(access_token);
                    wxUserinfo.setExpires_in(expires_in);
                    wxUserinfo.setRefresh_token(refresh_token);
                    wxUserinfo.setAccessTokenCode(weixinCode);
                    Integer scopeInt = 0;
                    if("snsapi_base".equals(scope)){
                        scopeInt=1;
                    }else if("snsapi_userinfo".equals(scope)){
                        scopeInt=2;
                    }
                    wxUserinfo.setScope(scopeInt);
                    wxUserinfo.setTokenDate(new Date());
                    return wxUserinfoManager.saveAndResult(wxUserinfo);
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public WxUserinfo userinfo(String inOpenid,String accessToken) {
        WxUserinfo wxUserinfo = wxUserinfoManager.findByOpenid(inOpenid);
        if(wxUserinfo!=null){
            if(wxUserinfo.getNickname()!=null && wxUserinfo.getUserinfoStatus()==1){
//                return wxUserinfo;
            }
        }
        String accessTokenUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+inOpenid+"&lang=zh_CN";
        try {
//            HttpPost httpPost = HttpClientUtils.buildHttpPost(accessTokenUrl);
            URI uri = new URI(accessTokenUrl);
//            HttpPost httpPost = new HttpPost(uri);
            HttpGet httpGet = new HttpGet(uri);
            String result = superHttpDao.doGet(httpGet);
            if(result!=null){
//                JSONObject jsonObject = JSONObject.fromObject(result);
                JSONObject jsonObject = JSON.parseObject(result);
                if(jsonObject.get("errcode")==null){
                    //{"openid":"oYWFn0iym6zTkMKwGw_SwK3Yjo0U","nickname":"越过山丘","sex":0,"language":"zh_CN","city":"","province":"","country":"","headimgurl":"https:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/Dgt3UtqtHO3FHXcRFItpGfTWP5qxiaED3KMXMIylhFjicQHCSfH6Gbkt8GKzNNz2j4Rkx6nlAb7XfwEY9ibhnfJDw\/132","privilege":[]}
                    String openid = jsonObject.getString("openid");;
                    String nickname = jsonObject.getString("nickname");
                    String headimgurl = jsonObject.getString("headimgurl");
                    String privilege = null;
                    /*
                    if(jsonObject.has("privilege")){
                        privilege = jsonObject.getString("privilege");
                    }
                    */
                    String unionid =  null;
                    if(jsonObject.get("unionid")!=null){
                        unionid =  jsonObject.getString("unionid");
                    }
                    wxUserinfo = new WxUserinfo();
                    wxUserinfo.setOpenid(openid);
                    wxUserinfo.setNickname(nickname);


                    if(jsonObject.get("sex")!=null){
                        int sex = jsonObject.getIntValue("sex");
                        wxUserinfo.setSex(sex);
                    }
                    if(jsonObject.get("province")!=null){
                        String province	 = jsonObject.getString("province");
                        wxUserinfo.setProvince(province);
                    }

                    if(jsonObject.get("city")!=null){
                        String city	 = jsonObject.getString("city");
                        wxUserinfo.setCity(city);
                    }

                    if(jsonObject.get("country")!=null){
                        String country	 = jsonObject.getString("country");
                        wxUserinfo.setCountry(country);
                    }

                    wxUserinfo.setHeadimgurl(headimgurl);
                    wxUserinfo.setPrivilege(privilege);
                    wxUserinfo.setUnionid(unionid);
                    wxUserinfo.setUserinfoStatus(1);
                    return wxUserinfoManager.saveAndResult(wxUserinfo);
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getAccessToken() {
        if(accessToken==null){
            AccessToken resultAccessToken = refAccessToken();
            if(resultAccessToken!=null){
                accessToken=resultAccessToken;
            }
        }
        if(accessToken!=null){
            return accessToken.getAccessToken();
        }
        return null;
    }

    public void taskRefAccessToken() {
        if (DWSurveyConfig.DWSURVEY_WEIXIN_OPEN) {
            AccessToken resultAccessToken = refAccessToken();
            if(resultAccessToken!=null){
                accessToken = resultAccessToken;
                jsApiTicket = refJsApiTicket();
            }
        }
    }

    /**
     * 需要调用定时器方法
     * @return
     */
    public AccessToken refAccessToken() {
        if(DWSurveyConfig.DWSURVEY_WEIXIN_OPEN) {
            String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+DWSurveyConfig.DWSURVEY_WEIXIN_APP_ID+"&secret="+DWSurveyConfig.DWSURVEY_WEIXIN_APP_SECRET;
            try {
                HttpPost httpPost = HttpClientUtils.buildHttpPost(accessTokenUrl);
//            String postJson = "{\"expire_seconds\": 900, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+userId+"\"}}}";
//            HttpPost httpPost = HttpClientUtils.buildHttpPostFormJson(accessTokenUrl,postJson);
                String result = superHttpDao.doPost(httpPost);
                logger.info("token result {}", result);
                if(result!=null) {
//                    JSONObject jsonObject = JSONObject.fromObject(result);
                    JSONObject jsonObject = JSON.parseObject(result);
                    if(jsonObject.get("errcode")==null){
                        //{"access_token":"ACCESS_TOKEN","expires_in":7200}
                        String access_token = jsonObject.getString("access_token");
                        int expires_in = jsonObject.getIntValue("expires_in");
                        AccessToken accessToken = new AccessToken();
                        accessToken.setAccessToken(access_token);
                        accessToken.setExpireSecond(expires_in);
                        return accessToken;
                    }else{
//                    42001
                        logger.error("refAccessToken 失败 {}",result);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public WxUserinfo getUserInfo(String inOpenid) {
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+getAccessToken()+"&openid="+inOpenid+"&lang=zh_CN";
        try {
//            HttpPost httpPost = HttpClientUtils.buildHttpPost(accessTokenUrl);
            URI uri = new URI(accessTokenUrl);
            HttpPost httpPost = new HttpPost(uri);
            String result = superHttpDao.doPost(httpPost);
            if(result!=null){

               /* {
                    "subscribe": 1,
                        "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
                        "nickname": "Band",
                        "sex": 1,
                        "language": "zh_CN",
                        "city": "广州",
                        "province": "广东",
                        "country": "中国",
                        "headimgurl":"http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
                        "subscribe_time": 1382694957,
                        "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
                    "remark": "",
                        "groupid": 0,
                        "tagid_list":[128,2],
                    "subscribe_scene": "ADD_SCENE_QR_CODE",
                        "qr_scene": 98765,
                        "qr_scene_str": ""
                }
                */
//                JSONObject jsonObject = JSONObject.fromObject(result);
                JSONObject jsonObject = JSON.parseObject(result);
                if(jsonObject.get("errcode")==null){
                    String openid = jsonObject.getString("openid");;
                    String nickname = jsonObject.getString("nickname");
                    String headimgurl = jsonObject.getString("headimgurl");
                    String privilege = null;
                    /*
                    if(jsonObject.has("privilege")){
                        privilege = jsonObject.getString("privilege");
                    }
                    */
                    String unionid =  null;
                    if(jsonObject.get("unionid")!=null){
                        unionid =  jsonObject.getString("unionid");
                    }
                    WxUserinfo wxUserinfo = new WxUserinfo();
                    wxUserinfo.setOpenid(openid);
                    wxUserinfo.setNickname(nickname);

                    if(jsonObject.get("sex")!=null){
                        int sex = jsonObject.getIntValue("sex");
                        wxUserinfo.setSex(sex);
                    }
                    if(jsonObject.get("province")!=null){
                        String province	 = jsonObject.getString("province");
                        wxUserinfo.setProvince(province);
                    }

                    if(jsonObject.get("city")!=null){
                        String city	 = jsonObject.getString("city");
                        wxUserinfo.setCity(city);
                    }

                    if(jsonObject.get("country")!=null){
                        String country	 = jsonObject.getString("country");
                        wxUserinfo.setCountry(country);
                    }

                    wxUserinfo.setHeadimgurl(headimgurl);
                    wxUserinfo.setPrivilege(privilege);
                    wxUserinfo.setUnionid(unionid);
                    wxUserinfo.setUserinfoStatus(1);
                    return wxUserinfoManager.saveAndResult(wxUserinfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendLoginMsg(User user,String ipAddr){
        String wxOpenId = user.getWxOpenId();
        if(StringUtils.isNotEmpty(wxOpenId)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            TemplateData templateData = TemplateData.New();
            templateData.setTouser(wxOpenId);
            templateData.setTemplate_id("ygyUQ13JdYPMk9dV9_VZwyKzIRny38Qd82iLJ5AyIgQ");
            templateData.setTopcolor("#07c160");
            templateData.add("first","您好，您的帐号 "+user.getLoginName()+"被登录","#353535");
            templateData.add("time",simpleDateFormat.format(user.getLastLoginTime()),"#353535");
            templateData.add("ip",ipAddr,"#353535");
            templateData.add("reason","备注：如果本次登录不是您本人所为，说明您的帐号已经被盗！为减少您的损失，请联系我们处理。","#353535");
            sendTemplateMsg(templateData);
        }
    }

    public void sendTemplateMsg(TemplateData templateData) {

        taskExecutor.execute(new Runnable() {
            public void run() {
                String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+getAccessToken();
                try {
                    String postJson = templateData.build();
                    HttpPost httpPost = HttpClientUtils.buildHttpPostFormJson(accessTokenUrl,postJson);
                    String result = superHttpDao.doPost(httpPost);
                    if(result!=null){
//                        JSONObject jsonObject = JSONObject.fromObject(result);
                        JSONObject jsonObject = JSON.parseObject(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        });

    }


    /**
     * 生成带参数场景微信二维码
     * @param scene_str
     * @return
     */
    public String QRcodeTicket(String scene_str) {
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+getAccessToken();
        try {
//            HttpPost httpPost = HttpClientUtils.buildHttpPost(accessTokenUrl);
//            String scene_str = USER_BIND_WEIXIN+"_"+userId;
            String postJson = "{\"expire_seconds\": 900, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+scene_str+"\"}}}";
            HttpPost httpPost = HttpClientUtils.buildHttpPostFormJson(accessTokenUrl,postJson);
            String result = superHttpDao.doPost(httpPost);
            if(result!=null) {
                logger.debug("QRcodeTicket result {}",result);
//                JSONObject jsonObject = JSONObject.fromObject(result);
                JSONObject jsonObject = JSON.parseObject(result);
                if(jsonObject.get("errcode")==null){
                    //                {"ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==","expire_seconds":60,"url":"http://weixin.qq.com/q/kZgfwMTm72WWPkovabbI"}
                    String ticket = jsonObject.getString("ticket");
                    int expire_seconds = jsonObject.getIntValue("expire_seconds");
                    String url = jsonObject.getString("url");
                    return ticket;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public String getJsApiTicket() {
        if(jsApiTicket==null){
            jsApiTicket = refJsApiTicket();
        }
        return jsApiTicket;
    }

    /**
     * https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
     */
    public String refJsApiTicket() {
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+getAccessToken()+"&type=jsapi";
        try {
//            HttpPost httpPost = HttpClientUtils.buildHttpPost(accessTokenUrl);
//            String scene_str = USER_BIND_WEIXIN+"_"+userId;
            HttpGet httpGet = new HttpGet(accessTokenUrl);
            String result = superHttpDao.doGet(httpGet);
            if(result!=null) {
//                JSONObject jsonObject = JSONObject.fromObject(result);
                JSONObject jsonObject = JSON.parseObject(result);
                logger.debug("refJsApiTicket result {}", result);
                if(jsonObject.getString("ticket")!=null){
                    //                {"ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==","expire_seconds":60,"url":"http://weixin.qq.com/q/kZgfwMTm72WWPkovabbI"}
                    String ticket = jsonObject.getString("ticket");
//                    int expire_seconds = jsonObject.getIntValue("expire_seconds");
//                    String url = jsonObject.getString("url");
                    return ticket;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

}
