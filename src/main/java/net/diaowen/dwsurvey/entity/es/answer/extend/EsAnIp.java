package net.diaowen.dwsurvey.entity.es.answer.extend;

public class EsAnIp {

    private String ip;
    //省
    private String province;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
