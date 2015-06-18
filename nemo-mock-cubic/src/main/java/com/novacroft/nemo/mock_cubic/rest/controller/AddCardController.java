package com.novacroft.nemo.mock_cubic.rest.controller;

import static com.novacroft.nemo.common.utils.Converter.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.mock_cubic.application_service.OysterCardDetailsService;
import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

@Controller
public class AddCardController {

    private static final Integer ZERO = 0;
    private static final Integer ONE = 1;
    private static final String JSON_MEDIA = "application/json";

    @Autowired
    protected OysterCardDetailsService oysterCardDetailsService;
    @Autowired
    protected GetCardService getCardService;

    @RequestMapping(value = "/card/add", method = RequestMethod.PUT, consumes = JSON_MEDIA, produces = JSON_MEDIA)
    @ResponseBody
    public CardInfoResponseV2 addCard(@RequestBody AddCardResponseCmd cmd) {
        CardInfoResponseV2 response = new CardInfoResponseV2();
        if (cmd != null && cmd.getPrestigeId() != null && StringUtil.isNotEmpty(cmd.getPrestigeId())) {
            setDefaultValuesforRequiredFields(cmd);
            oysterCardDetailsService.createOrUpdateOysterCard(cmd);

            CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cmd.getPrestigeId());
            convert(cardInfoResponseV2DTO, response);
            return response;
        }
        return response;
    }

    protected void setDefaultValuesforRequiredFields(AddCardResponseCmd cmd) {
        if (cmd.getCurrency() == null) {
            cmd.setCurrency(ZERO);
        }

        if (cmd.getAutoloadState() == null) {
            cmd.setAutoloadState(ONE);
        }
    }
}
