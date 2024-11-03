package net.diaowen.dwsurvey.entity;

import net.diaowen.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by keyuan on 2019/8/6.
 */
@Entity
@Table(name="t_wx_userinfo")
public class WxUserinfo extends IdEntity {

    private String userId;//关联的用户ID

    private String unionid;// 多个应用共享的ID
    private String openid;// 属于单个应用的ID
    private String nickname;//
    private Integer sex;//
    private String province;//
    private String city;//
    private String country;//
    private String headimgurl;//
    private String privilege;//

    //最后获取token的时间
    private Date tokenDate;
    private String access_token;//
    private String expires_in;//
    private String refresh_token;//
    private String accessTokenCode;//

    //作信息获取状态提示，即为
    private Integer scope;// 1 snsapi_base  2 snsapi_userinfo

    //状态标记是否已经获取过userinfo
    private Integer userinfoStatus=0;//0 未取 1已取过

    private Date createDate = new Date();

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public Date getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(Date tokenDate) {
        this.tokenDate = tokenDate;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getUserinfoStatus() {
        return userinfoStatus;
    }

    public void setUserinfoStatus(Integer userinfoStatus) {
        this.userinfoStatus = userinfoStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessTokenCode() {
        return accessTokenCode;
    }

    public void setAccessTokenCode(String accessTokenCode) {
        this.accessTokenCode = accessTokenCode;
    }
}
