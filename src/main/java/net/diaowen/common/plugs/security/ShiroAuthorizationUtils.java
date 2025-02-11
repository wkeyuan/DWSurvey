package net.diaowen.common.plugs.security;

import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.dwsurvey.common.PermissionCode;
import net.diaowen.dwsurvey.common.RoleCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


public class ShiroAuthorizationUtils {

    public static HttpResult isSurveyRoleOrPerm(String userId,String surveyUserId,String roleOrPerm) {
        if(userId!=null){
            if(!userId.equals(surveyUserId)){
                //如果不是创建者，则判断是否是管理员
                //不是创建者，则查看是否超管、企业管理员、部门管理、拥有系统或部门管理权限的用户，需要分不同情况去判断
                HttpResult httpResult = ShiroAuthorizationUtils.isSurveyDataSysAdminHttpResult(PermissionCode.HT_SURVEY_MANAGER_SYS);
                if (httpResult != null) return httpResult;
                //则判断是否拥有部门数据权限
                httpResult = ShiroAuthorizationUtils.isSurveyDataDeptAdminHttpResult(PermissionCode.HT_SURVEY_MANAGER_DEPT);
                if (httpResult == null) {
                    //拥有部门问卷权限，则判断这份问卷是否属于这个部门
                    //根据 surveyUserId, 查找用户所属的属门与管理员是否在同一个部门
                    //select deptId from t_user_dept where surveyUserId = '222'
                    //select deptId from t_user_dept where user = '333'
                    //判断他们是否有交集

                }
            }
        }else{
            return HttpResult.FAILURE_MSG("未登录或没有相应数据权限");
        }
        return null;
    }

    public static ModelAndView isRoleView(String[] roles){
        boolean isPerm = isRole(roles);
        if(isPerm){
            return null;
        }else{
            return new ModelAndView("/diaowen-common/nopermission");
        }
    }

    public static HttpResult isRoleHttpResult(String[] roles){
        boolean isPerm = isRole(roles);
        if(isPerm){
            return null;
        }else{
            return HttpResult.FAILURE_MSG("权限不够！");
        }
    }

    public static boolean isRole(String[] roles){
        boolean isRole=false;
        Subject subject = SecurityUtils.getSubject();
        List<String> roleList = new ArrayList<String>();
        for (String role:roles) {
            isRole=subject.hasRole(role);
            if(isRole){
                break;
            }
        }
        return isRole;
    }

    public static ModelAndView isPermissionView(String perms){
        boolean isPerm = isPerm(new String[]{perms},Logical.OR);
        if(isPerm){
            return null;
        }else{
            return new ModelAndView("/diaowen-common/nopermission");
        }
    }

    public static HttpResult isPermissionHttpResult(String perms){
        boolean isPerm = isPerm(new String[]{perms},Logical.OR);
        if(isPerm){
            return null;
        }else{
            return HttpResult.FAILURE_MSG("权限不够！");
        }
    }


    public static ModelAndView isPermissionView(String[] perms,Logical logical){
        boolean isPerm = isPerm(perms,logical);
        if(isPerm){
           return null;
        }else{
           return new ModelAndView("/diaowen-common/nopermission");
        }
    }

    public static HttpResult isPermissionHttpResult(String[] perms,Logical logical){
        boolean isPerm = isPerm(perms,logical);
        if(isPerm){
            return null;
        }else{
            return HttpResult.FAILURE_MSG("权限不够！");
        }
    }

    public static boolean isPerm(String[] perms, Logical logical){
        boolean isPerm=false;
        Subject subject = SecurityUtils.getSubject();
        if (logical == Logical.OR) {
            for (String perm:perms) {
                isPerm=subject.isPermitted(perm);
                if(isPerm){
                    break;
                }
            }
            return isPerm;
        }else{
            for (String perm:perms) {
                isPerm=subject.isPermitted(perm);
                if(!isPerm){
                    break;
                }
            }
            return isPerm;
        }

    }

    public static boolean isPerm(String perm){
        Subject subject = SecurityUtils.getSubject();
        return subject.isPermitted(perm);
    }

    public static boolean hasRole(String role) {
        Subject subject = SecurityUtils.getSubject();
        return subject.hasRole(role);
    }

    public static String[] superAdmin(){
        return new String[]{RoleCode.SUPER_ADMIN,RoleCode.DWSURVEY_SUPER_ADMIN};
    }


    public static String[] sysSurveyDataAdminRoles(){
        return new String[]{RoleCode.ENT_ADMIN,RoleCode.SUPER_ADMIN,RoleCode.DWSURVEY_SUPER_ADMIN};
    }

    public static String[] deptSurveyDataAdminRoles(){
        return new String[]{RoleCode.DEPT_ADMIN};
    }

    public static HttpResult isSurveyDataSysAdminHttpResult(String permissionCode) {
        HttpResult httpResult = ShiroAuthorizationUtils.isPermissionHttpResult(permissionCode);
        if (httpResult != null) {
            httpResult = ShiroAuthorizationUtils.isRoleHttpResult(ShiroAuthorizationUtils.sysSurveyDataAdminRoles());
            if (httpResult != null) {
                return httpResult;
            }
        }
        return null;
    }

