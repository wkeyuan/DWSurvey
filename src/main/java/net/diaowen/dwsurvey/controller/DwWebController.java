package net.diaowen.dwsurvey.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.HttpStatus;
import net.diaowen.dwsurvey.common.FooterInfo;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/api/dwsurvey/anon/web")
public class DwWebController {

    @Autowired
    private AccountManager accountManager;
    /**
     * 获取问卷详情
     * @return
     */
    @RequestMapping(value = "/footer-info.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<FooterInfo> footerInfo() {
        try{
            FooterInfo footerInfo = new FooterInfo();
            footerInfo.setVersionInfo(DWSurveyConfig.DWSURVEY_VERSION_INFO);
            footerInfo.setVersionNumber(DWSurveyConfig.DWSURVEY_VERSION_NUMBER);
            footerInfo.setVersionBuilt(DWSurveyConfig.DWSURVEY_VERSION_BUILT);
            footerInfo.setSiteName(DWSurveyConfig.DWSURVEY_WEB_INFO_SITE_NAME);
            footerInfo.setSiteUrl(DWSurveyConfig.DWSURVEY_WEB_INFO_SITE_URL);
            footerInfo.setSiteIcp(DWSurveyConfig.DWSURVEY_WEB_INFO_SITE_ICP);
            footerInfo.setSiteMail(DWSurveyConfig.DWSURVEY_WEB_INFO_SITE_MAIL);
            footerInfo.setSitePhone(DWSurveyConfig.DWSURVEY_WEB_INFO_SITE_PHONE);
            footerInfo.setYears("2012-"+new SimpleDateFormat("yyyy").format(new Date()));
            User user = accountManager.getCurUser();
            if(user!=null){
                //登录用户返回带版本号
                return HttpResult.SUCCESS(footerInfo);
            }else{
                //非登录用户返回不带版本号
                footerInfo.setVersionNumber("");
                return HttpResult.SUCCESS(footerInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.FAILURE();
    }


}
