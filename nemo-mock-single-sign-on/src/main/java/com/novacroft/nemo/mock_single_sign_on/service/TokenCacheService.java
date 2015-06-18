package com.novacroft.nemo.mock_single_sign_on.service;

import java.util.List;

public interface TokenCacheService {
    
    void saveTokenToCache(String token, String username, String app, String sessionId);
    
    String getCachedUsername(String token);
    
    void clearTokenCache(String token);
    
    Boolean isTokenValid(String token);
    
    List<String> getCachedApps(String token);
    
    void saveAppToCache(String token, String app, String sessionId);
    
    String getCachedToken(String sessionId);
}
