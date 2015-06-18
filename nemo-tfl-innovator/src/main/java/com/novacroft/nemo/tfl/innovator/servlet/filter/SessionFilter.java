package com.novacroft.nemo.tfl.innovator.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.domain.InnovatorUser;

public class SessionFilter implements Filter {
    protected static final String INNOVATOR_USER = "INNOVATOR_USER";
    protected static final String INNOVATOR_USER_OBJECT = "INNOVATOR_USER_OBJECT";
    private static final String JSESSIONID = "JSESSIONID";
    private static final String COOKIE = "Cookie";
    private static final String LOGGED_IN_USER_JSON = "LOGGED_IN_USER_JSON";
    private String innovatorSystemTimeOutURI;
    private String innovatorSystemNemoURI;
    private Boolean debugMode;

    @Autowired
    private RestOperations restTemplate;
    @Autowired
    protected SystemParameterService systemParameterService;

    @Override
    public void destroy() {
        return;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        boolean noSession = false;
        if (!debugMode) {
            noSession = checkForInnovatorSession(request.getSession(false));
        }
        if (noSession && !debugMode) {
            response.sendRedirect(innovatorSystemTimeOutURI);
        } else {
            chain.doFilter(req, res);
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private boolean checkForInnovatorSession(HttpSession session) {
        boolean noSession = false;
        if (session != null && (session.getAttribute(INNOVATOR_USER) == null || checkForNotLoggedIn((String) session.getAttribute(INNOVATOR_USER)))) {
            String nemoUri = String.format(innovatorSystemNemoURI, LOGGED_IN_USER_JSON, Math.random());
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add(COOKIE, JSESSIONID + "=" + session.getId());
            HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

            ResponseEntity loggedInUserResponse = restTemplate.exchange(nemoUri, HttpMethod.GET, requestEntity, String.class);
            String innovatorUser = (String) loggedInUserResponse.getBody();
            noSession = checkForNotLoggedIn(innovatorUser);
            if (!noSession) {
                InnovatorUser innovatorUserObject = new Gson().fromJson(innovatorUser, InnovatorUser.class);
                session.setAttribute(INNOVATOR_USER_OBJECT, innovatorUserObject);
            }
            session.setAttribute(INNOVATOR_USER, innovatorUser);
        } else {
            noSession = true;
        }
        return noSession;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        innovatorSystemTimeOutURI = config.getInitParameter("innovatorSystemTimeOutURI");
        innovatorSystemNemoURI = config.getInitParameter("innovatorSystemNemoURI");
        debugMode = (config.getInitParameter("debugMode").equalsIgnoreCase("true"));
    }

    private boolean checkForNotLoggedIn(String search){
        if (search.indexOf("Not Logged In") > -1) {
            return true;
        }
        return false;
    }

    public void setRestTemplate(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setSystemParameterService(SystemParameterService systemParameterService) {
        this.systemParameterService = systemParameterService;
    }

}