package net.diaowen.dwsurvey.entity.es.answer;

import java.io.Serializable;

public class EsAnState {
    /** 回复的此条数据统计信息 **/
    //回答的题数
    private Integer anQuNum;
    /** 数据--有效情况   根据设计问卷时指定的必填项 **/
    //是否是有效数据  1有效
    private Integer isEff;
    //结束方式
    private Integer breakType;
    /** 数据--审核情况  **/
    //审核状态  0未处理 1通过 2不通过
    private Integer handleState;

    public Integer getAnQuNum() {
        return anQuNum;
    }

    public void setAnQuNum(Integer anQuNum) {
        this.anQuNum = anQuNum;
    }

    public Integer getIsEff() {
        return isEff;
    }

    public void setIsEff(Integer isEff) {
        this.isEff = isEff;
    }

    public Integer getBreakType() {
        return breakType;
    }

    public void setBreakType(Integer breakType) {
        this.breakType = breakType;
    }

    public Integer getHandleState() {
        return handleState;
    }

    public void setHandleState(Integer handleState) {
        this.handleState = handleState;
    }
}
