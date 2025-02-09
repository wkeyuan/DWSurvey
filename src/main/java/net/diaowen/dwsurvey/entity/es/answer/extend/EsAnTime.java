package net.diaowen.dwsurvey.entity.es.answer.extend;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class EsAnTime {
    //回答时间
    //    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")  // 数据格式转换，并加上8小时进行存储
    private Date bgAnDate;
    //回答时间
    //    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endAnDate;
    //用时
    private Float totalTime;

    public Date getBgAnDate() {
        return bgAnDate;
    }

    public void setBgAnDate(Date bgAnDate) {
        this.bgAnDate = bgAnDate;
    }

    public Date getEndAnDate() {
        return endAnDate;
    }

    public void setEndAnDate(Date endAnDate) {
        this.endAnDate = endAnDate;
    }

    public Float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Float totalTime) {
        this.totalTime = totalTime;
    }
}
