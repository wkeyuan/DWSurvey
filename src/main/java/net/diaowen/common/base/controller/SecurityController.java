package net.diaowen.common.base.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.security.filter.FormAuthenticationWithLockFilter;
import net.diaowen.dwsurvey.common.RoleCode;
import net.diaowen.dwsurvey.service.UserManager;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/api/dwsurvey/anon/security")
public class SecurityController {

    @Autowired
    private AccountManager accountManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private FormAuthenticationWithLockFilter formAuthFilter;

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
    public LoginRegisterResult login(HttpServletRequest request, HttpServletResponse response, String userName, String password) {
        Subject subject = SecurityUtils.getSubject();
        boolean isAuth = subject.isAuthenticated();
        if(isAuth){
            User user = accountManager.getCurUser();
            if(user!=null){
                String[] authed = new String[]{};
                if("1".equals(user.getId())) authed = new String[]{RoleCode.DWSURVEY_SUPER_ADMIN};
                return LoginRegisterResult.SUCCESS(authed);
            }
        }
        //账号密码
        request.setAttribute("username",userName);
        return loginPwd(request,response,userName,password);
    }

    @RequestMapping("/login-pwd.do")
    @ResponseBody
    public LoginRegisterResult loginPwd(HttpServletRequest request, HttpServletResponse response, @RequestParam String userName, @RequestParam String password) {
        Subject subject = SecurityUtils.getSubject();
        boolean isAuth = subject.isAuthenticated();
        String error="账号或密码错误";
        String[] authed = null;
        try{
            if(isAuth) subject.logout();
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
                            if("1".equals(user.getId())) authed = new String[]{RoleCode.DWSURVEY_SUPER_ADMIN};
                            return LoginRegisterResult.SUCCESS(authed);
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
        return LoginRegisterResult.FAILURE(HttpResult.FAILURE_MSG(error));
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



}
