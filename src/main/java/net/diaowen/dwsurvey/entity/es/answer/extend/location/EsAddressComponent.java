package net.diaowen.dwsurvey.entity.es.answer.extend.location;

public class EsAddressComponent {

    // 国家
    private String country;
    //所在省（省编码在城市编码表中可查询到）
    private String province;
    //所在城市
    private String city;
    //所在城市编码
    private String citycode;
    //所在区
    private String district;
    //所在区域编码
    private String adcode;
    //所在乡镇
    private String township;
    //所在街道
    private String street;
    //门牌号
    private String streetNumber;

    // 所在社区
    private String neighborhood;
    // 社区类型
    private String neighborhoodType;
    //所在楼/大厦
    private String building;
    //楼类型
    private String buildingType;

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

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getNeighborhoodType() {
        return neighborhoodType;
    }

    public void setNeighborhoodType(String neighborhoodType) {
        this.neighborhoodType = neighborhoodType;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
