package net.diaowen.dwsurvey.config;

import net.diaowen.common.plugs.security.FormAuthenticationWithLockFilter;
import net.diaowen.common.plugs.security.MyDefaultWebSecurityManager;
import net.diaowen.common.plugs.security.ShiroDbRealm;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
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

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> map = new HashMap<>();
        //登出
        map.put("/logout", "logout");
        //对所有用户认证
        map.put("/login", "authc");
        map.put("/ic/**", "user");
        map.put("/design/**", "user");
        map.put("/da/**", "user");
        map.put("/up/**", "user");
        map.put("/sy/**", "roles[admin]");

        //登录
        shiroFilterFactoryBean.setLoginUrl("/login.do");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/design/my-survey/list.do");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/login.do?una=0");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("authc", formAuthFilter());
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
