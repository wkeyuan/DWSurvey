package net.diaowen.common.base.controller;

import net.diaowen.common.plugs.httpclient.HttpResult;

public class LoginRegisterResult {

    private String status;
    private String type;
    private String[] currentAuthority;
    private HttpResult httpResult;
    private String username;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getCurrentAuthority() {
        return currentAuthority;
    }

    public void setCurrentAuthority(String[] currentAuthority) {
        this.currentAuthority = currentAuthority;
    }

    public HttpResult getHttpResult() {
        return httpResult;
    }

    public void setHttpResult(HttpResult httpResult) {
        this.httpResult = httpResult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static LoginRegisterResult RESULT(String status, String type){
        LoginRegisterResult loginResult = new LoginRegisterResult();
        loginResult.setStatus(status);
        loginResult.setType(type);
        loginResult.setCurrentAuthority(new String[]{});
        return loginResult;
    }

    public static LoginRegisterResult SUCCESS(String currentAuthority, String username) {
        LoginRegisterResult loginResult = new LoginRegisterResult();
        loginResult.setStatus("ok");
        loginResult.setType("account");
        loginResult.setUsername(username);
//        loginResult.setCurrentAuthority("admin");
        loginResult.setCurrentAuthority(new String[]{currentAuthority});
        return loginResult;
    }

    public static LoginRegisterResult SUCCESS(String[] currentAuthority, String username) {
        LoginRegisterResult loginResult = new LoginRegisterResult();
        loginResult.setStatus("ok");
        loginResult.setType("account");
        loginResult.setUsername(username);
//        loginResult.setCurrentAuthority("admin");
        loginResult.setCurrentAuthority(currentAuthority);
        return loginResult;
    }

    public static LoginRegisterResult FAILURE(){
        LoginRegisterResult loginResult = new LoginRegisterResult();
        loginResult.setStatus("error");
        loginResult.setType("account");
        loginResult.setCurrentAuthority(new String[]{"guest"});
        return loginResult;
    }

    public static LoginRegisterResult FAILURE(HttpResult httpResult){
        LoginRegisterResult loginResult = new LoginRegisterResult();
        loginResult.setStatus("error");
        loginResult.setType("account");
        loginResult.setCurrentAuthority(new String[]{"guest"});
        loginResult.setHttpResult(httpResult);
        return loginResult;
    }

}
