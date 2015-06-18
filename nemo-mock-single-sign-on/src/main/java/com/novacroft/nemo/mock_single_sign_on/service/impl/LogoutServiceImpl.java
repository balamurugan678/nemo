package com.novacroft.nemo.mock_single_sign_on.service.impl;

import static com.novacroft.nemo.mock_single_sign_on.constant.LogonRestURIConstants.COOKIE_HEADER_KEY;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_APP_ID;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameter.PARAMETER_TOKEN;
import static com.novacroft.nemo.mock_single_sign_on.constant.PageParameterArgument.NAME_VALUE_SEPARATOR;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.novacroft.nemo.mock_single_sign_on.service.LogoutService;

@Service("logoutService")
public class LogoutServiceImpl implements LogoutService {
    protected static final Logger logger = LoggerFactory.getLogger(LogoutServiceImpl.class);
    private static final String SSO_APP_ID = "ssoServerID";
    private static final String HTTP_SCHEMA = "http://";
    
    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter;
    
    @Override
    public void singleSignOut(List<String> logoutUrls, String token) {
        for (String url : logoutUrls) {
            postLogout(HTTP_SCHEMA + url, token);
        }
    }

    protected void postLogout(String url, String token) {
        restTemplate.getMessageConverters().add(mappingJacksonHttpMessageConverter);
        
        MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<>();
        parameterMap.set(PARAMETER_APP_ID, SSO_APP_ID);
        
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.add(COOKIE_HEADER_KEY, PARAMETER_TOKEN + NAME_VALUE_SEPARATOR + token);
        tokenHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        
        HttpEntity<?> requestEntity= new HttpEntity<>(parameterMap, tokenHeaders);
        
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, requestEntity, Object.class);
        if (HttpStatus.OK != responseEntity.getStatusCode()) {
            logger.debug("Single Sign Out Failed on: " + url);
        }
    }
}
