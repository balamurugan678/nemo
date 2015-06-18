package com.novacroft.nemo.mock_cubic.controller;

import static com.novacroft.nemo.mock_cubic.constant.Constant.DEFAULT_COMMAND_NAME;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;
import com.novacroft.nemo.mock_cubic.constant.CardAction;
import com.novacroft.nemo.mock_cubic.service.card.CardResponseService;


/**
 * Add a Card to the mock cubic response table.
 */
@Controller
@RequestMapping(value = "AddCardResponse.htm")
public class AddCardResponseController {
    private static final String VIEW = "AddCardResponseView";

    @Autowired
    protected CardResponseService cardResponseService;

    @RequestMapping(method = RequestMethod.GET)
    public final ModelAndView showPage() {

        return new ModelAndView(VIEW, DEFAULT_COMMAND_NAME, new AddCardResponseCmd());
    }

    @RequestMapping(method = RequestMethod.POST)

    public final ModelAndView addCard(@ModelAttribute(DEFAULT_COMMAND_NAME) final AddCardResponseCmd cmd) {
        final ModelAndView modelAndView = new ModelAndView(VIEW);
        final Document xml = cardResponseService.createGetCardResponseXML(cmd);
        cardResponseService.addGetCardResponse(cmd.getPrestigeId(), xml, CardAction.GET_CARD);
        return modelAndView;
    }

    public final CardResponseService getCardResponseService() {
        return cardResponseService;
    }

    public final void setCardResponseService(final CardResponseService cardResponseService) {
        this.cardResponseService = cardResponseService;
    }
}
