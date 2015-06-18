package com.novacroft.nemo.tfl.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.novacroft.nemo.tfl.services.application_service.OysterCardService;
import com.novacroft.nemo.tfl.services.transfer.Card;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

@Controller
@RequestMapping(value = "/card")
public class CardController extends BaseServicesController {

    @Autowired
    protected OysterCardService oysterCardService;

    @RequestMapping(value = "/{cardNumber}", method = RequestMethod.GET, produces = JSON_MEDIA)
    @ResponseBody
    public Card getCardDetails(@PathVariable("cardNumber") String cardNumber) {
        return oysterCardService.getCard(cardNumber);
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.POST, produces = JSON_MEDIA)
    @ResponseBody
    public WebServiceResult createOysterCardForCustomer(@PathVariable("customerId") Long customerId) {
        return oysterCardService.createOysterCard(customerId);
    }

    @RequestMapping(value = "/{customerId}/{oysterCardNumber}", method = RequestMethod.PUT, produces = JSON_MEDIA)
    @ResponseBody
    public WebServiceResult createOysterCard(@PathVariable Long customerId, @PathVariable String oysterCardNumber) {
        return oysterCardService.createOysterCard(customerId, oysterCardNumber);
    }

    @RequestMapping(value = "id/{cardId}", method = RequestMethod.GET, produces = JSON_MEDIA)
    @ResponseBody
    public Card getCardFromExternalId(@PathVariable Long cardId) {
        return oysterCardService.getCard(cardId);
    }
    
    @RequestMapping(value = "/{cardId}", method = RequestMethod.PUT, produces = JSON_MEDIA, consumes=JSON_MEDIA)
    @ResponseBody
    public Card updateCardFromExternald(@PathVariable Long cardId, @RequestBody Card card){
        return oysterCardService.updateCard(cardId, card);
    }
}
