package net.diaowen.dwsurvey.common;

import net.diaowen.common.plugs.httpclient.HttpStatus;
import net.diaowen.dwsurvey.entity.SurveyJson;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswer;

public class DwAnswerCheckResult {

    // 验证是否通过
    private boolean anCheckIsPass = false;
    // 點认值0
    private Integer anCheckResultCode = 0;
    // 对此条状态的简要说明
    private String anCheckResultMsg = null;
    // 对此条状态的详细说明
    private String anCheckResultRemark = null;
    // 答卷token
    private String anToken;
    // 是否启用图片验证码
    private boolean showCaptcha = false;

    //答卷ID
    private String indexResponseId = null;
    private Float sumScore = null; // 总得分
    private DwEsSurveyAnswer dwEsSurveyAnswer = null;

    // 重定向到某个地址
    private String redirectUrl;

    public DwAnswerCheckResult(){
        this.anCheckIsPass = false;
        this.anCheckResultCode = 0;
        this.anCheckResultMsg = "";
        this.anCheckResultRemark = "";
    }

    DwAnswerCheckResult(boolean anCheckIsPass,int anCheckResultCode, String anCheckResultMsg, String anCheckResultRemark) {
        this.anCheckIsPass = anCheckIsPass;
        this.anCheckResultCode = anCheckResultCode;
        this.anCheckResultMsg = anCheckResultMsg;
        this.anCheckResultRemark = anCheckResultRemark;
    }

    public boolean isAnCheckIsPass() {
        return anCheckIsPass;
    }

    public void setAnCheckIsPass(boolean anCheckIsPass) {
        this.anCheckIsPass = anCheckIsPass;
    }

    public Integer getAnCheckResultCode() {
        return anCheckResultCode;
    }

    public void setAnCheckResultCode(Integer anCheckResultCode) {
        this.anCheckResultCode = anCheckResultCode;
    }

    public String getAnCheckResultMsg() {
        return anCheckResultMsg;
    }

    public void setAnCheckResultMsg(String anCheckResultMsg) {
        this.anCheckResultMsg = anCheckResultMsg;
    }

    public String getAnToken() {
        return anToken;
    }

    public void setAnToken(String anToken) {
        this.anToken = anToken;
    }

    public boolean isShowCaptcha() {
        return showCaptcha;
    }

    public void setShowCaptcha(boolean showCaptcha) {
        this.showCaptcha = showCaptcha;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getAnCheckResultRemark() {
        return anCheckResultRemark;
    }

    public void setAnCheckResultRemark(String anCheckResultRemark) {
        this.anCheckResultRemark = anCheckResultRemark;
    }

    public String getIndexResponseId() {
        return indexResponseId;
    }

    public void setIndexResponseId(String indexResponseId) {
        this.indexResponseId = indexResponseId;
    }

    public DwEsSurveyAnswer getDwEsSurveyAnswer() {
        return dwEsSurveyAnswer;
    }

    public void setDwEsSurveyAnswer(DwEsSurveyAnswer dwEsSurveyAnswer) {
        this.dwEsSurveyAnswer = dwEsSurveyAnswer;
    }

    public Float getSumScore() {
        return sumScore;
    }

    public void setSumScore(Float sumScore) {
        this.sumScore = sumScore;
    }

    public void buildResult(DwAnswerCheckResult dwAnswerCheckResult){
        this.buildResult(dwAnswerCheckResult.anCheckIsPass, dwAnswerCheckResult.anCheckResultCode, dwAnswerCheckResult.anCheckResultMsg, dwAnswerCheckResult.anCheckResultRemark);
    }

    public void buildResult(boolean anCheckIsPass, Integer anCheckResultCode, String anCheckResultMsg, String anCheckResultRemark){
        this.anCheckIsPass = anCheckIsPass;
        this.anCheckResultCode = anCheckResultCode;
        this.anCheckResultMsg = anCheckResultMsg;
        this.anCheckResultRemark = anCheckResultRemark;
    }

    // 类似http请求状态码机制
    // =200 验证通过
    public static DwAnswerCheckResult SUCCESS200 = new DwAnswerCheckResult(true, 200,"ok", "过滤验证通过");//
    public static DwAnswerCheckResult SUCCESS201 = new DwAnswerCheckResult(true, 201,"success", "保存答卷成功");//

    // >=400 验证不通过
    public static DwAnswerCheckResult CHECK400 = new DwAnswerCheckResult(false, 400,"重复的请求", "短时间内重复的请求");//
    public static DwAnswerCheckResult CHECK401 = new DwAnswerCheckResult(false, 401,"已经答过，请不要重复作答", "超过单个终端限制次数");//终端答卷次数
    public static DwAnswerCheckResult CHECK402 = new DwAnswerCheckResult(false, 402,"超过答卷次数限制", "超过单个IP限制次数");//
    public static DwAnswerCheckResult CHECK403 = new DwAnswerCheckResult(false, 403,"答卷密码错误", "答卷密码错误");//
    public static DwAnswerCheckResult CHECK404 = new DwAnswerCheckResult(false, 404,"问卷已经删除", "问卷已经删除");//
    public static DwAnswerCheckResult CHECK405 = new DwAnswerCheckResult(false, 405,"问卷未开启", "问卷未开启");//
    public static DwAnswerCheckResult CHECK406 = new DwAnswerCheckResult(false, 406,"没有问卷的发布数据", "没有问卷的发布数据");//
    public static DwAnswerCheckResult CHECK407 = new DwAnswerCheckResult(false, 407,"已经达到收集上限", "已经达到收集次数上限，截止答卷");//
    public static DwAnswerCheckResult CHECK408 = new DwAnswerCheckResult(false, 408,"已经达到最后截止时间", "已经达到最后截止时间，截止答卷");//
    public static DwAnswerCheckResult CHECK4081 = new DwAnswerCheckResult(false, 408,"还未到答卷开始时间", "还未到答卷开始时间，请稍后再访问");//
    public static DwAnswerCheckResult CHECK409 = new DwAnswerCheckResult(false, 409,"需要显示验证码", "重复请求请输入验证码");//
    public static DwAnswerCheckResult CHECK410 = new DwAnswerCheckResult(false, 410,"验证码不正确，请输入正确验证码", "重复请求请输入验证码");//


    // >=500 错误级别
    public static DwAnswerCheckResult CHECK500 = new DwAnswerCheckResult(false, 500,"没有对应的问卷", "没有在数据库中找到您需要的问卷信息，请确认更正后再访问");//
    public static DwAnswerCheckResult CHECK501 = new DwAnswerCheckResult(false, 501,"没有对应的问卷ID", "问卷ID没有在数据库中找到您需要的问卷信息，请确认更正后再访问");//
    public static DwAnswerCheckResult SERVER502 = new DwAnswerCheckResult(false, 502,"答卷保存未成功", "答卷保存未成功，需要管理员解决");//

}
