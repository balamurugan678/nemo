package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.CookieConstant.COOKIE_HEADER_KEY;
import static com.novacroft.nemo.tfl.common.constant.CookieConstant.TOKEN_FIELD_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.TOKEN;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.NAME_VALUE_SEPARATOR;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_APP;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_PASSWORD;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_SESSION_ID;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_TOKEN;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_USER_NAME;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.SSO_BASE_URL_CODE;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.SSO_CHECK_ACTIVE_SESSION_PATH;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.SSO_LOGIN_PATH;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.SSO_UPDATE_MASTER_DATA_PATH;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.SSO_VALIDATE_TOKEN_PATH;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.utils.UriUrlUtil;
import com.novacroft.nemo.tfl.common.application_service.SingleSignOnTokenService;
import com.novacroft.nemo.tfl.common.util.CookieUtil;

@Service(value = "singleSignOnTokenService")
public class SingleSignOnTokenServiceImpl extends BaseService implements SingleSignOnTokenService {

    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter;

    @Override
    public Cookie requestToken(String username, String password, String appId, String sessionId) {
        MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<>();
        parameterMap.set(PARAMETER_USER_NAME, username);
        parameterMap.set(PARAMETER_PASSWORD, password);
        parameterMap.set(PARAMETER_APP, appId);
        parameterMap.set(PARAMETER_SESSION_ID, sessionId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResponseEntity<String> response = restTemplate
                        .postForEntity(getSsoURI(SSO_LOGIN_PATH), new HttpEntity<>(parameterMap, headers), String.class);
        HttpHeaders responseHeader = response.getHeaders();
        return CookieUtil.extractCookieFromHeader(responseHeader, TOKEN_FIELD_NAME);
    }

    @Override
    public Object validateTokenAndRetrieveUserDetails(String token) {
        restTemplate.getMessageConverters().add(mappingJacksonHttpMessageConverter);

        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.add(COOKIE_HEADER_KEY, TOKEN + NAME_VALUE_SEPARATOR + token);
        tokenHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(getSsoURI(SSO_VALIDATE_TOKEN_PATH), new HttpEntity<String>(null,
                        tokenHeaders), Object.class);
        return responseEntity.getBody();
    }

    protected URI getSsoURI(String path) {
        String ssoBaseUrl = getContent(SSO_BASE_URL_CODE);
        URI baseURI = UriUrlUtil.getUriFromAString(ssoBaseUrl);
        return UriUrlUtil.addPathToUri(baseURI, path);
    }

    @Override
    public Object checkIfSessionActive(String token, String appId, String sessionId) {
        restTemplate.getMessageConverters().add(mappingJacksonHttpMessageConverter);

        MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<>();
        parameterMap.set(PARAMETER_TOKEN, token);
        parameterMap.set(PARAMETER_APP, appId);
        parameterMap.set(PARAMETER_SESSION_ID, sessionId);

        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(getSsoURI(SSO_CHECK_ACTIVE_SESSION_PATH), new HttpEntity<>(parameterMap,
                        tokenHeaders), Object.class);
        return responseEntity.getBody();
    }

    @Override
    public Object updateMasterCustomerData(Map<String, String> changeset) {
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(mappingJacksonHttpMessageConverter);
        
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<>();
        parameterMap.setAll(changeset);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(getSsoURI(SSO_UPDATE_MASTER_DATA_PATH), new HttpEntity<>(parameterMap,
                        requestHeaders), Object.class);
        return responseEntity.getBody();
    }
}

