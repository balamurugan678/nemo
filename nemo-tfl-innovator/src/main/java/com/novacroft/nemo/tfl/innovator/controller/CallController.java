package com.novacroft.nemo.tfl.innovator.controller;

import com.novacroft.nemo.common.application_service.CallService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.command.impl.CallCmd;
import com.novacroft.nemo.common.validator.AddressValidator;
import com.novacroft.nemo.common.validator.CallValidator;
import com.novacroft.nemo.common.validator.ContactDetailsValidator;
import com.novacroft.nemo.common.validator.CustomerNameValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * controller for calls
 */

@Controller
@RequestMapping(value = "call")
public class CallController {
    static final Logger LOGGER = LoggerFactory.getLogger(CallController.class);
    public static final String VIEW_NAME = "CallView";
    public static final String COMMAND_NAME = "callCmd";

    @Autowired
    protected CallService callService;

    @Autowired
    protected SelectListService selectListService;

    @Autowired
    protected CustomerNameValidator customerNameValidator;

    @Autowired
    protected AddressValidator addressValidator;

    @Autowired
    protected ContactDetailsValidator contactDetailsValidator;

    @Autowired
    protected CallValidator callValidator;

    @ModelAttribute
    public void populateTitlesSelectList(Model model) {
        model.addAttribute("titles", selectListService.getSelectList("Titles"));
    }

    @ModelAttribute
    public void populateCallTypeSelectList(Model model) {
        model.addAttribute("callTypes", callService.getCallTypeSelectList());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView view(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        modelAndView.addObject(COMMAND_NAME, new CallCmd());
        return modelAndView;
    }

    @RequestMapping(params = "id", method = RequestMethod.GET)
    public ModelAndView load(@RequestParam("id") Long callId, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        CallCmd cmd = new CallCmd();
        if (callId != null) {
            cmd = callService.getCall(callId);
        } else {
            LOGGER.info("No id passed");
            modelAndView.addObject("error", "callid.parameter.error");
            modelAndView.addObject(COMMAND_NAME, cmd);
            return modelAndView;
        }
        modelAndView.addObject(COMMAND_NAME, cmd);
        request.setAttribute("id", cmd.getId());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute(COMMAND_NAME) CallCmd cmd, BindingResult result, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        this.addressValidator.validate(cmd, result);
        this.callValidator.validate(cmd, result);
        this.customerNameValidator.validate(cmd, result);
        this.contactDetailsValidator.validate(cmd, result);
        request.setAttribute("id", cmd.getId());
        if (!result.hasErrors()) {
            this.callService.updateCall(cmd);
        }
        modelAndView.addObject(COMMAND_NAME, cmd);
        return modelAndView;
    }

}
