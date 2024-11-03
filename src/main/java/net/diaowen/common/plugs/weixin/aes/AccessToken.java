package net.diaowen.common.plugs.weixin.aes;

import java.util.Date;

public class AccessToken {
    private String accessToken; //获取到的凭证
    private int expireSecond;    //凭证有效时间  单位:秒
    private Date createTime = new Date();

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpireSecond() {
        return expireSecond;
    }

    public void setExpireSecond(int expireSecond) {
        this.expireSecond = expireSecond;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}