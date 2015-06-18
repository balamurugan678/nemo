package com.novacroft.nemo.mock_payment_gateway.cyber_source.controller;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.CommandName;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.RequestName;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.ViewName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Parameter inspector controller
 */
@Controller
@RequestMapping(value = RequestName.PARAMETER_SPY)
public class ParameterSpyController {
    @RequestMapping
    public ModelAndView showParameters(@RequestParam Map<String, String> parameterMap) {
        ModelAndView modelAndView = new ModelAndView(ViewName.PARAMETER_SPY);
        modelAndView.addObject(CommandName.PARAMETER_MAP, parameterMap);
        return modelAndView;
    }
}
