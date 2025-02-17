package net.diaowen.dwsurvey.config;

import net.diaowen.common.plugs.security.*;
import net.diaowen.common.plugs.security.cache.ShiroCacheManager;
import net.diaowen.common.plugs.security.token.JwtDefaultSubjectFactory;
import net.diaowen.common.plugs.security.token.jwt.JwtFilter;
import net.diaowen.common.plugs.security.token.jwt.JwtRealm;
import net.diaowen.common.plugs.security.token.redis.RedisTokenFilter;
import net.diaowen.common.plugs.security.token.redis.RedisTokenRealm;
import net.diaowen.dwsurvey.common.PermissionCode;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
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
////@ImportResource(locations= {"classpath:conf/security/applicationContext-shiro.xml"})
public class ShiroConfig {

    /*
     * a. 告诉shiro不要使用默认的DefaultSubject创建对象，因为不能创建Session
     * */
    @Bean
    public SubjectFactory subjectFactory() {
        return new JwtDefaultSubjectFactory();
    }

    @Bean
    public Realm myShiroRealm() {
        Realm customRealm = new JwtRealm();
        return customRealm;
    }

    @Bean
    public Realm redisTokenRealm() {
        Realm redisTokenRealm = new RedisTokenRealm();
        return redisTokenRealm;
    }

    @Bean
    public Realm shiroDbRealm() {
        ShiroDbRealm shiroDbRealm = new ShiroDbRealm();
        return shiroDbRealm;
    }



    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public ShiroCacheManager redisCacheManager() {
        ShiroCacheManager redisCacheManager = new ShiroCacheManager();
        return redisCacheManager;
    }

    //权限管理，配置主要是Realm的管理认证  SecurityManager
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(myShiroRealm());
        securityManager.setRealm(redisTokenRealm());
        /*List<Realm> realmList = new ArrayList<>();
        realmList.add(myShiroRealm());
        realmList.add(shiroDbRealm());
        securityManager.setRealms(realmList);*/
        //禁止Subject的getSession方法
        securityManager.setCacheManager(redisCacheManager());
        securityManager.setSubjectFactory(subjectFactory());
       /* SubjectDAO subjectDAO = securityManager.getSubjectDAO();
        if(subjectDAO instanceof DefaultSubjectDAO){
            System.out.println("subjectDAO--");
            DefaultSubjectDAO defaultSubjectDAO = (DefaultSubjectDAO) subjectDAO;
            SessionStorageEvaluator sessionStorageEvaluator = defaultSubjectDAO.getSessionStorageEvaluator();
            if (sessionStorageEvaluator instanceof DefaultSessionStorageEvaluator){
                System.out.println("sessionStorageEvaluator--");
                DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = (DefaultSessionStorageEvaluator) sessionStorageEvaluator;
                defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
            }
        }*/
        // 关闭 ShiroDAO 功能
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    @Bean
    public JwtFilter jwtFilter() {
        JwtFilter jwtFilter = new JwtFilter();
        return jwtFilter;
    }

    @Bean
    public RedisTokenFilter redisTokenFilter() {
        RedisTokenFilter redisTokenFilter = new RedisTokenFilter();
        return redisTokenFilter;
    }

