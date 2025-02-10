package net.diaowen.dwsurvey.service.impl.answer;

import com.octo.captcha.service.image.ImageCaptchaService;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.ipaddr.IPLocation;
import net.diaowen.common.plugs.ipaddr.IPService;
import net.diaowen.common.plugs.weixin.WeixinMpService;
import net.diaowen.common.utils.CookieUtils;
import net.diaowen.common.utils.HttpRequestDeviceUtils;
import net.diaowen.common.utils.IpUtils;
import net.diaowen.common.utils.NumberUtils;
import net.diaowen.dwsurvey.common.AnswerCheckData;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.SurveyAnswer;
import net.diaowen.dwsurvey.entity.SurveyDetail;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.WxUserinfo;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.WxUserinfoManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service
public class SubmitSurveyAnswerCheckService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SurveyDirectoryManager directoryManager;
    @Autowired
    private AccountManager accountManager;

    public boolean isAnswerPerm(HttpServletRequest request, SurveyAnswer answer, SurveyDirectory survey, String perm) {
        boolean isPerm = false;
        if(!isPerm){
            User curUser = accountManager.getCurUser();
            if(curUser!=null){
                if(curUser.getId().equals(answer.getUserId())){
                    isPerm = true;//自己填的
                }else{
                    String surveyId = answer.getSurveyId();
                    SurveyDirectory surveyDirectory = directoryManager.getSurveyByUser(surveyId, curUser.getId());
                    if (surveyDirectory != null) {
                        isPerm = true;//此问卷管理员
                    }
                }
            }
        }
        return isPerm;
    }

}
