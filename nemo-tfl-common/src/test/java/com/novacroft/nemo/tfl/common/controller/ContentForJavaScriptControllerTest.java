package com.novacroft.nemo.tfl.common.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.CachedContentSerialisationService;
import com.novacroft.nemo.common.support.ReloadableDatabaseMessageSource;
import com.novacroft.nemo.tfl.common.application_service.ContentCodeSerialisationService;

public class ContentForJavaScriptControllerTest {
    private static final String TEST_CONTENT = "test-content";
    private static final String TEST_CONTENT_CODE = "test-content-code";
    
    private ContentForJavaScriptController controller;
    private CachedContentSerialisationService mockCachedContentService;
    private ContentCodeSerialisationService mockContentCodeService;
    private HttpServletResponse mockResponse;
    
    @Before
    public void setUp() {
        controller = new ContentForJavaScriptController();
        mockCachedContentService = mock(CachedContentSerialisationService.class);
        mockContentCodeService = mock(ContentCodeSerialisationService.class);
        mockResponse = mock(HttpServletResponse.class);
        
        controller.cachedContentSerialisationService = mockCachedContentService;
        controller.contentCodeSerialisationService = mockContentCodeService;
    }
    
    @Test
    public void shouldGetContentAsJavaScript() {
        when(mockCachedContentService.serialiseContent(anyString(), any(ReloadableDatabaseMessageSource.class)))
                .thenReturn(TEST_CONTENT);
        when(mockContentCodeService.serialiseContentCodes()).thenReturn(TEST_CONTENT_CODE);
        doNothing().when(mockResponse).setContentType(anyString());
        
        
        final String expectedResult = "var content=" + TEST_CONTENT + ";" +
                                      "var contentCode=" + TEST_CONTENT_CODE + ";";
        assertEquals(expectedResult, 
                        controller.getContentAsJavaScript(new Locale(""), mockResponse));
        verify(mockResponse).setContentType(anyString());
        verify(mockCachedContentService).serialiseContent(anyString(), any(ReloadableDatabaseMessageSource.class));
        verify(mockContentCodeService).serialiseContentCodes();
    }
}
