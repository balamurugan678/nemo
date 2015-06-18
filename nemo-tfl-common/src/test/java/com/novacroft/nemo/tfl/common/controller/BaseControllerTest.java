package com.novacroft.nemo.tfl.common.controller;

import com.novacroft.nemo.common.exception.ControllerException;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.novacroft.nemo.test_support.UriTestUtil.*;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.USERNAME_1;
import static com.novacroft.nemo.tfl.common.controller.BaseController.ANONYMOUS_USER;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * Unit tests for BaseController
 */
public class BaseControllerTest {
    
    private BaseController controller;
    private SecurityService mockSecurityService;
    private ApplicationContext mockAppContext;
    private HttpSession mockHttpSession;
    
    @Before
    public void setUp() {
        controller = mock(BaseController.class, CALLS_REAL_METHODS);
        mockSecurityService = mock(SecurityService.class);
        mockAppContext = mock(ApplicationContext.class);
        mockHttpSession = mock(HttpSession.class);
        
        setField(controller, "securityService", mockSecurityService);
        controller.setApplicationContext(mockAppContext);
    }

    @Test
    public void addLoggedInUsernameShouldCallSecurityService() {
        controller.addLoggedInUsername();
        verify(mockSecurityService).getLoggedInUsername();
    }

    @Test
    public void userShouldNotBeLoggedInWithWithNullUsername() {
        assertFalse(controller.addIsUserLoggedIn(null));
    }

    @Test
    public void userShouldNotBeLoggedInWithWithBlankUsername() {
        assertFalse(controller.addIsUserLoggedIn(StringUtil.EMPTY_STRING));
    }

    @Test
    public void userShouldNotBeLoggedInWithWithAnonymousUsername() {
        assertFalse(controller.addIsUserLoggedIn(ANONYMOUS_USER));
    }

    @Test
    public void userShouldBeLoggedIn() {
        when(mockSecurityService.getLoggedInUsername()).thenReturn(USERNAME_1);
        
        assertTrue(controller.addIsUserLoggedIn(USERNAME_1));
    }

    @Test
    public void shouldAddBaseUriToModel() throws URISyntaxException {
        final URI EXPECTED_RESULT = new URI(String.format("%s://%s:%s", SCHEME_1, HOST_1, PORT_1));

        Model model = new ExtendedModelMap();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme(SCHEME_1);
        request.setServerName(HOST_1);
        request.setServerPort(PORT_1);

        controller.addBaseUri(model, request);

        assertEquals(EXPECTED_RESULT, model.asMap().get(PageAttribute.BASE_URI));
    }

    @Test
    public void shouldStreamFile() throws IOException {
        byte[] testFileContent = "test".getBytes();

        MockHttpServletResponse mockResponse = mock(MockHttpServletResponse.class);
        doNothing().when(mockResponse).setContentType(anyString());
        doNothing().when(mockResponse).setHeader(anyString(), anyString());

        OutputStream mockOutputStream = mock(OutputStream.class);
        doNothing().when(mockOutputStream).write(any(byte[].class));
        doNothing().when(mockOutputStream).flush();
        doNothing().when(mockOutputStream).close();

        controller.streamFile(mockResponse, mockOutputStream, "test-file", "test-content-type", testFileContent);

        verify(mockResponse).setContentType(anyString());
        verify(mockResponse).setHeader(anyString(), anyString());
        verify(mockOutputStream).write(any(byte[].class));
        verify(mockOutputStream).flush();
        verify(mockOutputStream).close();
    }

    @Test(expected = ControllerException.class)
    public void streamFileShouldError() throws IOException {
        byte[] testFileContent = "test".getBytes();

        MockHttpServletResponse mockResponse = mock(MockHttpServletResponse.class);
        doNothing().when(mockResponse).setContentType(anyString());
        doNothing().when(mockResponse).setHeader(anyString(), anyString());

        OutputStream mockOutputStream = mock(OutputStream.class);
        doThrow(new IOException("test-error")).when(mockOutputStream).write(any(byte[].class));
        doNothing().when(mockOutputStream).flush();
        doNothing().when(mockOutputStream).close();

        controller.streamFile(mockResponse, mockOutputStream, "test-file", "test-content-type", testFileContent);
    }
    
    @Test
    public void getContentWithStringArguments() {
        controller.getContent(StringUtil.EMPTY_STRING);
        verify(mockAppContext).getMessage(anyString(), any(Object[].class), any(Locale.class));
    }
    
    @Test
    public void getContentWithMessageSourceResolvableArguments() {
        MessageSourceResolvable mockMessageSourceResolvable = mock(MessageSourceResolvable.class);
        controller.getContent(mockMessageSourceResolvable, null);
        
        verify(mockAppContext).getMessage(mockMessageSourceResolvable, null);
    }
    
    @Test
    public void getClientIpAddress() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        controller.getClientIpAddress(mockRequest);
        verify(mockRequest).getRemoteAddr();
    }
    
    @Test
    public void getRedirectViewWithoutExposedAttributes() {
        RedirectView actualResult = controller.getRedirectViewWithoutExposedAttributes(URI_1);
        
        assertNotNull(actualResult);
        assertEquals(URI_1, actualResult.getUrl());
    }
    
    @Test
    public void getFromSessionShouldCallSessionGet() {
        controller.getFromSession(mockHttpSession, PARAMETER_1);
        verify(mockHttpSession).getAttribute(PARAMETER_1);
    }
    
    @Test
    public void deleteAttributeFromSessionShouldCallSessionRemove() {
        controller.deleteAttributeFromSession(mockHttpSession, PARAMETER_1);
        verify(mockHttpSession).removeAttribute(PARAMETER_1);
    }
    
    @Test
    public void addAttributeToSessionShouldCallSessionSet() {
        controller.addAttributeToSession(mockHttpSession, PARAMETER_1, VALUE_1);
        verify(mockHttpSession).setAttribute(PARAMETER_1, VALUE_1);
    }
    
    @Test
    public void getLoggedInUsernameShouldCallSecurityService() {
        controller.getLoggedInUsername();
        verify(mockSecurityService).getLoggedInUsername();
    }
    
    @Test
    public void shouldDecodeURL() {
        assertEquals(URI_1, controller.decodeURL(URI_1));
    }
}
