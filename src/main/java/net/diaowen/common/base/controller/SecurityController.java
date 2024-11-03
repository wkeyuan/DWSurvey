package net.diaowen.common.base.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.HttpStatus;
import net.diaowen.common.plugs.ipaddr.IPService;
import net.diaowen.common.plugs.security.filter.FormAuthenticationWithLockFilter;
import net.diaowen.common.plugs.sms.SmsService;
import net.diaowen.common.plugs.weixin.WeixinMpService;
import net.diaowen.common.utils.RandomUtils;
import net.diaowen.common.utils.security.RSAUtils;
import net.diaowen.dwsurvey.common.RoleCode;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.RandomCode;
import net.diaowen.dwsurvey.service.RandomCodeManager;
import net.diaowen.dwsurvey.service.SysLogManager;
import net.diaowen.dwsurvey.service.UserManager;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/dwsurvey/anon/security")
public class SecurityController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AccountManager accountManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private FormAuthenticationWithLockFilter formAuthFilter;
    @Autowired
    private RandomCodeManager randomCodeManager;
    @Autowired
    private SysLogManager sysLogManager;
    @Autowired
    private WeixinMpService weixinMpService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private IPService ipService;

    @RequestMapping("/logout.do")
    @ResponseBody
    public HttpResult logout(HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (SecurityUtils.getSubject() != null) {
            User curUser = accountManager.getCurUser();
            String userId = "";
            String loginName = "";
            if(curUser!=null){
                userId = curUser.getId();
                loginName = curUser.getLoginName();
            }
            SecurityUtils.getSubject().logout();
        }
        request.getSession().invalidate();
        return HttpResult.SUCCESS();
    }

    @RequestMapping("/login.do")
    @ResponseBody
    public LoginRegisterResult login(HttpServletRequest request, HttpServletResponse response, @RequestParam String type, String userName, String password, String mobile, String captcha, String tag) {
        Subject subject = SecurityUtils.getSubject();
        boolean isAuth = subject.isAuthenticated();
        if(isAuth){
            User user = accountManager.getCurUser();
            if(user!=null){
//                String[] authed = dwUserRoleManager.rolePermsByUserId(user.getId());
                String[] authed = null;
                if("1".equals(user.getId())) authed = new String[]{RoleCode.DWSURVEY_SUPER_ADMIN};
                return LoginRegisterResult.SUCCESS(authed, HttpResult.SUCCESS(user));
            }
        }
        if("weixin".equals(type)){
            //账号密码
            return loginWeixin(request);
        }else if("account".equals(type)){
            //账号密码
            request.setAttribute("username",userName);
            return loginPwd(request,response,userName,password);
        }else{
            request.setAttribute("username",mobile);
            return loginSms(request,response,mobile,captcha,null);
        }
    }
    @RequestMapping("/login-sms.do")
    @ResponseBody
    public LoginRegisterResult loginSms(HttpServletRequest request, HttpServletResponse response, @RequestParam String mobile, @RequestParam String captcha, String tag) {
        Subject subject = SecurityUtils.getSubject();
        boolean isAuth = subject.isAuthenticated();
        String error="";
        String[] authed = null;
        User user = null;
        try{
            if (!DWSurveyConfig.DWSURVEY_WEB_LOGIN_PHONE) return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("未开通短信登录"));
            if (!sysLogManager.ipLoginOk(ipService.getIp(request))) return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("当前登录受限，登录失败！"));
            user = accountManager.findUserByLoginNameOrEmail(mobile);
            if(user!=null){
                //cellphone
                RandomCode lastRc = randomCodeManager.findLastRc(mobile,1,2);
                if(lastRc!=null){
                    if(lastRc.getRdCode().equals(captcha)){

                        Date createDate = lastRc.getCreateDate();
                        Date curDate = new Date();
                        long d = (curDate.getTime()-createDate.getTime())/1000;
                        if(d<=600){
                            try{
                                String loginName = user.getLoginName();
                                String shaPassword = user.getShaPassword();
                                user.setShaPasswordTemp(shaPassword);

                                String plainPassword = loginName +"_DwSF_yu"+ RandomUtils.random(1000000l,9999999l);
                                user.setPlainPassword(plainPassword);
                                accountManager.saveUser(user);
                                request.setAttribute("username",loginName);
                                request.setAttribute("password",plainPassword);
                                UsernamePasswordToken loginToken = new UsernamePasswordToken(loginName, plainPassword);
                                if (!formAuthFilter.checkIfAccountLocked(request)) {
                                    try {
                                        subject.login(loginToken);
                                        formAuthFilter.resetAccountLock(loginName);
                                        isAuth = true;
//                                        authed = dwUserRoleManager.rolePermsByUserId(user.getId());
                                        if("1".equals(user.getId())) authed = new String[]{RoleCode.DWSURVEY_SUPER_ADMIN};
                                        subject.getSession().setAttribute("loginUserName", loginName);
                                        sysLogManager.saveNew("登录成功",loginName,"LOGIN-SMS",user.getId(),1, ipService.getIp(request));
                                    } catch (IncorrectCredentialsException e) {
                                        formAuthFilter.decreaseAccountLoginAttempts(request);
                                        isAuth = false;
                                        error = "IncorrectCredentialsException";
                                    } catch (AuthenticationException e) {
                                        isAuth = false;
                                        error = "AuthenticationException";
                                    }
                                } else {
                                    //ExcessiveAttemptsException超过登录次数
                                    error = "ExcessiveAttemptsException";
                                }

                            }finally{
                                user.setShaPassword(user.getShaPasswordTemp());
                                user.setLastLoginTime(new Date());
                                accountManager.saveUp(user);
                            }

                        }else{
                            error="10001";//短信验证码超时
                        }
                    }else{
                        error="10002";//短信验证码不正确
                    }
                }else{
                    error="10003";//短信验证码未生成
                }
            }else{
                error="10000";//用户不存在
            }
        }catch (Exception e) {
            error = e.getMessage();
            e.printStackTrace();
        }

        if(isAuth){
            Object loginAnSid = request.getSession().getAttribute("loginAnSid");
            if(loginAnSid!=null && "anLogin".equals(tag)){
                request.getSession().removeAttribute("loginAnSid");

            }else{

            }
//            return LoginRegisterResult.SUCCESS(authed);
            return LoginRegisterResult.SUCCESS(authed, HttpResult.SUCCESS(user));
        }else{
            sysLogManager.saveNew("登录失败",mobile,"LOGIN-SMS",null,2, ipService.getIp(request));
        }

        return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG(error));

    }


    @RequestMapping("/login-pwd.do")
    @ResponseBody
    public LoginRegisterResult loginPwd(HttpServletRequest request, HttpServletResponse response, @RequestParam String userName, @RequestParam String password) {
        Subject subject = SecurityUtils.getSubject();
        boolean isAuth = subject.isAuthenticated();
        String error="账号或密码错误";
        String[] authed = null;
        try{
            if (!DWSurveyConfig.DWSURVEY_WEB_LOGIN_PWD) return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("未开通密码登录"));
            if(isAuth) subject.logout();
            if (!sysLogManager.ipLoginOk(ipService.getIp(request))) return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("当前登录受限，登录失败！"));
            if(StringUtils.isNotEmpty(password)) password = RSAUtils.decrypt(password,DWSurveyConfig.DWSURVEY_RSA_PRIVATEKEY);
            if(StringUtils.isNotEmpty(userName)){
                User user = accountManager.findUserByLoginNameOrEmail(userName);
                if(user!=null){
                    UsernamePasswordToken loginToken = new UsernamePasswordToken(userName, password);
                    request.setAttribute("username",userName);
                    if (!formAuthFilter.checkIfAccountLocked(request)) {
                        try {
                            subject.login(loginToken);
                            formAuthFilter.resetAccountLock(userName);
                            subject.getSession().setAttribute("loginUserName", userName);
                            user.setLastLoginTime(new Date());
                            accountManager.saveUp(user);
//                            authed = dwUserRoleManager.rolePermsByUserId(user.getId());
                            if("1".equals(user.getId())) authed = new String[]{RoleCode.DWSURVEY_SUPER_ADMIN};
                            return LoginRegisterResult.SUCCESS(authed,HttpResult.SUCCESS(user));
                        } catch (IncorrectCredentialsException e) {
                            formAuthFilter.decreaseAccountLoginAttempts(request);
                            error = "密码不正确";
                        } catch (AuthenticationException e) {
                            error = "身份认证错误";
                        }
                    } else {
                        // ExcessiveAttemptsException超过登录次数
                        error = "超过登录次数限制";
                    }
                }else{
                    error = "未找到userName对应用户";
                }
            }else{
                error = "登录名userName不能为空";
            }
        }catch (Exception e) {
            e.printStackTrace();
            error = e.getMessage();
        }
        sysLogManager.saveNew("登录失败",userName,"LOGIN-PWD",null,2, ipService.getIp(request));
        return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG(error));
    }

    /**
     * 手机号注册
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/register.do")
    @ResponseBody
    public LoginRegisterResult registerSms(HttpServletRequest request,HttpServletResponse response,String mail, String mobile, String captcha, String password) {
        Subject subject = SecurityUtils.getSubject();
        boolean isAuth = subject.isAuthenticated();
        String error = "";
        User user = null;
        String[] authed = null;
        try{
            Boolean isRegister = DWSurveyConfig.DWSURVEY_WEB_REGISTER;
            if (!isRegister) return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("未开通注册功能"));
            //检查是否开启了注册
            if(isRegister!=null && isRegister){
                String loginName = mail;
                user = new User();
                user.setLoginName(loginName);
                user.setEmail(loginName);
                user.setName(loginName);
                user.setCellphone(mobile);
                user.setPlainPassword(password);
                user.setActivationCode(captcha);
                Object[] results = accountManager.registerSms(user);
                if(results[0]!=null){
                    UsernamePasswordToken loginToken = new UsernamePasswordToken(loginName, password);
                    if (!formAuthFilter.checkIfAccountLocked(request)) {
                        try {
                            subject.login(loginToken);
                            formAuthFilter.resetAccountLock(loginName);
                            isAuth = true;
                            subject.getSession().setAttribute("loginUserName", loginName);
                            if("1".equals(user.getId())) authed = new String[]{RoleCode.DWSURVEY_SUPER_ADMIN};
                        } catch (IncorrectCredentialsException e) {
                            formAuthFilter.decreaseAccountLoginAttempts(request);
                            isAuth = false;
                            error = "IncorrectCredentialsException";
                        } catch (AuthenticationException e) {
                            isAuth = false;
                            error = "AuthenticationException";
                        }
                    } else {
                        //ExcessiveAttemptsException超过登录次数
                        error = "ExcessiveAttemptsException";
                    }
                }else{
                    HttpStatus httpStatus = results[1]!=null?  (HttpStatus) results[1] : null;
                    return LoginRegisterResult.FAILURE(new HttpResult(httpStatus));
                }
            }else{
                return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("没有打开注册功能"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(isAuth){
//            return "redirect:/design/my-survey/list.do";
//            return "redirect:/ic/user/myaccount.do?tag?tag=myaccount";
//            return LoginRegisterResult.SUCCESS("user");
            return LoginRegisterResult.SUCCESS(authed,HttpResult.SUCCESS(user));
        }
//        return "redirect:/register.jsp?error="+error;
        return LoginRegisterResult.FAILURE();
    }


    @RequestMapping("/send-smscode.do")
    @ResponseBody
    public HttpResult sendSms(String phone, Integer eventType) {
        HttpResult httpResult = null;
        try{
            HttpResult<RandomCode> dataResult = randomCodeManager.createSmsCode(phone,1, eventType);
            if(dataResult!=null && dataResult.getResultCode()==200){
                RandomCode randomCode = dataResult.getData();
                logger.debug("phone {} sms code {}", phone, randomCode.getRdCode());
                if("true".equals(DWSurveyConfig.DWSURVEY_SMS_CODE_OPEN)){
                    smsService.sendCode(phone,randomCode.getRdCode());
                    httpResult = HttpResult.SUCCESS();
                } else {
                    httpResult = HttpResult.FAILURE_MSG("短信服务未打开，测试请通过后台查看验证码");
                }
            }else{
                httpResult = dataResult;
            }
        }catch (Exception e){
            e.printStackTrace();
            httpResult = HttpResult.EXCEPTION(e.getMessage());
        }
        return httpResult;
    }


    /**
     * 关注公众号二维码
     * @return
     */
    @RequestMapping("/wx-login-qrcode.do")
    @ResponseBody
    public HttpResult wxLoginQRCode(HttpServletRequest request) {
        try{
            String sessionId = request.getSession().getId();
            if(StringUtils.isNotEmpty(DWSurveyConfig.DWSURVEY_WEIXIN_APP_ID)){
                String ticket = weixinMpService.QRcodeTicket(WeixinMpService.USER_LOGIN_WEIXIN+"_"+sessionId);
                return  HttpResult.SUCCESS(ticket);
            }
            return HttpResult.FAILURE("未配置微信APPID");
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.FAILURE_MSG(e.getMessage());
        }
    }

    /**
     * 关注公众号后，自动登录
     * @return
     */
    @RequestMapping("/login-weixin.do")
    @ResponseBody
    public LoginRegisterResult loginWeixin(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        boolean isAuth = subject.isAuthenticated();
        String error = "";
        String[] authed = null;
        String sessionId = request.getSession().getId();
        User user = null;
        String loginName = null;
        try{
            if (!DWSurveyConfig.DWSURVEY_WEB_LOGIN_WX) return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("未开通微信登录"));
            if (!sysLogManager.ipLoginOk(ipService.getIp(request))) return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("当前登录受限，登录失败！"));
