package net.diaowen.dwsurvey.common;

public class PermissionCode {

    public static String QT_SURVEY_LIST = "QT_SURVEY_LIST";
    public static String QT_SURVEY_CREATE = "QT_SURVEY_CREATE";
    public static String QT_SURVEY_EDIT = "QT_SURVEY_EDIT";
    public static String QT_SURVEY_DELETE = "QT_SURVEY_DELETE";

    public static String QT_SURVEY_PREVIEW = "QT_SURVEY_PREVIEW";
    public static String QT_SURVEY_COPY = "QT_SURVEY_COPY";
    public static String QT_SURVEY_DEV = "QT_SURVEY_DEV";

    public static String QT_PDCOLLECT_MENU = "QT_PDCOLLECT_MENU";
    public static String QT_PDCOLLECT_ANSWERURL = "QT_PDCOLLECT_ANSWERURL";
    public static String QT_PDCOLLECT_SHARE = "QT_PDCOLLECT_SHARE";
    public static String QT_PDCOLLECT_SITECOMP = "QT_PDCOLLECT_SITECOMP" ;
    public static String QT_PDCOLLECT_IFRAME = "QT_PDCOLLECT_IFRAME" ;
    public static String QT_PDCOLLECT_WEIXIN = "QT_PDCOLLECT_WEIXIN" ;
    public static String QT_PDCOLLECT_IPANSWER = "QT_PDCOLLECT_IPANSWER" ;
    public static String QT_PDCOLLECT_PWDCODE = "QT_PDCOLLECT_PWDCODE" ;
    public static String QT_PDCOLLECT_USERBIND = "QT_PDCOLLECT_USERBIND" ;
    public static String QT_PDCOLLECT_FINISH = "QT_PDCOLLECT_FINISH";

    public static String QT_SURVEY_DATA_MENU = "QT_SURVEY_DATA_MENU";
    public static String QT_SURVEY_DATA_ANALYSIS = "QT_SURVEY_DATA_ANALYSIS";
    public static String QT_SURVEY_DATA_REPORT = "QT_SURVEY_DATA_REPORT";
    public static String QT_SURVEY_DATA_DISTRICT = "QT_SURVEY_DATA_DISTRICT";
    public static String QT_SURVEY_DATA_ANSWER_LIST = "QT_SURVEY_DATA_ANSWER_LIST";
    public static String QT_SURVEY_DATA_ANSWER_DETAIL = "QT_SURVEY_DATA_ANSWER_DETAIL";
    public static String QT_SURVEY_DATA_ANSWER_EXPORT = "QT_SURVEY_DATA_ANSWER_EXPORT";
    public static String QT_SURVEY_DATA_ANSWER_DELETE = "QT_SURVEY_DATA_ANSWER_DELETE";

    public static String QT_TEMPLATER_REF = "QT_TEMPLATER_REF";
    public static String QT_MYTASK_LIST = "QT_MYTASK_LIST";


    /***
     * 后台权限还差：
     * 用户：根据权限决定是查询部门或系统的。
     * 部门：根据权限决定是查询部门或系统的。
     * 控制台：根据所拥有的权限进行跳转
     * 控制台：把后台取全局数据的权限与取问卷的数据分隔，如地区，设置统计信息
     * 问卷：根据管理员拥有的权限，可以查看其它用户的问卷数据。
     */
    //后台
    public static String HT_MANAGER_MENU = "HT_MANAGER_MENU";
    public static String HT_DASHBOARD_ANALYSIS = "HT_DASHBOARD_ANALYSIS";
    public static String HT_DASHBOARD_MONITOR = "HT_DASHBOARD_MONITOR";
    //后台-问卷部分
    public static String HT_SURVEY_MANAGER_SYS = "HT_SURVEY_MANAGER_SYS" ;
    public static String HT_SURVEY_MANAGER_DEPT = "HT_SURVEY_MANAGER_DEPT" ;
    public static String HT_SURVEY_LIST = "HT_SURVEY_LIST";
    public static String HT_SURVEY_EDIT = "HT_SURVEY_EDIT";
    public static String HT_SURVEY_DELETE = "HT_SURVEY_DELETE";
    public static String HT_SURVEY_DEV = "HT_SURVEY_DEV";
    public static String HT_SURVEY_PREVIEW = "HT_SURVEY_PREVIEW";//可查看收集地址
    public static String HT_SURVEY_COPY = "HT_SURVEY_COPY";//可查看收集地址
    //收集页面
    public static String HT_SURVEY_COLLECT_MENU = "HT_SURVEY_COLLECT_MENU";//可查看收集地址
    public static String HT_SURVEY_COLLECT_URL = "HT_SURVEY_COLLECT_URL";//可查看收集地址
    public static String HT_SURVEY_COLLECT_SITECOMP = "HT_SURVEY_COLLECT_SITECOMP";//网站组件
    public static String HT_SURVEY_COLLECT_IPS_LIST = "HT_SURVEY_COLLECT_IPS_LIST";//查看ip配置
    public static String HT_SURVEY_COLLECT_BINDUSER_LIST = "HT_SURVEY_COLLECT_BINDUSER_LIST";//查看绑定配置
    public static String HT_SURVEY_COLLECT_FINISHGO_VIEW = "HT_SURVEY_COLLECT_FINISHGO_VIEW";//查看结束配置
    //统计数据
    public static String HT_SURVEY_DATA_MENU = "HT_SURVEY_DATA_MENU";
    public static String HT_SURVEY_DATA_ANALYSIS = "HT_SURVEY_DATA_ANALYSIS";
    public static String HT_SURVEY_DATA_REPORT = "HT_SURVEY_DATA_REPORT";
    public static String HT_SURVEY_DATA_DISTRICT = "HT_SURVEY_DATA_DISTRICT";
    public static String HT_SURVEY_DATA_ANSWER_LIST = "HT_SURVEY_DATA_ANSWER_LIST";
    public static String HT_SURVEY_DATA_ANSWER_DETAIL = "HT_SURVEY_DATA_ANSWER_DETAIL";
    public static String HT_SURVEY_DATA_ANSWER_EXPORT = "HT_SURVEY_DATA_ANSWER_EXPORT";
    public static String HT_SURVEY_DATA_ANSWER_DELETE = "HT_SURVEY_DATA_ANSWER_DELETE";

