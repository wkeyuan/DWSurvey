package net.diaowen.dwsurvey.entity.es.answer;

import java.io.Serializable;

public class EsAnIp {

    private String ip;
    //IP定位城市
    private String city;
    //IP定位详细地址
    private String addr;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
