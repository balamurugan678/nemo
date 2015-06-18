package com.novacroft.nemo.mock_single_sign_on.controller;


import static com.novacroft.nemo.mock_single_sign_on.constant.LogonRestURIConstants.POST_MOCK_LOGOUT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MockLogoutController {
    
    protected static final Logger logger = LoggerFactory.getLogger(MockLogoutController.class);
    
    @RequestMapping(value=POST_MOCK_LOGOUT, method=RequestMethod.POST)
    @ResponseBody
    public void postLogout(@RequestParam("appId") String appId, @CookieValue("token") String token) {
        StringBuilder infoBuilder = new StringBuilder("This is only to show logout request was post here. ")
                                            .append("[AppID = ").append(appId)
                                            .append(", Token = ").append(token)
                                            .append("]");
        logger.debug(infoBuilder.toString());
    }
}