//            String scaneValue = WeixinMpService.USER_LOGIN_WEIXIN+"_"+sessionId;
            if(isAuth) subject.logout();
            user = userManager.findBySessionId(sessionId);
            if(user!=null){
                //进行登录操作
                loginName = user.getLoginName();
                String shaPassword = user.getShaPassword();
                user.setShaPasswordTemp(shaPassword);

                String plainPassword = sessionId +"_DwSF_yu"+ RandomUtils.random(1000000l,9999999l);
                user.setPlainPassword(plainPassword);
                accountManager.saveUser(user);

                request.setAttribute("username",loginName);
                request.setAttribute("password",plainPassword);
                UsernamePasswordToken loginToken = new UsernamePasswordToken(loginName, plainPassword);
                if (!formAuthFilter.checkIfAccountLocked(request)) {
                    try {
                        subject.login(loginToken);
                        formAuthFilter.resetAccountLock(loginName);
                        isAuth = true;
                        subject.getSession().setAttribute("loginUserName", loginName);
//                        authed = dwUserRoleManager.rolePermsByUserId(user.getId());
                        if("1".equals(user.getId())) authed = new String[]{RoleCode.DWSURVEY_SUPER_ADMIN};
                        sysLogManager.saveNew("登录成功",loginName,"LOGIN-WX",user.getId(),1, ipService.getIp(request));
                    } catch (IncorrectCredentialsException e) {
                        formAuthFilter.decreaseAccountLoginAttempts(request);
                        error = "IncorrectCredentialsException";
                    } catch (AuthenticationException e) {
                        error = "AuthenticationException";
                    }
                } else {
                    //ExcessiveAttemptsException超过登录次数
                    error = "ExcessiveAttemptsException";
                }
            }
        }catch (Exception e){
            error = e.getMessage();
            e.printStackTrace();
        }finally{
            if(user!=null){
                user.setShaPassword(user.getShaPasswordTemp());
                user.setLastLoginTime(new Date());
                user.setSessionId("");
                accountManager.saveUp(user);
            }
        }

        if(isAuth){
//            return LoginRegisterResult.SUCCESS(authed);
            return LoginRegisterResult.SUCCESS(authed,HttpResult.SUCCESS(user));
        }else{
            sysLogManager.saveNew("登录失败",sessionId,"LOGIN-WX",null,2, ipService.getIp(request));
        }
        return LoginRegisterResult.RESULT("nosubscribe","weixin");
    }


    @RequestMapping("/checkEmail.do")
    @ResponseBody
    public boolean checkEmailUn(String id,String email) throws Exception{
        User user=userManager.findEmailUn(id,email);
        boolean result=true;
        if(user!=null){
            result=false;
        }
        return result;
    }



    @RequestMapping("/checkLoginNamelUn.do")
    @ResponseBody
    public boolean checkLoginNamelUn(String id,String loginName) throws Exception{
        User user=userManager.findNameUn(id,loginName);
        boolean result=true;
        if(user!=null){
            result=false;
        }
        return result;
    }

    @RequestMapping("/login-props.do")
    @ResponseBody
    public HttpResult loginProps() throws Exception{
        Map<String,Boolean> props = new HashMap<>();
        props.put("loginWx",DWSurveyConfig.DWSURVEY_WEB_LOGIN_WX);
        props.put("loginPhone",DWSurveyConfig.DWSURVEY_WEB_LOGIN_PHONE);
        props.put("loginPwd",DWSurveyConfig.DWSURVEY_WEB_LOGIN_PWD);
        props.put("register",DWSurveyConfig.DWSURVEY_WEB_REGISTER);
        return HttpResult.SUCCESS(props);
    }


}
