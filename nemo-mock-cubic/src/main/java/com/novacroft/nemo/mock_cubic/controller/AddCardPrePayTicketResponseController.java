package com.novacroft.nemo.mock_cubic.controller;

import static com.novacroft.nemo.common.utils.StringUtil.isEmpty;
import static com.novacroft.nemo.mock_cubic.constant.Constant.ADD_CARD_PREPAY_TICKET_RESPONSE_URL;
import static com.novacroft.nemo.mock_cubic.constant.Constant.ADD_CARD_PREPAY_TICKET_RESPONSE_VIEW;
import static com.novacroft.nemo.mock_cubic.constant.Constant.DEFAULT_COMMAND_NAME;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

import com.novacroft.nemo.mock_cubic.command.AddCardPrePayTicketCmd;
import com.novacroft.nemo.mock_cubic.constant.CardAction;
import com.novacroft.nemo.mock_cubic.service.card.AddPrePayTicketService;
import com.novacroft.nemo.mock_cubic.service.card.CardResponseService;

/**
 * Add a Card to the mock cubic response table.
 */
@Controller
@RequestMapping(value = ADD_CARD_PREPAY_TICKET_RESPONSE_URL)
public class AddCardPrePayTicketResponseController {
    

    @Autowired
    protected AddPrePayTicketService addPrePayTicketService;

    @Autowired
    protected CardResponseService cardResponseService;

    @RequestMapping(method = RequestMethod.GET)
    public final ModelAndView showPage() {
        return new ModelAndView(ADD_CARD_PREPAY_TICKET_RESPONSE_VIEW, DEFAULT_COMMAND_NAME, new AddCardPrePayTicketCmd());
    }

    @RequestMapping(method = RequestMethod.POST)
    public final ModelAndView addCard(@ModelAttribute(DEFAULT_COMMAND_NAME) final AddCardPrePayTicketCmd cmd) {
        final ModelAndView modelAndView = new ModelAndView(ADD_CARD_PREPAY_TICKET_RESPONSE_VIEW);
        final Document xml = addPrePayTicketService.createPrePayTicketResponse(cmd);
        cardResponseService.addGetCardResponse(cmd.getPrestigeId(), xml,
                (!isEmpty(cmd.getErrorDescription())) ? CardAction.UPDATE_CARD_ERROR : CardAction.UPDATE_CARD);
        return modelAndView;
    }

    public final CardResponseService getCardResponseService() {
        return cardResponseService;
    }

    public final void setCardResponseService(final CardResponseService cardResponseService) {
        this.cardResponseService = cardResponseService;
    }
}