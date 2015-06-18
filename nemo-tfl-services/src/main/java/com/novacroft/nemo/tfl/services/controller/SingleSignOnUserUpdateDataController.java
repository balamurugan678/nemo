package com.novacroft.nemo.tfl.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.novacroft.nemo.tfl.services.application_service.SingleSignOnUpdateService;

@Controller
@RequestMapping(value = "/SSOUpdateUser")
public class SingleSignOnUserUpdateDataController extends BaseServicesController {

    @Autowired
    protected SingleSignOnUpdateService ssoUpdateService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(@RequestBody String user) {

        return this.ssoUpdateService.updateUser(user);
    }

}
