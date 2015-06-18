package com.novacroft.nemo.tfl.common.controller;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.PAFService;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.constant.ContentCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "findAddress")
public class FindAddressController {
    static final Logger logger = LoggerFactory.getLogger(FindAddressController.class);

    @Autowired
    protected PAFService pafService;
    @Autowired
    protected PostcodeValidator postcodeValidator;

    @RequestMapping(method = RequestMethod.POST, value = "ajax.htm")
    @ResponseBody
    public String findAddress(HttpServletRequest request) throws Exception {
        String postcodeList = "";
        String postcode = ServletRequestUtils.getStringParameter(request, "postcode");
        if (postcodeValidator.validate(postcode)) {
            String[] addressesForPostcode = pafService.getAddressesForPostcode(postcode);
            postcodeList = new Gson().toJson(addressesForPostcode);
        } else {
            postcodeList = ContentCode.INVALID_POSTCODE.codeStem();
        }
        return postcodeList;
    }
}
