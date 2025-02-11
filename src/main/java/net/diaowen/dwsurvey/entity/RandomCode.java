package net.diaowen.dwsurvey.entity;

import net.diaowen.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by keyuan on 2019/7/25.
 */
@Entity
@Table(name="t_random_code")
public class RandomCode extends IdEntity{

    private String rdName;//随机对象名称，如手机号，邮件地址, 微信

    private Integer rdType;//随机类型：1，手机号，2 邮件 3 微信
    private Integer rdEvent;// 随机事件：1 注册  2 登录  3 绑定 4 抽奖 5 解绑手机号

    private String rdCode;//随机值

    private Date createDate = new Date();

    private Integer rdStatus; // 1 已经发送  2 成功验证

    private String userId;

    private String sceneStr;

    public String getRdName() {
        return rdName;
    }

    public void setRdName(String rdName) {
        this.rdName = rdName;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public String getRdCode() {
        return rdCode;
    }

    public void setRdCode(String rdCode) {
        this.rdCode = rdCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getRdEvent() {
        return rdEvent;
    }

    public void setRdEvent(Integer rdEvent) {
        this.rdEvent = rdEvent;
    }

    public Integer getRdStatus() {
        return rdStatus;
    }

    public void setRdStatus(Integer rdStatus) {
        this.rdStatus = rdStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSceneStr() {
        return sceneStr;
    }

    public void setSceneStr(String sceneStr) {
        this.sceneStr = sceneStr;
    }
}
