package com.novacroft.nemo.tfl.common.application_service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SingleSignOnSecurityService {
    
    void login(String username, String password, HttpServletRequest request, HttpServletResponse response);
    
    Object checkSessionActive(String token, String sessionId);
    
    Boolean autoLogin(String token, Object jsonUserDetail, HttpServletRequest request);
    
    void singleSignOut(String token);
}
