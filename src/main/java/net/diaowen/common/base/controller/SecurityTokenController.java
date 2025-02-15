package net.diaowen.common.base.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.cache.redis.RedisManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.HttpStatus;
import net.diaowen.common.plugs.ipaddr.IPService;
import net.diaowen.common.plugs.security.FormAuthenticationWithLockFilter;
import net.diaowen.common.plugs.security.token.JwtUtils;
import net.diaowen.common.plugs.sms.SmsService;
import net.diaowen.common.plugs.weixin.WeixinMpService;
import net.diaowen.common.utils.security.DigestUtils;
import net.diaowen.common.utils.security.RSAUtils;
import net.diaowen.dwsurvey.common.RoleCode;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.RandomCode;
import net.diaowen.dwsurvey.service.RandomCodeManager;
import net.diaowen.dwsurvey.service.SysLogManager;
import net.diaowen.dwsurvey.service.UserManager;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/api/dwsurvey/anon/security-token")
public class SecurityTokenController {

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
    private RedisManager redisManager;
    @Autowired
    private IPService ipService;

    @RequestMapping("/logout.do")
    @ResponseBody
    public HttpResult logout(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String userId = "";
        String loginName = "";
//        SecurityUtils.getSubject().logout();
        String tokenStr = getRequestToken(request);
        Object redisTokenObj = redisManager.get(JwtUtils.REDIS_STORE_PREFIX+tokenStr);
        if(redisTokenObj!=null) {
            User user = (User) redisTokenObj;
            if (user != null) {
                userId = user.getId();
                loginName = user.getLoginName();
                if(StringUtils.isNotEmpty(tokenStr)){
                    redisManager.deleteKey(JwtUtils.REDIS_STORE_PREFIX+tokenStr);
                }
                SecurityUtils.getSubject().logout();
                sysLogManager.saveNew("退出登录",loginName,"LOGOUT",userId,1, ipService.getIp(request));
            }
        }
        return HttpResult.SUCCESS();
    }

