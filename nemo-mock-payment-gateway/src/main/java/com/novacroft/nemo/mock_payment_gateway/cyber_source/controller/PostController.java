package com.novacroft.nemo.mock_payment_gateway.cyber_source.controller;

import com.google.gson.Gson;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service.PostSigningService;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service.PostTransactionService;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.CommandName;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.RequestName;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.ViewName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.RequestParameter.TARGET_ACTION_SIGN;

/**
 * CyberSource Secure Acceptance standard transaction request controller
 */
@Controller
@RequestMapping(value = RequestName.GATEWAY_REQUEST)
public class PostController {
    @Autowired
    protected PostTransactionService postTransactionService;
    @Autowired
    protected PostSigningService postSigningService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView transactionRequest(@RequestParam Map<String, String> requestParameterMap) {
        return new ModelAndView(ViewName.TRANSACTION_REQUEST, CommandName.COMMAND,
                this.postTransactionService.getRequestAndConfiguration(requestParameterMap));
    }

    @RequestMapping(params = TARGET_ACTION_SIGN, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String signRequest(@RequestParam Map<String, String> parameters) {
        this.postSigningService.signPostReply(parameters);
        return new Gson().toJson(parameters);
    }
}
