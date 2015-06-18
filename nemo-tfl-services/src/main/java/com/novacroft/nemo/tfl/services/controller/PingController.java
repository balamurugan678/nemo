package com.novacroft.nemo.tfl.services.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.novacroft.nemo.common.utils.DateUtil.formatDateAsISO8601;

/**
 * "ping" controller
 */
@Controller
@RequestMapping(value = "/ping")
public class PingController extends BaseServicesController {

    @RequestMapping(method = RequestMethod.GET, produces = JSON_MEDIA)
    @ResponseBody
    public Map getPing() {
        return doPing(RequestMethod.GET.name());
    }

    @RequestMapping(method = RequestMethod.PUT, produces = JSON_MEDIA)
    @ResponseBody
    public Map putPing() {
        return doPing(RequestMethod.PUT.name());
    }

    @RequestMapping(method = RequestMethod.POST, produces = JSON_MEDIA)
    @ResponseBody
    public Map postPing() {
        return doPing(RequestMethod.POST.name());
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = JSON_MEDIA)
    @ResponseBody
    public Map deletePing() {
        return doPing(RequestMethod.DELETE.name());
    }

    protected Map doPing(String method) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("method", method);
        map.put("pingedAt", formatDateAsISO8601(new Date()));
        return map;
    }
}
