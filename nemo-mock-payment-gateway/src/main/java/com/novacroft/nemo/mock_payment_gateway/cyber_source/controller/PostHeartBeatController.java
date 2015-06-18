package com.novacroft.nemo.mock_payment_gateway.cyber_source.controller;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration.PostSettings;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.RequestName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * HTTP Post heart beat controller
 */
@Controller
@RequestMapping(value = RequestName.HEARTBEAT)
public class PostHeartBeatController {
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> status() {
        PostSettings postSettings = PostSettings.getInstance();
        return new ResponseEntity<String>(postSettings.getAlive() ? HttpStatus.OK : HttpStatus.I_AM_A_TEAPOT);
    }
}
