package net.diaowen.dwsurvey.config;

import net.diaowen.common.plugs.security.*;
import net.diaowen.common.plugs.security.filter.FormAuthenticationWithLockFilter;
import net.diaowen.common.plugs.security.filter.MyRolesAuthorizationFilter;
import net.diaowen.common.plugs.security.filter.MyUserFilter;
import net.diaowen.dwsurvey.common.RoleCode;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
//@ImportResource(locations= {"classpath:conf/security/applicationContext-shiro.xml"})
public class ShiroConfig {



    @Bean
    public ShiroDbRealm myShiroRealm() {
        ShiroDbRealm customRealm = new ShiroDbRealm();
        return customRealm;
    }

    //权限管理，配置主要是Realm的管理认证  SecurityManager
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public FormAuthenticationWithLockFilter formAuthFilter() {
        FormAuthenticationWithLockFilter formAuthFilter = new FormAuthenticationWithLockFilter();
        formAuthFilter.setMaxLoginAttempts(100);
        formAuthFilter.setSuccessAdminUrl("/design/my-survey/list.do");
        formAuthFilter.setSuccessAdminRole("admin");
        formAuthFilter.setRememberMeParam("rememberMe");
        return formAuthFilter;
    }


    @Bean
    public UserFilter userAuthFilter() {
        MyUserFilter formAuthFilter = new MyUserFilter();
        return formAuthFilter;
    }

    @Bean
    public MyRolesAuthorizationFilter rolesAuthFilter() {
        MyRolesAuthorizationFilter rolesAuthorizationFilter = new MyRolesAuthorizationFilter();
        return rolesAuthorizationFilter;
    }

    @Bean
    public AnonymousFilter anonymousFilter() {
        AnonymousFilter anonymousFilter = new AnonymousFilter();
        return anonymousFilter;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> map = new LinkedHashMap<>();
        //登出
        map.put("/logout", "logout");
        //对所有用户认证
        map.put("/api/dwsurvey/anon/**", "anon");
        map.put("/login", "authc");
        map.put("/ic/**", "user");
        map.put("/design/**", "user");
        map.put("/da/**", "user");
        map.put("/api/dwsurvey/app/**", "user");
        map.put("/api/dwsurvey/admin/**", "roles["+ RoleCode.DWSURVEY_SUPER_ADMIN +"]");
        //登录
//        shiroFilterFactoryBean.setLoginUrl("/login.do");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/design/my-survey/list.do");
        //错误页面，认证不通过跳转
//        shiroFilterFactoryBean.setUnauthorizedUrl("/login.do?una=0");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("authc", formAuthFilter());
        filters.put("user", userAuthFilter());
        filters.put("roles", rolesAuthFilter());
//        filters.put("anon", anonymousFilter());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie rememberMeCookie = new SimpleCookie("rememberMe");
        rememberMeCookie.setHttpOnly(true);
        rememberMeCookie.setMaxAge(2592000);
        return rememberMeCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(rememberMeCookie());
        return rememberMeManager;
    }


}
