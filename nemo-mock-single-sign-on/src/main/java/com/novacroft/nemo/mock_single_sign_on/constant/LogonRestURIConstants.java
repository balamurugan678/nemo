package com.novacroft.nemo.mock_single_sign_on.constant;

public final class LogonRestURIConstants {
    
    
    public static final String POST_AUTHENTICATE = "/Login";
    public static final String POST_VALIDATE_SSO = "/validatesso";
    public static final String POST_VALIDATE_TOKEN = "/validatetoken";
    public static final String POST_CHECK_ACTIVE_SESSION = "/checkactivesession";
    public static final String POST_SIGNOUT = "/signout";
    public static final String POST_MOCK_LOGOUT = "/MockLogout";
    public static final String POST_UPDATE_USER_CUSTOMER = "/UpdateUserCustomer";
    
    public static final String COOKIE_HEADER_KEY = "Cookie";

    private LogonRestURIConstants(){
        
    }
}
