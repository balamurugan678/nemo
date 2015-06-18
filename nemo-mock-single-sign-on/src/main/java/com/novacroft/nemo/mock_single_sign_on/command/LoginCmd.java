package com.novacroft.nemo.mock_single_sign_on.command;




public class LoginCmd {
    
    protected String userName = "TEST";
    protected String password;
    protected String app;
    protected String returnURL;
    protected String errorURL;
    protected String errorCode;
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getApp() {
        return app;
    }
    public void setApp(String app) {
        this.app = app;
    }
    public String getReturnURL() {
        return returnURL;
    }
    public void setReturnURL(String returnURL) {
        this.returnURL = returnURL;
    }
    public String getErrorURL() {
        return errorURL;
    }
    public void setErrorURL(String errorURL) {
        this.errorURL = errorURL;
    }
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    

}