    @RequestMapping("/login.do")
    @ResponseBody
    public LoginRegisterResult login(HttpServletRequest request, HttpServletResponse response, @RequestParam String type, String userName, String password, String mobile, String captcha, String tag) {
        Subject subject = SecurityUtils.getSubject();
        boolean isAuth = subject.isAuthenticated();
        String tokenStr = getRequestToken(request);
        if(StringUtils.isNotEmpty(tokenStr)){
            Object redisTokenObj = redisManager.get(JwtUtils.REDIS_STORE_PREFIX+tokenStr);
            if(redisTokenObj!=null){
                User user = (User)redisTokenObj;
                if(user!=null){
                    redisManager.expireKey(JwtUtils.REDIS_STORE_PREFIX+tokenStr,30*60, TimeUnit.SECONDS);
                    String[] authed = {};
                    if("1".equals(user.getId())) authed = new String[]{RoleCode.DWSURVEY_SUPER_ADMIN};
//                    String[] authed = dwUserRoleManager.rolePermsByUserId(user.getId());
                    return LoginRegisterResult.SUCCESS(tokenStr, authed, HttpResult.SUCCESS(user));
                }
            }
        }
        if("weixin".equals(type)){
            //账号密码
            return loginWeixin(request);
        }else if("account".equals(type)){
            //账号密码
            request.setAttribute("username",userName);
            return loginPwd(request,response,userName,password,null);
//            return loginPwdJwt(request,response,userName,password,null);
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
                            sysLogManager.saveNew("登录成功",user.getLoginName(),"LOGIN-SMS",user.getId(),1, ipService.getIp(request));
                            return loginJwtToken(user);
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

        if(tag!=null && "anLogin".equals(tag)){
            return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG(error));
        }
        return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG(error));

    }

    @RequestMapping("/login-pwd.do")
    @ResponseBody
    public LoginRegisterResult loginPwd(HttpServletRequest request, HttpServletResponse response, @RequestParam String userName, @RequestParam String password, String tag) {
        logger.debug("password en {}",password);
        if(StringUtils.isNotEmpty(password)) {
            try {
                password = RSAUtils.decrypt(password,DWSurveyConfig.DWSURVEY_RSA_PRIVATEKEY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.debug("password de {}",password);
        return loginPwdJwt(request,response,userName,password,tag);
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
        User user = null;
        try{
            Boolean isRegister = DWSurveyConfig.DWSURVEY_WEB_REGISTER;
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
                    return loginJwtToken(user);
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
                if("true".equals(DWSurveyConfig.DWSURVEY_SMS_CODE_OPEN)){
                    smsService.sendCode(phone,randomCode.getRdCode());
                    httpResult = HttpResult.SUCCESS();
                }else{
                    logger.info("randomCode {}", randomCode.getRdCode());
                    httpResult = HttpResult.FAILURE("服务未打开");
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
            if (!DWSurveyConfig.DWSURVEY_WEIXIN_OPEN) return HttpResult.SUCCESS("微信服务未开启");
            String sessionId = request.getSession().getId();
            if(StringUtils.isNotEmpty(DWSurveyConfig.DWSURVEY_WEIXIN_APP_ID)){
                String ticket = weixinMpService.QRcodeTicket(WeixinMpService.USER_LOGIN_WEIXIN+"_"+sessionId);
                return  HttpResult.SUCCESS(new String[]{ticket,sessionId});
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
        boolean isAuth = false;
        String error = "";
        String[] authed = null;
//        String sessionId = request.getSession().getId();
        String sessionId = request.getParameter("dwSessionId");
        User user = null;
        String loginName = null;
        try{
            if (!DWSurveyConfig.DWSURVEY_WEB_LOGIN_WX) return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("未开通微信登录"));
            if (!sysLogManager.ipLoginOk(ipService.getIp(request))) return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("当前登录受限，登录失败！"));
//            String scaneValue = WeixinMpService.USER_LOGIN_WEIXIN+"_"+sessionId;
            user = userManager.findBySessionId(sessionId);
            if(user!=null){
                //进行登录操作
                sysLogManager.saveNew("登录成功",user.getLoginName(),"LOGIN-WX",user.getId(),1, ipService.getIp(request));
                return loginJwtToken(user);
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


    @RequestMapping("/login-pwd-jwt.do")
    public LoginRegisterResult loginPwdJwt(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true) String userName, @RequestParam String password, String tag) {
        if (!sysLogManager.ipLoginOk(ipService.getIp(request))) return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("当前登录受限，登录失败！"));
        if(StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password)){
            User user = accountManager.findUserByLoginNameOrEmail(userName);
            if(user==null) return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("未找到对应的用户"));
            String shaPassword = DigestUtils.sha1Hex(password);
            if(shaPassword.equals(user.getShaPassword())){
                //验证通过
                sysLogManager.saveNew("登录成功",userName,"LOGIN-PWD",user.getId(),1, ipService.getIp(request));
                return loginJwtToken(user);
            }
        }
        sysLogManager.saveNew("登录失败",userName,"LOGIN-PWD",null,2, ipService.getIp(request));
        return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("用户名或密码错误"));
    }

    private LoginRegisterResult loginJwtToken(User user) {
        if(user==null) return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG("未找到对应的用户"));
        user.setLastLoginTime(new Date());
        accountManager.saveUp(user);
        String[] authed = {};
        if("1".equals(user.getId())) authed = new String[]{RoleCode.DWSURVEY_SUPER_ADMIN};
//        String[] authed = dwUserRoleManager.rolePermsByUserId(user.getId());
        String userName = user.getLoginName();
        String jwtToken = buildToken(userName, user);
        redisManager.set(JwtUtils.REDIS_STORE_PREFIX+jwtToken,user,30*60, TimeUnit.SECONDS);
        return LoginRegisterResult.SUCCESS(jwtToken,authed,HttpResult.SUCCESS(user));
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


    private String buildToken(String userName, User user) {
        //生成token
        JwtUtils jwtUtil = new JwtUtils();
        Map<String, Object> chaim = new HashMap<>();
        chaim.put("username", userName);
        chaim.put("name", user.getName());
        String jwtToken = jwtUtil.createToken(userName, 5 * 60 * 1000, chaim);
        return jwtToken;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader(JwtUtils.TOKEN);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            return httpRequest.getParameter(JwtUtils.TOKEN);
        }
        if (StringUtils.isBlank(token)) {
            // 从 cookie 获取 token
            Cookie[] cookies = httpRequest.getCookies();
            if (null == cookies || cookies.length == 0) {
                return null;
            }
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(JwtUtils.TOKEN)) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }

}
