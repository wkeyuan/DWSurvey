package net.diaowen.dwsurvey.common;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PermissionModule {

    QT_SURVEY("前台-问卷模块",0),
    QT_PRO_MODEL("前台-项目模板",5),
    QT_ACCOUNT("前台-我的账号",6),
    QT_PRO_TASK("前台-项目任务",7),
    QT_PRO_COLLECT("前台-项目数据收集",9),
    QT_PRO_STATS("前台-项目数据统计",10),

    HT_SURVEY("后台-问卷管理",100),

    HT_PRO_MODEL("后台-项目模板管理",106),
    HT_CASCADE("后台-级联题数据管理",108),

    HT_ENT_EMPLOYEE("后台-账户管理",109),
    HT_ENT_DEPT("后台-部门管理",110),
    HT_USER_AUDIT("后台-账号审核管理",112),

    HT_ROLE("后台-角色管理",114),
    HT_PERM("后台-权限管理",115),
    HT_SYSTEM_LOG("后台-系统日志",116),
;

    private String name;
    private int index;

    private PermissionModule(String name,int index){
        this.name=name;
        this.index=index;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }


}
