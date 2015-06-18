package com.novacroft.nemo.mock_single_sign_on.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.mock_single_sign_on.command.LoginCmd;
import com.novacroft.nemo.mock_single_sign_on.data_service.MasterCustomerDataService;
import com.novacroft.nemo.mock_single_sign_on.service.LogonService;
import com.novacroft.nemo.mock_single_sign_on.service.TokenCacheService;
import com.novacroft.nemo.tfl.common.controller.BaseController;

@Controller
@RequestMapping(value = "Login")
public class LoginController extends BaseController {

    private static final int FIVE_MINUTE_EXPIRY = 300;
    private static final int TOKEN_LENGTH = 8;
    private static final String LOGIN_CMD = "LoginCmd";
    
    protected Map<String, String> applicationIdMap;
    @Autowired
    protected LogonService logonService;
    @Autowired
    protected MasterCustomerDataService masterCustomerDataService;
    @Autowired
    protected TokenCacheService tokenCacheService;
    

    @Value("${apps}")
    private String appIdMap;


    @ModelAttribute
    public void populateModelAttributes(Model model) {
        applicationIdMap = new HashMap<String, String>(logonService.convert(appIdMap));
        SelectListDTO list = new SelectListDTO();
        for (Map.Entry<String, String> entry : applicationIdMap.entrySet()) {
            SelectListOptionDTO option = new SelectListOptionDTO();
            option.setValue(entry.getKey());
            option.setMeaning(entry.getValue());
            list.getOptions().add(option );
        }
      
        list.setName("appList");
        model.addAttribute("appList", list);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loginPage(@ModelAttribute(LOGIN_CMD) LoginCmd cmd) {
       

        return new ModelAndView("LoginView", LOGIN_CMD, cmd);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object authenticateLoginRequestAndGenerateToken(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(LOGIN_CMD) LoginCmd cmd,
            @RequestParam(value="Username", required=true, defaultValue="") String name,
            @RequestParam(value="Password", required=true, defaultValue="") String password,
            @RequestParam(value="App", required=true, defaultValue="") String appId, 
            @RequestParam(value="returnURL", required=false, defaultValue="World") String returnURL,
            @RequestParam(value="errorURL", required=false, defaultValue="World") String errorURL,
            @RequestParam(value="sessionId", required=false) String sessionId
            ){
        String username = StringUtils.defaultString(name);
        username = StringUtils.defaultIfBlank(username, cmd.getUserName());
        
        String loggedInApp = StringUtils.defaultString(appId);
        loggedInApp = StringUtils.defaultIfBlank(loggedInApp, cmd.getApp());
        
        String requestSessionId = sessionId;
        requestSessionId = StringUtils.defaultIfBlank(requestSessionId, request.getRequestedSessionId());
        
        if (masterCustomerDataService.isUsernameExisting(username)) {
            Cookie cookie = new Cookie("token", RandomStringUtils.randomAlphabetic(TOKEN_LENGTH));
            cookie.setPath("/");
            cookie.setMaxAge(FIVE_MINUTE_EXPIRY);
            response.addCookie(cookie);
            
            tokenCacheService.saveTokenToCache(cookie.getValue(), username, loggedInApp, requestSessionId);
        }
        return new ModelAndView("LoginView", LOGIN_CMD, cmd);
    }

}
