package com.novacroft.nemo.mock_single_sign_on.service.impl;

import static com.novacroft.nemo.test_support.CookieTestUtil.TEST_TOKEN;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_APP_ID;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_SESSION_ID;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_USER_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TokenCacheServiceImplTest {
    @Mock
    private TokenCacheServiceImpl mockService;
    @Mock
    private Map<String, String> mockSessionTokenMap;
    @Mock
    private Map<String, String> mockTokenUserMap;
    @Mock
    private Map<String, ArrayList<String>> mockTokenAppMap;
    @Mock
    private ArrayList<String> mockAppList;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        setField(mockService, "sessionTokenMap", mockSessionTokenMap);
        setField(mockService, "tokenUserMap", mockTokenUserMap);
        setField(mockService, "tokenAppMap", mockTokenAppMap);
        
        when(mockSessionTokenMap.put(anyString(), anyString())).thenReturn(null);
        when(mockSessionTokenMap.remove(anyString())).thenReturn(null);
        
        when(mockTokenUserMap.put(anyString(), anyString())).thenReturn(null);
        when(mockTokenUserMap.remove(anyString())).thenReturn(null);
        
        when(mockService.isTokenValid(anyString())).thenCallRealMethod();
        when(mockService.getCachedApps(anyString())).thenCallRealMethod();
        when(mockService.getCachedToken(anyString())).thenCallRealMethod();
    }
    
    @Test
    public void saveTokenToCacheShouldSaveDataToMap() {
        doCallRealMethod().when(mockService).saveTokenToCache(anyString(), anyString(), anyString(), anyString());
        doNothing().when(mockService).saveAppToCache(anyString(), anyString(), anyString());
        
        mockService.saveTokenToCache(TEST_TOKEN, TEST_USER_NAME, TEST_APP_ID, TEST_SESSION_ID);
        
        verify(mockTokenUserMap).put(TEST_TOKEN, TEST_USER_NAME);
        verify(mockService).saveAppToCache(TEST_TOKEN, TEST_APP_ID, TEST_SESSION_ID);
    }
    
    @Test
    public void getCachedUsernameShouldGetFromMap() {
        when(mockService.getCachedUsername(anyString())).thenCallRealMethod();
        when(mockTokenUserMap.get(anyString())).thenReturn(TEST_USER_NAME);
        
        assertEquals(TEST_USER_NAME, mockService.getCachedUsername(TEST_TOKEN));
        verify(mockTokenUserMap).get(TEST_TOKEN);
    }
    
    @Test
    public void clearTokenCacheShouldRemoveFromMap() {
        doCallRealMethod().when(mockService).clearTokenCache(anyString());
        when(mockSessionTokenMap.values()).thenReturn(mockAppList);
        when(mockAppList.removeAll(anyCollectionOf(String.class))).thenReturn(true);
        
        mockService.clearTokenCache(TEST_TOKEN);
        
        verify(mockTokenAppMap).remove(TEST_TOKEN);
        verify(mockTokenUserMap).remove(TEST_TOKEN);
        verify(mockAppList).removeAll(anyCollectionOf(String.class));
    }
    
    @Test
    public void testIsTokenValidWhenTokenNotFoundInUserMap() {
        when(mockTokenUserMap.containsKey(anyString())).thenReturn(false);
        
        assertFalse(mockService.isTokenValid(TEST_TOKEN));
    }
    
    @Test
    public void testIsTokenValidWhenTokenNotFoundInAppMap() {
        when(mockTokenUserMap.containsKey(anyString())).thenReturn(true);
        when(mockTokenAppMap.containsKey(anyString())).thenReturn(false);
        
        assertFalse(mockService.isTokenValid(TEST_TOKEN));
    }
    
    @Test
    public void testIsTokenValidWhenTokenFoundInMaps() {
        when(mockTokenUserMap.containsKey(anyString())).thenReturn(true);
        when(mockTokenAppMap.containsKey(anyString())).thenReturn(true);
        
        assertTrue(mockService.isTokenValid(TEST_TOKEN));
    }
    
    @Test
    public void getCachedAppsReturnEmptyList() {
        when(mockTokenAppMap.get(anyString())).thenReturn(null);
        
        List<String> actualResult = mockService.getCachedApps(TEST_TOKEN);
        
        assertTrue(actualResult.isEmpty());
    }
    
    @Test
    public void getCachedAppsReturnList() {
        when(mockTokenAppMap.get(anyString())).thenReturn(mockAppList);
        
        assertEquals(mockAppList, mockService.getCachedApps(TEST_TOKEN));
    }
    
    @Test
    public void shouldSaveAppToCache() {
        doCallRealMethod().when(mockService).saveAppToCache(anyString(), anyString(), anyString());
        when(mockTokenAppMap.containsKey(TEST_TOKEN)).thenReturn(false);
        when(mockTokenAppMap.put(TEST_TOKEN, new ArrayList<String>())).thenReturn(null);
        when(mockTokenAppMap.get(TEST_TOKEN)).thenReturn(mockAppList);
        when(mockAppList.contains(anyString())).thenReturn(false);
        when(mockAppList.add(anyString())).thenReturn(true);
        
        mockService.saveAppToCache(TEST_TOKEN, TEST_APP_ID, TEST_SESSION_ID);
        
        verify(mockAppList).add(TEST_APP_ID);
        verify(mockSessionTokenMap).put(TEST_SESSION_ID, TEST_TOKEN);
    }
    
    @Test
    public void getCachedTokenShouldReturnNull() {
        assertNull(mockService.getCachedToken(null));
    }
    
    @Test
    public void getCachedTokenShouldGetTokenFromMap() {
        when(mockSessionTokenMap.get(anyString())).thenReturn(TEST_TOKEN);
        
        assertEquals(TEST_TOKEN, mockService.getCachedToken(TEST_SESSION_ID));
        verify(mockSessionTokenMap).get(TEST_SESSION_ID);
    }
}
