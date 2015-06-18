package com.novacroft.nemo.common.application_service.impl;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Locale;

import static com.novacroft.nemo.common.constant.LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * BaseService unit tests
 */
public class BaseServiceTest {

    protected static final String TEST_CODE = "test-code";
    protected static final String TEST_MESSAGE = "test-message";
    protected static final String[] TEST_ARGUMENTS = new String[]{"test-argument-1", "test-argument-2"};

    @Test
    public void getContentWithCode() {
        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        when(mockApplicationContext.getMessage(anyString(), isNull(Object[].class), isNull(Locale.class)))
                .thenReturn(TEST_MESSAGE);
        BaseService service = mock(BaseService.class, CALLS_REAL_METHODS);
        service.applicationContext = mockApplicationContext;
        assertEquals(TEST_MESSAGE, service.getContent(TEST_CODE));
        verify(service).getContent(anyString());
        verify(service, never()).getContent(anyString(), (String) anyVararg());
        verify(service, never()).getContent(anyString(), any(Locale.class), (String) anyVararg());
    }

    @Test
    public void getContentWithCodeAndArguments() {
        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        when(mockApplicationContext.getMessage(anyString(), any(Object[].class), isNull(Locale.class)))
                .thenReturn(TEST_MESSAGE);
        BaseService service = mock(BaseService.class, CALLS_REAL_METHODS);
        service.applicationContext = mockApplicationContext;
        assertEquals(TEST_MESSAGE, service.getContent(TEST_CODE, TEST_ARGUMENTS));
        verify(service, never()).getContent(anyString());
        verify(service).getContent(anyString(), (String) anyVararg());
        verify(service, never()).getContent(anyString(), any(Locale.class), (String) anyVararg());
    }

    @Test
    public void getContentWithCodeLocaleAndArguments() {
        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        when(mockApplicationContext.getMessage(anyString(), any(Object[].class), any(Locale.class))).thenReturn(TEST_MESSAGE);
        BaseService service = mock(BaseService.class, CALLS_REAL_METHODS);
        service.applicationContext = mockApplicationContext;
        assertEquals(TEST_MESSAGE, service.getContent(TEST_CODE, ENGLISH_UNITED_KINGDOM_LOCALE, TEST_ARGUMENTS));
        verify(service, never()).getContent(anyString());
        verify(service, never()).getContent(anyString(), (String) anyVararg());
        verify(service).getContent(anyString(), any(Locale.class), (String) anyVararg());
    }
}
