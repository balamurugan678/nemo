package com.novacroft.nemo.tfl.common.application_service;

import java.util.Map;

import javax.servlet.http.Cookie;



public interface SingleSignOnTokenService {
    
    Cookie requestToken(String username, String password, String appId, String sessionId);
    
    Object validateTokenAndRetrieveUserDetails(String token);
    
    Object checkIfSessionActive(String token, String appId, String sessionId);
    
    Object updateMasterCustomerData(Map<String, String> changeset);
    
}
