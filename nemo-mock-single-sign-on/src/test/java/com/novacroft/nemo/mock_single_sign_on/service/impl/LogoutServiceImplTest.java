package com.novacroft.nemo.mock_single_sign_on.service.impl;

import static com.novacroft.nemo.test_support.CookieTestUtil.TEST_TOKEN;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

public class LogoutServiceImplTest {
    private static final String TEST_URL_1 = "testUrl1";
    private static final String TEST_URL_2 = "testUrl2";

    @Mock
    private LogoutServiceImpl mockLogoutService;

    private MappingJacksonHttpMessageConverter mockJsonConverter;
    private RestTemplate mockTemplate;
    private MockRestServiceServer mockRestServer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockJsonConverter = new MappingJacksonHttpMessageConverter();
        mockTemplate = new RestTemplate();

        mockLogoutService.mappingJacksonHttpMessageConverter = mockJsonConverter;
        mockLogoutService.restTemplate = mockTemplate;

        mockRestServer = MockRestServiceServer.createServer(mockLogoutService.restTemplate);
    }

    @Test
    public void singleSignOutShouldInvokePostLogout() {
        doCallRealMethod().when(mockLogoutService).singleSignOut(anyListOf(String.class), anyString());

        List<String> testUrls = Arrays.asList(TEST_URL_1, TEST_URL_2);
        mockLogoutService.singleSignOut(testUrls, TEST_TOKEN);

        verify(mockLogoutService, times(2)).postLogout(anyString(), anyString());
    }

    @Test
    public void postLogoutShouldPostResttemplate() {
        doCallRealMethod().when(mockLogoutService).postLogout(anyString(), anyString());
        String testJson = new Gson().toJson("");

        mockRestServer.expect(requestTo(TEST_URL_1)).andExpect(method(HttpMethod.POST)).andRespond(withSuccess(testJson, MediaType.APPLICATION_JSON));

        mockLogoutService.postLogout(TEST_URL_1, TEST_TOKEN);

        mockRestServer.verify();
    }
}
