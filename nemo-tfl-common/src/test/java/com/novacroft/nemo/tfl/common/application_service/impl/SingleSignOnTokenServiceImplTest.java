package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CookieTestUtil.TOKEN_ATTRIBUTE_VALUE;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.novacroft.nemo.tfl.common.command.impl.LoginCmdImpl;

public class SingleSignOnTokenServiceImplTest {
    private final String SSO_BASE_URL = "http://localhost/nemo-mock-single-sign-on";
    private final String SSO_LOGIN_URI = SSO_BASE_URL + "/Login";
    private final String SSO_VALIDATE_TOKEN_URI = SSO_BASE_URL + "/validatetoken";
    private final String SSO_CHECK_ACTIVE_SESSION_URI = SSO_BASE_URL + "/checkactivesession";
    private final String SSO_UPDATE_CUSTOMER_DATA_URI = SSO_BASE_URL + "/UpdateUserCustomer";
    
    private final String APP_NAME = "app";
    private final String SESSION_ID = "session id";
    
    private SingleSignOnTokenServiceImpl service;
    private MappingJacksonHttpMessageConverter mockJsonConverter;
    private RestTemplate mockTemplate;
    private MockRestServiceServer mockServer;
    private LoginCmdImpl testLoginCmd;

    @Before
    public void setUp() {
        service = mock(SingleSignOnTokenServiceImpl.class);
        when(service.getSsoURI(anyString())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(SSO_BASE_URL);
        
        mockJsonConverter = new MappingJacksonHttpMessageConverter();
        mockTemplate = new RestTemplate();
        
        service.mappingJacksonHttpMessageConverter = mockJsonConverter;
        service.restTemplate = mockTemplate;
        
        mockServer = MockRestServiceServer.createServer(service.restTemplate);
        
        testLoginCmd = new LoginCmdImpl();
        testLoginCmd.setUsername("nemo");
        testLoginCmd.setPassword("nemo");
        
    }
    
    @Test
    public void requestTokenShouldPostData() {
        when(service.requestToken(anyString(), anyString(), anyString(), anyString())).thenCallRealMethod();
        
        mockServer.expect(requestTo(SSO_LOGIN_URI))
                  .andExpect(method(HttpMethod.POST))
                  .andRespond(withSuccess(TOKEN_ATTRIBUTE_VALUE, MediaType.TEXT_PLAIN));
        
        service.requestToken(testLoginCmd.getUsername(), testLoginCmd.getPassword(), APP_NAME, SESSION_ID);
        mockServer.verify();
    }
    
    @Test
    public void validateTokenOnSsoShouldPostData() {
        when(service.validateTokenAndRetrieveUserDetails(anyString())).thenCallRealMethod();
        
        String mockResponseJson = new Gson().toJson(testLoginCmd);
        mockServer.expect(requestTo(SSO_VALIDATE_TOKEN_URI))
                  .andExpect(method(HttpMethod.POST))
                  .andRespond(withSuccess(mockResponseJson, MediaType.APPLICATION_JSON));
        
        service.validateTokenAndRetrieveUserDetails(TOKEN_ATTRIBUTE_VALUE);
        mockServer.verify();
    }
    
    @Test
    public void checkIfSessionActiveShouldPostData() {
        when(service.checkIfSessionActive(anyString(), anyString(), anyString())).thenCallRealMethod();
        
        String mockResponseJson = new Gson().toJson(testLoginCmd);
        mockServer.expect(requestTo(SSO_CHECK_ACTIVE_SESSION_URI))
                  .andExpect(method(HttpMethod.POST))
                  .andRespond(withSuccess(mockResponseJson, MediaType.APPLICATION_JSON));
        
        service.checkIfSessionActive(TOKEN_ATTRIBUTE_VALUE, APP_NAME, SESSION_ID);
        mockServer.verify();
    }
    
    @Test
    public void updateMasterCustomerDataShouldPostData() {
        when(service.updateMasterCustomerData(anyMap())).thenCallRealMethod();
        
        String mockResponseJson = new Gson().toJson(testLoginCmd);
        mockServer.expect(requestTo(SSO_UPDATE_CUSTOMER_DATA_URI))
                  .andExpect(method(HttpMethod.POST))
                  .andRespond(withSuccess(mockResponseJson, MediaType.APPLICATION_JSON));
        
        service.updateMasterCustomerData(new HashMap<String, String>());
        mockServer.verify();
    }
}
