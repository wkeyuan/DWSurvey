package net.diaowen.dwsurvey.entity.es.answer.question.option;

import net.diaowen.dwsurvey.entity.es.answer.extend.location.EsAddressComponent;
import net.diaowen.dwsurvey.entity.es.answer.extend.location.EsCitySearchResult;

public class EsAnLocation {
    // 定位品牌：wx, amap; 备注：微信定位结果会转换成高德信息统一返回保存
    private String locationBrand;
    // 客户端来源: m, pc
    private String locationClient;
    // 定位级别: vague 模糊,accurate 精确, high 高精度
    private String accuracyLevel;
    // 是否完成定位
    private Boolean isLocation;

    // 定位数据: GCJ-02 坐标
    private Float longitude; // 经度，浮点数，范围为180 ~ -180。
    private Float latitude;// 纬度，浮点数，范围为90 ~ -90
    // 定位精度
    private Float accuracy;
    // 定位类型: ip/h5/sdk/ipcity/wx
    private String locationType;
    // 海拔
    private Float altitude;
    // 高度精度
    private Float altitudeAccuracy;
    // 方向
    private String heading;
    // 速度
    private Float speed;
    // 定位规范地址地址
    private String formattedAddress;
    // 结构化信息
    private EsAddressComponent addressComponent;

    // citysearch 定位数据
    private Boolean isCitysearch;
    private EsCitySearchResult citySearchResult;



    // 定位结果信息 'SUCCESS' 或者 'PERMISSION_DENIED' 或者 'TIME_OUT' 或者 'POSITION_UNAVAILABLE'
    private String info;
    public String getLocationBrand() {
        return locationBrand;
    }

    public void setLocationBrand(String locationBrand) {
        this.locationBrand = locationBrand;
    }

    public String getLocationClient() {
        return locationClient;
    }

    public void setLocationClient(String locationClient) {
        this.locationClient = locationClient;
    }

    public String getAccuracyLevel() {
        return accuracyLevel;
    }

    public void setAccuracyLevel(String accuracyLevel) {
        this.accuracyLevel = accuracyLevel;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Float getAltitude() {
        return altitude;
    }

    public void setAltitude(Float altitude) {
        this.altitude = altitude;
    }

    public Float getAltitudeAccuracy() {
        return altitudeAccuracy;
    }

    public void setAltitudeAccuracy(Float altitudeAccuracy) {
        this.altitudeAccuracy = altitudeAccuracy;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public EsAddressComponent getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(EsAddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

    public EsCitySearchResult getCitySearchResult() {
        return citySearchResult;
    }

    public void setCitySearchResult(EsCitySearchResult citySearchResult) {
        this.citySearchResult = citySearchResult;
    }

    public Boolean getCitysearch() {
        return isCitysearch;
    }

    public void setCitysearch(Boolean citysearch) {
        isCitysearch = citysearch;
    }

    public Boolean getLocation() {
        return isLocation;
    }

    public void setLocation(Boolean location) {
        isLocation = location;
    }
}
