package net.diaowen.dwsurvey.entity.es.answer.extend;

public class EsAnIp {

    private String ip;
    //省级
    private String province;
    //市级 兼容早期的分词
    private String city;
    //市级 keyword 新分词
    private String cityV6;
    //县级
    private String county;
    //乡级
    private String town;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCityV6() {
        return cityV6;
    }

    public void setCityV6(String cityV6) {
        this.cityV6 = cityV6;
    }
}