    public static HttpResult isSurveyDataDeptAdminHttpResult(String permissionCode) {
        HttpResult httpResult = ShiroAuthorizationUtils.isPermissionHttpResult(permissionCode);
        if (httpResult != null) {
            httpResult = ShiroAuthorizationUtils.isRoleHttpResult(ShiroAuthorizationUtils.deptSurveyDataAdminRoles());
            if (httpResult != null) {
                return httpResult;
            }
        }
        return null;
    }

    /**
     * 前台功能，默认放行的角色
     * @return
     */
    public static String[] defaultQTAdminRoles(){
        return new String[]{RoleCode.PROJECT_ADMIN,RoleCode.DEPT_ADMIN,RoleCode.ENT_ADMIN,RoleCode.SUPER_ADMIN, RoleCode.DWSURVEY_SUPER_ADMIN};
    }

    public static String[] defaultHTAdminRoles(){
        return new String[]{RoleCode.DEPT_ADMIN,RoleCode.ENT_ADMIN,RoleCode.SUPER_ADMIN, RoleCode.DWSURVEY_SUPER_ADMIN};
    }

    public static String getQtRoleArrayStr(String rolesOrPermsCode){
        String temp = RoleCode.PROJECT_ADMIN+","+RoleCode.DEPT_ADMIN+","+RoleCode.ENT_ADMIN+","+RoleCode.SUPER_ADMIN+","+RoleCode.DWSURVEY_SUPER_ADMIN;
        if(rolesOrPermsCode!=null){
            return temp+rolesOrPermsCode;
        }
        return temp;
    }


    public static String getHtRoleArrayStr(String rolesOrPermsCode){
        String temp = RoleCode.DEPT_ADMIN+","+RoleCode.ENT_ADMIN+","+RoleCode.SUPER_ADMIN+","+RoleCode.DWSURVEY_SUPER_ADMIN;
        if(rolesOrPermsCode!=null){
            return temp+rolesOrPermsCode;
        }
        return temp;
    }

    public static HttpResult isDefaultAdminRoleAndPermissionHttpResult(String permissionCode) {
        HttpResult httpResult = ShiroAuthorizationUtils.isPermissionHttpResult(permissionCode);
        if (httpResult != null) {
            httpResult = ShiroAuthorizationUtils.isRoleHttpResult(ShiroAuthorizationUtils.defaultQTAdminRoles());
            if (httpResult != null) {
                return httpResult;
            }
        }
        return null;
    }

    public static HttpResult isDefaultAdminRoleAndPermissionHttpResult(String[] permissionCodes,Logical logical) {
        HttpResult httpResult = ShiroAuthorizationUtils.isPermissionHttpResult(permissionCodes,logical);
        if (httpResult != null) {
            httpResult = ShiroAuthorizationUtils.isRoleHttpResult(ShiroAuthorizationUtils.defaultQTAdminRoles());
            if (httpResult != null) {
                return httpResult;
            }
        }
        return null;
    }

    /**
     * 前台admin组
     * @param permissionCode
     * @return
     */
    public static ModelAndView isDefaultAdminRoleAndPermissionView(String permissionCode) {
        ModelAndView modelAndView = ShiroAuthorizationUtils.isPermissionView(permissionCode);
        if (modelAndView != null) {
            modelAndView = ShiroAuthorizationUtils.isRoleView(ShiroAuthorizationUtils.defaultQTAdminRoles());
            if (modelAndView != null) {
                return modelAndView;
            }
        }
        return null;
    }

    /**
     * 前台admin组
     * @param permissionCodes
     * @param logical
     * @return
     */
    public static ModelAndView isDefaultAdminRoleAndPermissionView(String[] permissionCodes,Logical logical) {
        ModelAndView modelAndView;
        modelAndView = ShiroAuthorizationUtils.isPermissionView(permissionCodes,logical);
        if (modelAndView != null) {
            modelAndView = ShiroAuthorizationUtils.isRoleView(ShiroAuthorizationUtils.defaultQTAdminRoles());
            if (modelAndView != null) {
                return modelAndView;
            }
        }
        return null;
    }

    /**
     * 后台默认admin组
     * @param permissionCode
     * @return
     */
    public static ModelAndView isDefaultHTAdminRoleAndPermissionView(String permissionCode) {
        ModelAndView modelAndView = ShiroAuthorizationUtils.isPermissionView(permissionCode);
        if (modelAndView != null) {
            modelAndView = ShiroAuthorizationUtils.isRoleView(ShiroAuthorizationUtils.defaultHTAdminRoles());
            if (modelAndView != null) {
                return modelAndView;
            }
        }
        return null;
    }

    /**
     * 后台默认admin组
     * @param permissionCodes
     * @param logical
     * @return
     */
    public static ModelAndView isDefaultHTAdminRoleAndPermissionView(String[] permissionCodes,Logical logical) {
        ModelAndView modelAndView;
        modelAndView = ShiroAuthorizationUtils.isPermissionView(permissionCodes,logical);
        if (modelAndView != null) {
            modelAndView = ShiroAuthorizationUtils.isRoleView(ShiroAuthorizationUtils.defaultHTAdminRoles());
            if (modelAndView != null) {
                return modelAndView;
            }
        }
        return null;
    }




}
