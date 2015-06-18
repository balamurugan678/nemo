package com.novacroft.nemo.mock_single_sign_on.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.novacroft.nemo.mock_single_sign_on.service.TokenCacheService;

@Service("tokenCacheService")
public class TokenCacheServiceImpl implements TokenCacheService {

    protected static Map<String, String> tokenUserMap = new HashMap<>();
    protected static Map<String, ArrayList<String>> tokenAppMap = new HashMap<>();
    protected static Map<String, String> sessionTokenMap = new HashMap<>(); 
    
    @Override
    public synchronized void saveTokenToCache(String token, String username, String app, String sessionId) {
        tokenUserMap.put(token, username);
        saveAppToCache(token, app, sessionId);
    }

    @Override
    public synchronized String getCachedUsername(String token) {
        return tokenUserMap.get(token);
    }

    @Override
    public synchronized void clearTokenCache(String token) {
        tokenUserMap.remove(token);
        tokenAppMap.remove(token);
        sessionTokenMap.values().removeAll(Collections.singleton(token));
    }

    @Override
    public synchronized Boolean isTokenValid(String token) {
        return tokenUserMap.containsKey(token) && tokenAppMap.containsKey(token);
    }

    @Override
    public synchronized List<String> getCachedApps(String token) {
        List<String> cachedApps = tokenAppMap.get(token);
        return (cachedApps == null ? new ArrayList<String>() : cachedApps);
    }

    @Override
    public synchronized void saveAppToCache(String token, String app, String sessionId) {
        if (!tokenAppMap.containsKey(token)) {
            tokenAppMap.put(token, new ArrayList<String>());
        }
        ArrayList<String> cacheApps = tokenAppMap.get(token);
        if (!cacheApps.contains(app)) {
            cacheApps.add(app);
        }
        
        sessionTokenMap.put(sessionId, token);
    }

    @Override
    public String getCachedToken(String sessionId) {
        return sessionId == null ? null : sessionTokenMap.get(sessionId);
    }
}
