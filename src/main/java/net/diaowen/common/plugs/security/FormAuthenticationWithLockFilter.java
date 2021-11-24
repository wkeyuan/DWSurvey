package net.diaowen.common.plugs.security;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.diaowen.common.base.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import net.diaowen.common.base.service.AccountManager;

public class FormAuthenticationWithLockFilter extends FormAuthenticationFilter {
	Log log=LogFactory.getLog(FormAuthenticationWithLockFilter.class);

    private long  maxLoginAttempts = 10;

    public static ConcurrentHashMap<String, AtomicLong> accountLockMap   = new ConcurrentHashMap<String, AtomicLong>();
    
    private String successAdminUrl;
  
    private String successAdminRole;
    
    
    @Autowired
    protected AccountManager accountManager;
    
    @Override
    public boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
                    + "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }
        if (checkIfAccountLocked(request)) {
            return onLoginFailure(token, new ExcessiveAttemptsException(), request, response);
        } else {
            if (!doLogin(request, response, token)) {
                resetAccountLock(getUsername(request));
                return false;
            }
            return true;
        }
    }

    public boolean checkIfAccountLocked(ServletRequest request) {
        String username = getUsername(request);
        if (username!=null && accountLockMap.get((String) username) != null) {
            long remainLoginAttempts = accountLockMap.get((String) username).get();
            if (remainLoginAttempts <= 0) {
                return true;
            }
        }
        return false;
    }

    
    private boolean doLogin(ServletRequest request, ServletResponse response, AuthenticationToken token)
            throws Exception {
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            
//    		User user = accountManager.findUserByLoginName(getUsername(request));
    		User user = accountManager.findUserByLoginNameOrEmail(getUsername(request));
            user.setLastLoginTime(new Date());
            accountManager.saveUp(user);
            return onLoginSuccess(token, subject, request, response);
        } catch (IncorrectCredentialsException e) {
            decreaseAccountLoginAttempts(request);
            checkIfAccountLocked(request);
            return onLoginFailure(token, e, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    public void decreaseAccountLoginAttempts(ServletRequest request) {
        AtomicLong initValue = new AtomicLong(maxLoginAttempts);
        AtomicLong remainLoginAttempts = accountLockMap.putIfAbsent(getUsername(request), new AtomicLong(maxLoginAttempts));
        if (remainLoginAttempts == null) {
            remainLoginAttempts = initValue;
        }
        remainLoginAttempts.getAndDecrement();
        accountLockMap.put(getUsername(request), remainLoginAttempts);
    }

    public void resetAccountLock(String username) {
        accountLockMap.put(username, new AtomicLong(maxLoginAttempts));
    }

    public void setMaxLoginAttempts(long maxLoginAttempts) {
        this.maxLoginAttempts = maxLoginAttempts;
    }
    
    public void setSuccessAdminUrl(String successAdminUrl) {
		this.successAdminUrl = successAdminUrl;
	}
    
    public void setSuccessAdminRole(String successAdminRole) {
		this.successAdminRole = successAdminRole;
	}
    
    /* 得到某个账号还可以登录次数 */
    public Long getAccountLocked(String username){
   	 long remainLoginAttempts=0;
        if (username!=null && accountLockMap.get((String) username) != null) {
            remainLoginAttempts = accountLockMap.get((String) username).get();
        }
        return remainLoginAttempts+1;
   }
    /* 重写登录失败，加入了失败时还可以重试的次数信息 */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
    		AuthenticationException e, ServletRequest request,
    		ServletResponse response) {
    	request.setAttribute("remainLoginAttempt", getAccountLocked(getUsername(request)));
    	return super.onLoginFailure(token, e, request, response);
    }
    
    
    @Override
    protected String getUsername(ServletRequest request) {
    	// TODO Auto-generated method stub
    	String username = super.getUsername(request);
    	if(username==null){
    		Object temp=request.getAttribute(getUsernameParam());
    		username=temp!=null?temp.toString():null;
    	}
    	return username;
    }
    @Override
    protected String getPassword(ServletRequest request) {
    	String password = super.getPassword(request);
    	if(password==null){
    		Object temp=request.getAttribute(getPasswordParam());
    		password=temp!=null?temp.toString():null;
    	}
    	return password;
    }
    
    @Override
    protected boolean isRememberMe(ServletRequest request) {
//    	 TODO Auto-generated method stub
    	return super.isRememberMe(request);
    }


}
