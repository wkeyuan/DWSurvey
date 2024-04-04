package net.diaowen.dwsurvey.entity.es.answer.extend;

public class EsAnSource {
    /** 不同来源数据 **/
    //数据来源  0 默认（网调），1=管理员录入数据， 2=管理员批量导入数据
    private Integer dbSource;

    // 0=PC,1=M
    private Integer pcOrM;

    //例如：微信，浏览器名称
    private String anClient;

    public Integer getDbSource() {
        return dbSource;
    }

    public void setDbSource(Integer dbSource) {
        this.dbSource = dbSource;
    }

    public Integer getPcOrM() {
        return pcOrM;
    }

    public void setPcOrM(Integer pcOrM) {
        this.pcOrM = pcOrM;
    }

    public String getAnClient() {
        return anClient;
    }

    public void setAnClient(String anClient) {
        this.anClient = anClient;
    }
}
