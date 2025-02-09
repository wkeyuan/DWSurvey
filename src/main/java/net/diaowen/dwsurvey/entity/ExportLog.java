package net.diaowen.dwsurvey.entity;

import net.diaowen.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by keyuan on 2018/6/9.
 */
@Entity
@Table(name = "t_export_log")
public class ExportLog extends IdEntity {
    private float progress;
    private int exportType;
    private String param1;//surveyId
    private String param2;//文件存储目录
    private String param3;//文件名
    private String param4;//文件存储目录+文件名，web地址
    private Date createDate = new Date();
    private Date endDate;
    private Float totalTime;
    private Integer titleTag=0;
    private Integer threadMaxExportNum;
    private Integer expUpQu=0;//0 不下载文件，1下载文件
    private Integer expDataContent=1;//null 1 导出原始数据， 2导出分值

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public int getExportType() {
        return exportType;
    }

    public void setExportType(int exportType) {
        this.exportType = exportType;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getTitleTag() {
        return titleTag;
    }

    public void setTitleTag(Integer titleTag) {
        this.titleTag = titleTag;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Float totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getThreadMaxExportNum() {
        return threadMaxExportNum;
    }

    public void setThreadMaxExportNum(Integer threadMaxExportNum) {
        this.threadMaxExportNum = threadMaxExportNum;
    }

    public Integer getExpUpQu() {
        return expUpQu;
    }

    public void setExpUpQu(Integer expUpQu) {
        this.expUpQu = expUpQu;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public Integer getExpDataContent() {
        return expDataContent;
    }

    public void setExpDataContent(Integer expDataContent) {
        this.expDataContent = expDataContent;
    }
}
