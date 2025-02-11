package net.diaowen.common.plugs.ipaddr;

public class IPLocation {

    private String ip;
    //IP对应的省
    private String province;
    //IP对应的市
    private String city;
    //区县
    private String county;
    //乡
    private String town;
    //街道
    private String village;
    //解析后完整地址
    private String address;


    public IPLocation(){

    }
    public IPLocation(String ip,String province,String city,String address){
        this.ip = ip;
        this.province = province;
        this.city = city;
        this.address = address;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }
}
