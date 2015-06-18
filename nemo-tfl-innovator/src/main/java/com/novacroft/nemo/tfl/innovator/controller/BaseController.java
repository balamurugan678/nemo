package com.novacroft.nemo.tfl.innovator.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestOperations;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SystemParameterService;

public class BaseController {
    protected static final String INNOVATOR_USER = "INNOVATOR_USER";
    private static final String JSESSIONID = "JSESSIONID";
    private static final String COOKIE = "Cookie";
    private static final String LOGGED_IN_USER_JSON = "LOGGED_IN_USER_JSON";
    @Autowired
    private RestOperations restTemplate;
    @Autowired
    protected SystemParameterService systemParameterService;

    /* TODO: code commented out for development purposes - will be re-instated for production
        @ModelAttribute
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public void getInnovatorSession(HttpSession session) {
            if (session.getAttribute(INNOVATOR_USER) == null) {
                String innovatorNemoURI = systemParameterService.getParameterValue(SystemParameterCode
                .INNOVATOR_SYSTEM_NEMO_URI.code());
                String innovatorNemoURI = systemParameterService.getParameterValue(SystemParameterCode
                .INNOVATOR_SYSTEM_NEMO_URI.code());
                String nemoUri = String.format("%s?MODULE=&ACTION=%s&random=%s", innovatorNemoURI, LOGGED_IN_USER_JSON,
                Math.random());
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.add(COOKIE, JSESSIONID + "=" + session.getId());
                HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

                 * ResponseEntity rssResponse1 = restTemplate.exchange(
                 * "http://localhost/InnovatorNew/servlet/nemo?MODULE=&ACTION=LOGGED_IN_USER_JSON&random=0
                 * .10967199234535785", HttpMethod.GET,
                 * requestEntity, String.class); String rss1 = (String) rssResponse1.getBody();

                ResponseEntity loggedInUserResponse = restTemplate.exchange(nemoUri, HttpMethod.GET, requestEntity,
                String.class);
                String innovatorUser = (String) loggedInUserResponse.getBody();
                session.setAttribute(INNOVATOR_USER, innovatorUser);
            }
        }
        */
    public String getClientIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
    
    public RedirectView getRedirectViewWithoutExposedAttributes(String url) {
        RedirectView redirectView = new RedirectView(url);
        redirectView.setExposeModelAttributes(false);
        return redirectView;
    }
}
