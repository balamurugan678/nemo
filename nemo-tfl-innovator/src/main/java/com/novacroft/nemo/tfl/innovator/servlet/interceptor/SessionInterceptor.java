package com.novacroft.nemo.tfl.innovator.servlet.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.domain.InnovatorUser;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;

@Component
public class SessionInterceptor extends HandlerInterceptorAdapter{
    protected static final String INNOVATOR_USER = "INNOVATOR_USER";
    private static final String JSESSIONID = "JSESSIONID";
    private static final String COOKIE = "Cookie";
    private static final String LOGGED_IN_USER_JSON = "LOGGED_IN_USER_JSON";
    private String innovatorTimeOutURI;
    private String innovatorNemoURI;
    
    @Autowired
    private RestOperations restTemplate;
    @Autowired
    protected SystemParameterService systemParameterService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        checkURL();
        boolean noSession = checkForInnovatorSession(request.getSession());
        if (noSession) {
            response.sendRedirect(innovatorTimeOutURI);
            return false;
        }
        return true;
    }
    
    private void checkURL() {
        if (innovatorTimeOutURI == null){
            innovatorTimeOutURI = systemParameterService.getParameterValue(SystemParameterCode.INNOVATOR_SYSTEM_TIMEOUT_URI.code());
            innovatorNemoURI = systemParameterService.getParameterValue(SystemParameterCode.INNOVATOR_SYSTEM_NEMO_URI.code());
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private boolean checkForInnovatorSession(HttpSession session) {
        boolean noSession = false;
        if (session != null && (session.getAttribute(INNOVATOR_USER) == null || checkForNotLoggedIn((String) session.getAttribute(INNOVATOR_USER)))) {
            String nemoUri = String.format("%s?MODULE=&ACTION=%s&random=%s", innovatorNemoURI, LOGGED_IN_USER_JSON, Math.random());
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add(COOKIE, JSESSIONID + "=" + session.getId());
            HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

            ResponseEntity loggedInUserResponse = restTemplate.exchange(nemoUri, HttpMethod.GET, requestEntity, String.class);
            String innovatorUser = (String) loggedInUserResponse.getBody();
            noSession = checkForNotLoggedIn(innovatorUser);
            if (!noSession) {
                InnovatorUser innovatorUserObject = new Gson().fromJson(innovatorUser, InnovatorUser.class);
            }
            session.setAttribute(INNOVATOR_USER, innovatorUser);
        } else {
            noSession = true;
        }
        return noSession;
    }
    
    private boolean checkForNotLoggedIn(String search){
        if (search.indexOf("Not Logged In") > -1) {
            return true;
        }
        return false;
    }
    
    
}
