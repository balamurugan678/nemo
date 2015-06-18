package com.novacroft.nemo.tfl.common.application_service;

import javax.servlet.http.HttpSession;

public interface SingleSignOnSessionService {
    
    HttpSession removeSessionByToken(String token);
    
    HttpSession removeSessionById(String sessionId);
    
    void addSessionByToken(String token, HttpSession httpSession);
}
