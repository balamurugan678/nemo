package com.novacroft.nemo.mock_payment_gateway.cyber_source.controller;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service.SoapSettingsService;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.command.SoapSettingsCmd;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.CommandName;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.RequestName;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.RequestParameter;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.ViewName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Soap web service reply settings controller
 */
@Controller
@RequestMapping(value = RequestName.SOAP_SETTINGS)
public class SoapSettingsController {

    @Autowired
    protected SoapSettingsService soapSettingsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView show() {
        return new ModelAndView(ViewName.SOAP_SETTINGS, CommandName.COMMAND, this.soapSettingsService.getSettings());

    }

    @RequestMapping(params = RequestParameter.TARGET_ACTION_UPDATE, method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute(CommandName.COMMAND) SoapSettingsCmd cmd) {
        this.soapSettingsService.updateSettings(cmd);
        return new ModelAndView(ViewName.SOAP_SETTINGS, CommandName.COMMAND, this.soapSettingsService.getSettings());
    }

    @RequestMapping(params = RequestParameter.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel(@ModelAttribute(CommandName.COMMAND) SoapSettingsCmd cmd) {
        return new ModelAndView(ViewName.SOAP_SETTINGS, CommandName.COMMAND, this.soapSettingsService.getSettings());
    }
}