    //后台-问卷分组
    public static String HT_SURVEY_GROUP_LIST = "HT_SURVEY_GROUP_LIST" ;
    public static String HT_SURVEY_GROUP_CREATE = "HT_SURVEY_GROUP_CREATE" ;
    public static String HT_SURVEY_GROUP_EDIT = "HT_SURVEY_GROUP_EDIT" ;
    public static String HT_SURVEY_GROUP_DELETE = "HT_SURVEY_GROUP_DELETE" ;

    //后台-问卷模板
    public static String HT_PROTEMPLATER_LIST = "HT_PROTEMPLATER_LIST" ;
    public static String HT_PROTEMPLATER_CREATE = "HT_PROTEMPLATER_CREATE" ;
    public static String HT_PROTEMPLATER_EDIT = "HT_PROTEMPLATER_EDIT" ;
    public static String HT_PROTEMPLATER_DELETE = "HT_PROTEMPLATER_DELETE" ;
    public static String HT_PROTEMPLATER_DEV = "HT_PROTEMPLATER_DEV" ;

    //后台-级联数据
    public static String HT_CASCADEDB_LIST = "HT_CASCADEDB_LIST" ;
    public static String HT_CASCADEDB_CREATE = "HT_CASCADEDB_CREATE" ;
    public static String HT_CASCADEDB_EDIT = "HT_CASCADEDB_EDIT" ;
    public static String HT_CASCADEDB_DELETE = "HT_CASCADEDB_DELETE" ;

    //用户部分
    public static String HT_USER_MANAGER_SYS = "HT_USER_MANAGER_SYS";//官理系统用户
    public static String HT_USER_MANAGER_DEPT = "HT_USER_MANAGER_DEPT" ;//官理部门用户
    public static String HT_USER_LIST = "HT_USER_LIST" ;
    public static String HT_USER_CREATE = "HT_USER_CREATE" ;
    public static String HT_USER_EDIT = "HT_USER_EDIT" ;
    public static String HT_USER_DELETE = "HT_USER_DELETE" ;
    public static String HT_USER_EDIT_ROLE = "HT_USER_EDIT_ROLE" ;
    public static String HT_USER_EDIT_DEPT = "HT_USER_EDIT_DEPT" ;

    public static String HT_USER_EDIT_PWD = "HT_USER_EDIT_PWD" ;
    public static String HT_USER_DIS = "HT_USER_DIS" ;

    //部门部分
    public static String HT_DEPT_MANAGER_SYS = "HT_DEPT_MANAGER_SYS" ;
    public static String HT_DEPT_MANAGER_DEPT = "HT_DEPT_MANAGER_DEPT" ;
    public static String HT_DEPT_LIST = "HT_DEPT_LIST" ;
    public static String HT_DEPT_CREATE = "HT_DEPT_CREATE" ;
    public static String HT_DEPT_EDIT = "HT_DEPT_EDIT" ;
    public static String HT_DEPT_DELETE = "HT_DEPT_DELETE" ;

    //角色部分
    public static String HT_ROLE_LIST = "HT_ROLE_LIST" ;
    public static String HT_ROLE_CREATE = "HT_ROLE_CREATE" ;
    public static String HT_ROLE_EDIT = "HT_ROLE_EDIT" ;
    public static String HT_ROLE_DELETE = "HT_ROLE_DELETE" ;
    public static String HT_ROLE_EDIT_PERM = "HT_ROLE_EDIT_PERM" ;

    //权限部分
    public static String HT_PERM_LIST = "HT_PERM_LIST" ;
    public static String HT_SYSTEMLOG_LIST = "HT_SYSTEMLOG_LIST" ;

    public static String HT_APP_LIST = "HT_APP_LIST" ;
    public static String HT_APP_CREATE = "HT_APP_CREATE" ;
    public static String HT_APP_EDIT = "HT_APP_EDIT" ;
    public static String HT_APP_DELETE = "HT_APP_DELETE";

    //未使用
    public static String HT_USERAUDIT_LIST = "HT_USERAUDIT_LIST" ;
    public static String HT_USERAUDIT_EDIT = "HT_USERAUDIT_EDIT" ;

}