    @Bean
    public FormAuthenticationWithLockFilter formAuthFilter() {
        FormAuthenticationWithLockFilter formAuthFilter = new FormAuthenticationWithLockFilter();
        formAuthFilter.setMaxLoginAttempts(6);
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
    public RolesOrAuthorizationFilter rolesOrAuthFilter() {
        RolesOrAuthorizationFilter rolesOrAuthorizationFilter = new RolesOrAuthorizationFilter();
        return rolesOrAuthorizationFilter;
    }

    @Bean
    public PermissionsAuthorizationFilter permissionsAuthorizationFilter() {
        PermissionsAuthorizationFilter permissionsAuthorizationFilter = new PermissionsAuthorizationFilter();
        return permissionsAuthorizationFilter;
    }


    @Bean
    public AnonymousFilter anonymousFilter() {
        AnonymousFilter anonymousFilter = new AnonymousFilter();
        return anonymousFilter;
    }

    private String rolesOrPermsValue(String rolesOrPerms){
        return "rolesOrPerms["+ShiroAuthorizationUtils.getQtRoleArrayStr(","+rolesOrPerms)+"]";
    }

    private String rolesOrPermsValue(String[] rolesOrPerms){
        StringBuffer buf = new StringBuffer();
        for (String roleOrPerm:rolesOrPerms) {
            buf.append(",");
            buf.append(roleOrPerm);
        }
        return "rolesOrPerms["+ShiroAuthorizationUtils.getQtRoleArrayStr(buf.toString())+"]";
    }

    private String rolesOrPermsValueHt(String rolesOrPerms){
        return "rolesOrPerms["+ShiroAuthorizationUtils.getHtRoleArrayStr(","+rolesOrPerms)+"]";
    }

    private String rolesOrPermsValueHt(String[] rolesOrPerms){
        StringBuffer buf = new StringBuffer();
        for (String roleOrPerm:rolesOrPerms) {
            buf.append(",");
            buf.append(roleOrPerm);
        }
        return "rolesOrPerms["+ShiroAuthorizationUtils.getHtRoleArrayStr(buf.toString())+"]";
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);


        Map<String, String> map = new LinkedHashMap<>();
        map.put("/jwt/**", "jwtAuthc");
        //登出
        map.put("/logout", "logout");
        //对所有用户认证
        map.put("/api/dwsurvey/anon/**", "anon");
//        map.put("/api/dwsurvey/anon/security/**", "anon");
//        map.put("/api/dwsurvey/anon/jcap/**", "anon");
//        map.put("/api/dwsurvey/anon/response/**", "anon");
        map.put("/login", "authc");
        map.put("/ic/**", "user");
        map.put("/design/**", "user");
        map.put("/da/**", "user");
        map.put("/api/dwsurvey/app/**", "jwtAuthc");

        /**前台权限**/
        //模板权限
        map.put("/api/dwsurvey/app/survey-template.do", rolesOrPermsValue(PermissionCode.QT_TEMPLATER_REF));
        //问卷权限
        map.put("/api/dwsurvey/app/survey/list.do", rolesOrPermsValue(PermissionCode.QT_SURVEY_LIST));
        map.put("/api/dwsurvey/app/survey/add.do", rolesOrPermsValue(PermissionCode.QT_SURVEY_CREATE));//添加权限
        map.put("/api/dwsurvey/app/survey/survey-base-attr.do", rolesOrPermsValue(PermissionCode.QT_SURVEY_EDIT));//修改编辑权限
        map.put("/api/dwsurvey/app/survey/delete.do", rolesOrPermsValue(PermissionCode.QT_SURVEY_DELETE));
        map.put("/api/dwsurvey/app/design/survey-design/surveyAll.do", rolesOrPermsValue(new String[]{PermissionCode.QT_SURVEY_PREVIEW,PermissionCode.HT_SURVEY_PREVIEW}));//设计问卷编辑问卷权限
        map.put("/api/dwsurvey/app/design/survey-design/devSurvey.do", rolesOrPermsValue(new String[]{PermissionCode.QT_SURVEY_DEV}));//设计问卷编辑问卷权限
        map.put("/api/dwsurvey/app/design/**", rolesOrPermsValue(PermissionCode.QT_SURVEY_EDIT));//设计问卷编辑问卷权限

        //结束跳转
        map.put("/api/dwsurvey/app/survey/save-finish-attr.do", rolesOrPermsValue(PermissionCode.QT_PDCOLLECT_FINISH));
        //收集IPS
        map.put("/api/dwsurvey/app/collect/ip/**", rolesOrPermsValue(PermissionCode.QT_PDCOLLECT_IPANSWER));//设计问卷编辑问卷权限
        //人事关联
        map.put("/api/dwsurvey/app/rel/**", rolesOrPermsValue(PermissionCode.QT_PDCOLLECT_USERBIND));//设计问卷编辑问卷权限

        //数据部分
        //设备统计
        map.put("/api/dwsurvey/app/stats/analysis.do", rolesOrPermsValue(new String[]{PermissionCode.QT_SURVEY_DATA_ANALYSIS,PermissionCode.HT_SURVEY_DATA_ANALYSIS}));//
        map.put("/api/dwsurvey/app/stats/dsb.do", rolesOrPermsValue(new String[]{PermissionCode.QT_SURVEY_DATA_ANALYSIS,PermissionCode.HT_SURVEY_DATA_ANALYSIS}));//
        map.put("/api/dwsurvey/app/stats/report.do", rolesOrPermsValue(new String[]{PermissionCode.QT_SURVEY_DATA_REPORT,PermissionCode.HT_SURVEY_DATA_REPORT}));//
        map.put("/api/dwsurvey/app/stats/chart-data.do", rolesOrPermsValue(new String[]{PermissionCode.QT_SURVEY_DATA_REPORT,PermissionCode.HT_SURVEY_DATA_REPORT}));//
        map.put("/api/dwsurvey/app/answer/list.do", rolesOrPermsValue(new String[]{PermissionCode.QT_SURVEY_DATA_ANSWER_LIST,PermissionCode.HT_SURVEY_DATA_ANSWER_LIST}));//
//        map.put("/api/dwsurvey/app/answer/info.do", rolesOrPermsValue(new String[]{PermissionCode.QT_SURVEY_DATA_ANSWER_DETAIL,PermissionCode.HT_SURVEY_DATA_ANSWER_DETAIL}));//
        map.put("/api/dwsurvey/app/answer/delete.do", rolesOrPermsValue(new String[]{PermissionCode.QT_SURVEY_DATA_ANSWER_DELETE,PermissionCode.HT_SURVEY_DATA_ANSWER_DELETE}));//
        map.put("/api/dwsurvey/app/answer/export-xls.do", rolesOrPermsValue(new String[]{PermissionCode.QT_SURVEY_DATA_ANSWER_EXPORT,PermissionCode.HT_SURVEY_DATA_ANSWER_EXPORT}));//
        //公共地区统计
        map.put("/api/dwsurvey/app/stats/province-answer.do", rolesOrPermsValue(new String[]{PermissionCode.QT_SURVEY_DATA_ANALYSIS,PermissionCode.QT_SURVEY_DATA_DISTRICT,PermissionCode.HT_SURVEY_DATA_ANALYSIS,PermissionCode.HT_SURVEY_DATA_DISTRICT,PermissionCode.HT_DASHBOARD_MONITOR}));
        map.put("/api/dwsurvey/app/stats/province-answer-page.do", rolesOrPermsValue(new String[]{PermissionCode.QT_SURVEY_DATA_ANALYSIS,PermissionCode.QT_SURVEY_DATA_DISTRICT,PermissionCode.HT_SURVEY_DATA_ANALYSIS,PermissionCode.HT_SURVEY_DATA_DISTRICT,PermissionCode.HT_DASHBOARD_MONITOR}));

//        map.put("/api/dwsurvey/admin/**", "roles[SUPER_ADMIN]");
        /*** 后台权限 ***/
        map.put("/api/dwsurvey/admin/**", "jwtAuthc");
//        map.put("/api/dwsurvey/admin/**", rolesOrPermsValueHt(PermissionCode.HT_MANAGER_MENU));//必须先具有后台菜单权限才能进一步判断后台权限
        map.put("/api/dwsurvey/admin/analysis.do", rolesOrPermsValueHt(PermissionCode.HT_DASHBOARD_ANALYSIS));
//        map.put("/api/dwsurvey/admin/monitor.do", rolesOrPermsValue(PermissionCode.HT_DASHBOARD_MONITOR));
        //问卷部分
        map.put("/api/dwsurvey/admin/survey/list.do", rolesOrPermsValueHt(new String[]{PermissionCode.HT_SURVEY_MANAGER_DEPT,PermissionCode.HT_SURVEY_LIST}));
        map.put("/api/dwsurvey/admin/survey/delete.do", rolesOrPermsValueHt(new String[]{PermissionCode.HT_SURVEY_DELETE}));
        //问卷分组部分
        map.put("/api/dwsurvey/admin/survey-group/list.do", rolesOrPermsValueHt(PermissionCode.HT_SURVEY_GROUP_LIST));
        map.put("/api/dwsurvey/admin/survey-group/add.do", rolesOrPermsValueHt(PermissionCode.HT_SURVEY_GROUP_CREATE));
        map.put("/api/dwsurvey/admin/survey-group/edit.do", rolesOrPermsValueHt(PermissionCode.HT_SURVEY_GROUP_EDIT));
        map.put("/api/dwsurvey/admin/survey-group/delete.do", rolesOrPermsValueHt(PermissionCode.HT_SURVEY_GROUP_DELETE));
        //模板部分
        map.put("/api/dwsurvey/admin/survey-template/list.do", rolesOrPermsValueHt(PermissionCode.HT_PROTEMPLATER_LIST));
        map.put("/api/dwsurvey/admin/survey-template/add.do", rolesOrPermsValueHt(PermissionCode.HT_PROTEMPLATER_CREATE));
        map.put("/api/dwsurvey/admin/survey-template/edit.do", rolesOrPermsValueHt(PermissionCode.HT_PROTEMPLATER_EDIT));
        map.put("/api/dwsurvey/admin/survey-template/delete.do", rolesOrPermsValueHt(PermissionCode.HT_PROTEMPLATER_DELETE));
        //级联数据部分
        map.put("/api/dwsurvey/admin/cascade/list.do", rolesOrPermsValueHt(PermissionCode.HT_CASCADEDB_LIST));
        map.put("/api/dwsurvey/admin/cascade/cascade-data-list.do", rolesOrPermsValueHt(PermissionCode.HT_CASCADEDB_LIST));
        map.put("/api/dwsurvey/admin/cascade/add.do", rolesOrPermsValueHt(PermissionCode.HT_CASCADEDB_CREATE));
        map.put("/api/dwsurvey/admin/cascade/edit.do", rolesOrPermsValueHt(PermissionCode.HT_CASCADEDB_EDIT));
        map.put("/api/dwsurvey/admin/cascade/delete.do", rolesOrPermsValueHt(PermissionCode.HT_CASCADEDB_DELETE));
        //用户部分
        map.put("/api/dwsurvey/admin/user/list.do", rolesOrPermsValueHt(PermissionCode.HT_USER_LIST));
        map.put("/api/dwsurvey/admin/user/add.do", rolesOrPermsValueHt(PermissionCode.HT_USER_CREATE));
        map.put("/api/dwsurvey/admin/user/edit.do", rolesOrPermsValueHt(PermissionCode.HT_USER_EDIT));
        map.put("/api/dwsurvey/admin/user/delete.do", rolesOrPermsValueHt(PermissionCode.HT_USER_DELETE));
        map.put("/api/dwsurvey/admin/user/user-dept.do", rolesOrPermsValueHt(PermissionCode.HT_USER_EDIT_DEPT));
        map.put("/api/dwsurvey/admin/user/user-role.do", rolesOrPermsValueHt(PermissionCode.HT_USER_EDIT_ROLE));

        map.put("/api/dwsurvey/admin/dept/list.do", rolesOrPermsValueHt(PermissionCode.HT_DEPT_LIST));
        map.put("/api/dwsurvey/admin/dept/add.do", rolesOrPermsValueHt(PermissionCode.HT_DEPT_CREATE));
        map.put("/api/dwsurvey/admin/dept/edit.do", rolesOrPermsValueHt(PermissionCode.HT_DEPT_EDIT));
        map.put("/api/dwsurvey/admin/dept/delete.do", rolesOrPermsValueHt(PermissionCode.HT_DEPT_DELETE));


        map.put("/api/dwsurvey/admin/role/list.do", rolesOrPermsValueHt(PermissionCode.HT_ROLE_LIST));
        map.put("/api/dwsurvey/admin/role/add.do", rolesOrPermsValueHt(PermissionCode.HT_ROLE_CREATE));
        map.put("/api/dwsurvey/admin/role/edit.do", rolesOrPermsValueHt(PermissionCode.HT_ROLE_EDIT));
        map.put("/api/dwsurvey/admin/role/delete.do", rolesOrPermsValueHt(PermissionCode.HT_ROLE_DELETE));
        map.put("/api/dwsurvey/admin/role/role-perm.do", rolesOrPermsValueHt(PermissionCode.HT_ROLE_EDIT_PERM));

        map.put("/api/dwsurvey/admin/perm/perm.do", rolesOrPermsValueHt(PermissionCode.HT_PERM_LIST));
        map.put("/api/dwsurvey/admin/ent-app/list.do", rolesOrPermsValueHt(PermissionCode.HT_APP_LIST));
        map.put("/api/dwsurvey/admin/ent-app/add.do", rolesOrPermsValueHt(PermissionCode.HT_APP_CREATE));
        map.put("/api/dwsurvey/admin/ent-app/edit.do", rolesOrPermsValueHt(PermissionCode.HT_APP_EDIT));
        map.put("/api/dwsurvey/admin/ent-app/delete.do", rolesOrPermsValueHt(PermissionCode.HT_APP_DELETE));
        map.put("/api/dwsurvey/admin/log/*", rolesOrPermsValueHt(PermissionCode.HT_SYSTEMLOG_LIST));

        map.put("/sy/**", "roles[admin]");


        //登录
        shiroFilterFactoryBean.setLoginUrl("/#/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/design/my-survey/list.do");
        //错误页面，认证不通过跳转
//        shiroFilterFactoryBean.setUnauthorizedUrl("/login.do?una=0");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        Map<String, Filter> filters = new HashMap<>();

//        filters.put("jwtAuthc", jwtFilter());
        filters.put("jwtAuthc", redisTokenFilter());
        filters.put("authc", formAuthFilter());
        filters.put("user", userAuthFilter());
        filters.put("roles", rolesAuthFilter());
        filters.put("rolesOrPerms", rolesOrAuthFilter());
        filters.put("perms", permissionsAuthorizationFilter());
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
