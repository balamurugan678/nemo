package com.novacroft.nemo.tfl.common.servlet.filter;

import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.Before;
import org.junit.Test;

/**
 * Cross site scripting filter unit tests
 */
public class CrossSiteScriptingFilterTest {
    
    private CrossSiteScriptingFilter mockCrossSiteScriptingFilter;
    private ServletRequest mockServletRequest;
    private ServletResponse mockServletResponse;
    private FilterChain mockFilterChain;
    
    @Before
    public void setUp() {
        mockCrossSiteScriptingFilter = mock(CrossSiteScriptingFilter.class);
        mockServletRequest = mock(ServletRequest.class);
        mockServletResponse = mock(ServletResponse.class);
        mockFilterChain = mock(FilterChain.class);
    }
    
    @Test
    public void doFilterShouldEncode() throws IOException, ServletException {
        mockCrossSiteScriptingFilter.doFilter(mockServletRequest, mockServletResponse, mockFilterChain);
    }
    
}
