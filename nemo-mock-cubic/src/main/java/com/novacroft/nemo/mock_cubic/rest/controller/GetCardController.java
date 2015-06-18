package com.novacroft.nemo.mock_cubic.rest.controller;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.exception.ControllerException;
import com.novacroft.nemo.common.utils.XMLUtil;
import com.novacroft.nemo.mock_cubic.application_service.OysterCardDetailsService;
import com.novacroft.nemo.mock_cubic.command.GetCardCmd;
import com.novacroft.nemo.mock_cubic.constant.CardAction;
import com.novacroft.nemo.mock_cubic.service.card.CardResponseService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.converter.cubic.GetCardConverter;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;

import static com.novacroft.nemo.common.utils.Converter.convert;
import static com.novacroft.nemo.mock_cubic.constant.Constant.ENCODING;

/**
 * Controller for card response.
 */
@Controller
public class GetCardController {
    public static final String VIEW = "GetCardView";
    private static final Logger logger = LoggerFactory.getLogger(GetCardController.class);

    @Autowired
    protected CardResponseService cardResponseService;

    @Autowired
    protected OysterCardDetailsService oysterCardDetailsService;

    @Autowired
    protected GetCardService getCardService;

    @Autowired
    protected XmlModelConverter<CardInfoResponseV2> cardInfoResponseV2Converter;

    @Autowired
    protected GetCardConverter getCardConverter;

    @ModelAttribute
    public void populateActions(Model model) {
        model.addAttribute("cardActions", new ArrayList<CardAction>(Arrays.asList(CardAction.values())));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCard")
    public final ModelAndView load() {
        return new ModelAndView(VIEW, PageCommand.GET_CARD, new GetCardCmd());
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_GET_CARD, method = RequestMethod.POST, value = "/getCard")
    public final ModelAndView getCard(@ModelAttribute(PageCommand.GET_CARD) final GetCardCmd cmd, final BindingResult result) {
        cmd.setResponse(cardResponseService.getCardDetails(cmd.getPrestigeId(), cmd.getCardAction().code()));
        return new ModelAndView(VIEW, PageCommand.GET_CARD, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SEND_XML_REQUEST, method = RequestMethod.POST,
            value = "/getCard")
    public final ModelAndView getCardXml(@ModelAttribute(PageCommand.GET_CARD) final GetCardCmd cmd,
                                         final BindingResult result) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cmd.getPrestigeId());
        cardInfoResponseV2DTO = getCardService.checkAndPopulateNodesExcludingLeafNodes(cardInfoResponseV2DTO);
        CardInfoResponseV2 cardInfoResponseV2 = new CardInfoResponseV2();
        convert(cardInfoResponseV2DTO, cardInfoResponseV2);
        cmd.setResponse(cardInfoResponseV2Converter.convertModelToXml(cardInfoResponseV2));
        return new ModelAndView(VIEW, PageCommand.GET_CARD, cmd);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getCard")
    @ResponseBody
    public final String getCard(@RequestBody final String body) {
        String cardDetails = "";
        try {
            final Document xmlRequest = XMLUtil.convertStringToXmlDocument(URLDecoder.decode(body, ENCODING));
            cardDetails = cardResponseService.getCardDetails(xmlRequest);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            throw new ControllerException(e.getMessage(), e);
        }
        return cardDetails;
    }

    public final CardResponseService getCardResponseService() {
        return cardResponseService;
    }

    public final void setCardResponseService(final CardResponseService cardResponseService) {
        this.cardResponseService = cardResponseService;
    }
}
