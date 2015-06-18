package com.novacroft.nemo.mock_payment_gateway.cyber_source.controller;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service.PostSettingsService;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.command.PostSettingsCmd;
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
 * Post web service reply settings controller
 */
@Controller
@RequestMapping(value = RequestName.POST_SETTINGS)
public class PostSettingsController {
    @Autowired
    protected PostSettingsService postSettingsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView show() {
        return new ModelAndView(ViewName.POST_SETTINGS, CommandName.COMMAND, this.postSettingsService.getSettings());

    }

    @RequestMapping(params = RequestParameter.TARGET_ACTION_UPDATE, method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute(CommandName.COMMAND) PostSettingsCmd cmd) {
        this.postSettingsService.updateSettings(cmd);
        return new ModelAndView(ViewName.POST_SETTINGS, CommandName.COMMAND, this.postSettingsService.getSettings());
    }

    @RequestMapping(params = RequestParameter.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel(@ModelAttribute(CommandName.COMMAND) PostSettingsCmd cmd) {
        return new ModelAndView(ViewName.POST_SETTINGS, CommandName.COMMAND, this.postSettingsService.getSettings());
    }
}
