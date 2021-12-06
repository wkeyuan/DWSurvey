package net.diaowen.dwsurvey.common;

public class AnswerCheckData {

    private String token;
    private boolean imgCheckCode = false;//是否启用图片验证码
    private boolean answerCheck = true;
    private Integer answerCheckCode = 0;

    private String redirectUrl;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAnswerCheck() {
        return answerCheck;
    }

    public void setAnswerCheck(boolean answerCheck) {
        this.answerCheck = answerCheck;
    }

    public Integer getAnswerCheckCode() {
        return answerCheckCode;
    }

    public void setAnswerCheckCode(Integer answerCheckCode) {
        this.answerCheckCode = answerCheckCode;
    }

    public boolean isImgCheckCode() {
        return imgCheckCode;
    }

    public void setImgCheckCode(boolean imgCheckCode) {
        this.imgCheckCode = imgCheckCode;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
