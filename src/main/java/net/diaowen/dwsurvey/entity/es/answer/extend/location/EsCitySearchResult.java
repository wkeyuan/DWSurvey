package net.diaowen.dwsurvey.entity.es.answer.extend.location;

/**
 * 城市定位的数据
 */
public class EsCitySearchResult {
    private String info;
    private String infocode;
    // 国家
    private String country;
    // 省
    private String province;
    // 城市
    private String city;
    private String adcode;
    private String rectangle;
    private Float[] bounds;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getRectangle() {
        return rectangle;
    }

    public void setRectangle(String rectangle) {
        this.rectangle = rectangle;
    }

    public Float[] getBounds() {
        return bounds;
    }

    public void setBounds(Float[] bounds) {
        this.bounds = bounds;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
