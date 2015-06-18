package com.novacroft.nemo.tfl.common.application_service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

public class SingleSignOnSessionServiceImplTest {
    private static final String TEST_TOKEN_VALUE_1 = "TestToken1";
    private static final String TEST_TOKEN_VALUE_2 = "TestToken2";
    private static final String TEST_SESSION_ID_1 = "ABCDEFG";
    private static final String TEST_SESSION_ID_2 = "XYZ";
    
    private SingleSignOnSessionServiceImpl sessionService;
    
    @Before
    public void setUp() {
        sessionService = new SingleSignOnSessionServiceImpl();
    }
    
    @Test
    public void shouldAddSessionByToken() {
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getId()).thenReturn(TEST_SESSION_ID_1);
        
        sessionService.addSessionByToken(TEST_TOKEN_VALUE_1, mockSession);
        
        assertTrue(SingleSignOnSessionServiceImpl.TOKEN_SESSION_MAP.containsKey(TEST_TOKEN_VALUE_1));
        assertTrue(SingleSignOnSessionServiceImpl.ID_TOKEN_MAP.containsKey(TEST_SESSION_ID_1));
    }
    
    @Test
    public void sessionShouldNotBeAddedIfBlankToken() {
        HttpSession mockSession = mock(HttpSession.class);
        int mapSize = SingleSignOnSessionServiceImpl.TOKEN_SESSION_MAP.size();
        
        sessionService.addSessionByToken(null, mockSession);
        
        assertTrue(SingleSignOnSessionServiceImpl.TOKEN_SESSION_MAP.size() == mapSize);
    }
    
    @Test
    public void removeSessionByTokenShouldReturnNull() {
        assertNull(sessionService.removeSessionByToken(null));
    }
    
    @Test
    public void shouldRemoveSessionByToken() {
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getId()).thenReturn(TEST_SESSION_ID_2);
        
        sessionService.addSessionByToken(TEST_TOKEN_VALUE_2, mockSession);
        
        assertEquals(mockSession, sessionService.removeSessionByToken(TEST_TOKEN_VALUE_2));
        assertFalse(SingleSignOnSessionServiceImpl.TOKEN_SESSION_MAP.containsKey(TEST_TOKEN_VALUE_2));
        assertFalse(SingleSignOnSessionServiceImpl.ID_TOKEN_MAP.containsValue(TEST_TOKEN_VALUE_2));
    }
    
    @Test
    public void shouldRemoveSessionByID() {
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getId()).thenReturn(TEST_SESSION_ID_2);
        
        sessionService.addSessionByToken(TEST_TOKEN_VALUE_2, mockSession);
        
        assertEquals(mockSession, sessionService.removeSessionById(TEST_SESSION_ID_2));
        assertFalse(SingleSignOnSessionServiceImpl.ID_TOKEN_MAP.containsKey(TEST_SESSION_ID_2));
        assertFalse(SingleSignOnSessionServiceImpl.TOKEN_SESSION_MAP.containsKey(TEST_TOKEN_VALUE_2));
    }
}
