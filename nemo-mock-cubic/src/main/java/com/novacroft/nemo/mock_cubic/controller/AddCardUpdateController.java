package com.novacroft.nemo.mock_cubic.controller;

import static com.novacroft.nemo.common.utils.UriUrlUtil.addPathToUri;
import static com.novacroft.nemo.common.utils.UriUrlUtil.getApplicationBaseUri;
import static com.novacroft.nemo.mock_cubic.constant.Constant.ADD_CARD_UPDATE_URL;
import static com.novacroft.nemo.mock_cubic.constant.Constant.ADD_CARD_UPDATE_VIEW;
import static com.novacroft.nemo.mock_cubic.constant.Constant.DEFAULT_COMMAND_NAME;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.mock_cubic.application_service.UpdateCardService;
import com.novacroft.nemo.mock_cubic.command.AddRequestCmd;
import com.novacroft.nemo.mock_cubic.command.RemoveRequestCmd;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;

/**
 * Add a Card to the mock cubic response table.
 */
@Controller
@RequestMapping(value = ADD_CARD_UPDATE_URL)
public class AddCardUpdateController {

    private static final String PATH = "service/main";
    
    @Autowired
    protected UpdateCardService updateCardService;

    @Autowired
    protected XmlModelConverter<CardUpdatePrePayTicketRequest> cardUpdatePrePayTicketRequestConverter;
    @Autowired
    protected XmlModelConverter<CardUpdatePrePayValueRequest> cardUpdatePrePayValueRequestConverter;
    @Autowired
    protected XmlModelConverter<CardRemoveUpdateRequest> cardRemoveUpdateRequestConverter;
    @Autowired
    protected XmlModelConverter<RequestFailure> requestFailureConverter;

    RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(method = RequestMethod.GET)
    public final ModelAndView showPage() {
        return new ModelAndView(ADD_CARD_UPDATE_VIEW, DEFAULT_COMMAND_NAME, new AddRequestCmd());
    }

    @RequestMapping(method = RequestMethod.POST, params = "update")
    public final ModelAndView updateCard(@ModelAttribute("updateCmd") final AddRequestCmd cmd) {
        final ModelAndView modelAndView = new ModelAndView(ADD_CARD_UPDATE_VIEW);
        cmd.setAction("ADD");

        updateCardService.updatePending(cmd);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, params = "updateXML")
    public final ModelAndView updateXml(@ModelAttribute("updateXmlCmd") final AddRequestCmd cmd, HttpServletRequest request) {
        final ModelAndView modelAndView = new ModelAndView(ADD_CARD_UPDATE_VIEW);
        cmd.setAction("ADD");

        String xmlRequest;

        if (cmd.getPrePayValue() != null) {
            CardUpdatePrePayValueRequest ppvRequest = createPpvRequest(cmd);
            xmlRequest = cardUpdatePrePayValueRequestConverter.convertModelToXml(ppvRequest);
        } else {
            CardUpdatePrePayTicketRequest pptRequest = createPptRequest(cmd);
            xmlRequest = cardUpdatePrePayTicketRequestConverter.convertModelToXml(pptRequest);
        }

        URI uri = addPathToUri(getApplicationBaseUri(request), PATH); 
        restTemplate.postForEntity(uri, xmlRequest, String.class);
        
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, params = "remove")
    public final ModelAndView removePending(@ModelAttribute("removeCmd") final RemoveRequestCmd cmd) {
        final ModelAndView modelAndView = new ModelAndView(ADD_CARD_UPDATE_VIEW);
        cmd.setAction("REMOVE");

        updateCardService.remove(cmd);

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, params = "removeXML")
    public final ModelAndView removePendingXml(@ModelAttribute("removeXmlCmd") final RemoveRequestCmd cmd, HttpServletRequest request) {
        final ModelAndView modelAndView = new ModelAndView(ADD_CARD_UPDATE_VIEW);
        cmd.setAction("REMOVE");

        CardRemoveUpdateRequest removeRequest = createRemoveRequest(cmd);
        String xmlRequest = cardRemoveUpdateRequestConverter.convertModelToXml(removeRequest);

        URI uri = addPathToUri(getApplicationBaseUri(request), PATH); 
        restTemplate.postForEntity(uri, xmlRequest, String.class);

        return modelAndView;
    }

    protected CardUpdatePrePayValueRequest createPpvRequest(AddRequestCmd cmd) {
        return new CardUpdatePrePayValueRequest(cmd.getRealTimeFlag(), cmd.getPrestigeId(), cmd.getAction(), cmd.getPickupLocation(),
                        ifNotNullConvertToInteger(cmd.getPaymentMethod()), cmd.getUserId(), cmd.getPassword(),
                        ifNotNullConvertToInteger(cmd.getPrePayValue()), ifNotNullConvertToInteger(cmd.getCurrency()));
    }

    protected CardUpdatePrePayTicketRequest createPptRequest(AddRequestCmd cmd) {
        return new CardUpdatePrePayTicketRequest(cmd.getRealTimeFlag(), cmd.getPrestigeId(), cmd.getAction(), 
                        ifNotNullConvertToInteger(cmd.getProductCode()), cmd.getStartDate(), cmd.getExpiryDate(),
                        ifNotNullConvertToInteger(cmd.getCurrency()), ifNotNullConvertToInteger(cmd.getProductPrice()), cmd.getPickupLocation(),
                        ifNotNullConvertToInteger(cmd.getPaymentMethod()), cmd.getUserId(), cmd.getPassword());
    }

    protected CardRemoveUpdateRequest createRemoveRequest(RemoveRequestCmd cmd) {
        return new CardRemoveUpdateRequest(cmd.getPrestigeId(), cmd.getAction(), cmd.getOriginalRequestSequenceNumber(), cmd.getUserId(),
                        cmd.getPassword());
    }

    protected Integer ifNotNullConvertToInteger(Long l) {
        return (l == null) ? null : Integer.valueOf(l.intValue());
    }
}