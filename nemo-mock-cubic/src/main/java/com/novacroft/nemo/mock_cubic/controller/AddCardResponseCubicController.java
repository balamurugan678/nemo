package com.novacroft.nemo.mock_cubic.controller;

import static com.novacroft.nemo.mock_cubic.constant.Constant.DEFAULT_COMMAND_NAME;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.mock_cubic.application_service.OysterCardDetailsService;
import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardDataService;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;


/**
 * Add a Card to the mock cubic response table.
 */
@Controller
@RequestMapping(value = "AddCardResponseNew.htm")
public class AddCardResponseCubicController {
    private static final String VIEW = "AddCardResponseNewView";

    @Autowired
    protected OysterCardDataService cardResponseService;

    @Autowired
    protected OysterCardDetailsService oysterCardDetailsService;
    
    @RequestMapping(method = RequestMethod.GET)
    public final ModelAndView showPage() {
        return new ModelAndView(VIEW, DEFAULT_COMMAND_NAME, new AddCardResponseCmd());
    }

    @RequestMapping(method = RequestMethod.POST)
    public final ModelAndView addCard(@ModelAttribute(DEFAULT_COMMAND_NAME) final AddCardResponseCmd cmd) {
        oysterCardDetailsService.createOrUpdateOysterCard(cmd);
        return new ModelAndView(VIEW);
    }
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_FREE_PREPAYTICKET_SLOT,method = RequestMethod.POST)
    public final ModelAndView freePrePayTicketSlots(@ModelAttribute(DEFAULT_COMMAND_NAME) final AddCardResponseCmd cmd) {
        oysterCardDetailsService.freePrePayTicketSlots(cmd);
        return new ModelAndView(VIEW);
    }
}
