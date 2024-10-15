package net.diaowen.dwsurvey.entity.es.answer.extend;

public class EsAnSource {
    /** 不同来源数据 **/
    //数据来源  0 默认（网调），1=管理员录入数据， 2=管理员批量导入数据
    private String dbSource;
    //终端信息
    //设备 1=pc 2=m
    private String pcm;
    //操作系统
    private String sys;
    //浏览器
    private String bro;
    // 浏览器
    private String userAgentString;

    public String getDbSource() {
        return dbSource;
    }

    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }

    public String getPcm() {
        return pcm;
    }

    public void setPcm(String pcm) {
        this.pcm = pcm;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getBro() {
        return bro;
    }

    public void setBro(String bro) {
        this.bro = bro;
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public void setUserAgentString(String userAgentString) {
        this.userAgentString = userAgentString;
    }
}
