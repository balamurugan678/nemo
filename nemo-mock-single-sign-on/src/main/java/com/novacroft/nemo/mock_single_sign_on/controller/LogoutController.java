package com.novacroft.nemo.mock_single_sign_on.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.mock_single_sign_on.command.LogoutCmd;
import com.novacroft.nemo.mock_single_sign_on.constant.Page;
import com.novacroft.nemo.mock_single_sign_on.service.LogonService;
import com.novacroft.nemo.mock_single_sign_on.service.TokenCacheService;

@Controller
@RequestMapping(value = Page.LOGOUT)
public class LogoutController {
    private static final String LOGOUT_VIEW = "LogoutView";
    private static final String LOGOUT_CMD = "LogoutCmd"; 

    protected Map<String, String> appIdNameMap;
    
    @Autowired
    protected LogonService logonService;
    @Autowired
    protected TokenCacheService tokenCacheService;
    
    @Value("${apps}")
    private String appIdMap;
    
    @PostConstruct
    public void init() {
        appIdNameMap = new HashMap<String, String>(logonService.convert(appIdMap));
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayLoggedUser(@ModelAttribute(LOGOUT_CMD) LogoutCmd logoutCmd, 
                    @RequestParam(value = "returnURL", required = false) String returnURL,
                    @RequestParam(value = "sessionId", required = false) String sessionId,
                    HttpServletRequest httpRequest) {
        String requestSessionId = sessionId;
        requestSessionId = StringUtils.defaultIfBlank(requestSessionId, httpRequest.getRequestedSessionId());
        
        String token = tokenCacheService.getCachedToken(requestSessionId);
        if (token != null) {
            logoutCmd.setReturnURL(returnURL);
            String username = tokenCacheService.getCachedUsername(token);
            List<String> appIds = tokenCacheService.getCachedApps(token);
            List<String> loggedInApps = new ArrayList<>();
            for (String id : appIds) {
                if (appIdNameMap.containsKey(id)) {
                    loggedInApps.add(appIdNameMap.get(id));
                }
            }
            
            logoutCmd.setUsername(username);
            logoutCmd.setApps(loggedInApps);
            logoutCmd.setSessionId(requestSessionId);
        }
        
        return new ModelAndView(LOGOUT_VIEW, LOGOUT_CMD, logoutCmd);
    }
}
