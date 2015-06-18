package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.SingleSignOnSessionService;

@Service("singleSignOnSessionService")
public class SingleSignOnSessionServiceImpl implements SingleSignOnSessionService {
    protected static final Map<String, HttpSession> TOKEN_SESSION_MAP = new HashMap<>();
    protected static final Map<String, String> ID_TOKEN_MAP = new HashMap<>();

    @Override
    public synchronized HttpSession removeSessionByToken(String token) {
        String nullSafeToken = StringUtils.defaultString(token);
        
        HttpSession session = TOKEN_SESSION_MAP.remove(nullSafeToken);
        if (session != null) {
            TOKEN_SESSION_MAP.remove(nullSafeToken);
            ID_TOKEN_MAP.remove(session.getId());
        }
        return session;
    }

    @Override
    public synchronized  void addSessionByToken(String token, HttpSession httpSession) {
        if (StringUtils.isNotBlank(token)) {
            TOKEN_SESSION_MAP.put(token, httpSession);
            ID_TOKEN_MAP.put(httpSession.getId(), token);
        }
    }

    @Override
    public synchronized HttpSession removeSessionById(String sessionId) {
        HttpSession session = null;
        String token = ID_TOKEN_MAP.remove(sessionId);
        if (token != null) {
            session =  TOKEN_SESSION_MAP.remove(token);
        }
        return session;
    }
}
