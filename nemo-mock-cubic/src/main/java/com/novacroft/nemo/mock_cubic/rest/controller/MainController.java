package com.novacroft.nemo.mock_cubic.rest.controller;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.exception.ControllerException;
import com.novacroft.nemo.mock_cubic.application_service.UpdateCardService;
import com.novacroft.nemo.mock_cubic.service.EditAutoLoadChangeService;
import com.novacroft.nemo.mock_cubic.service.card.CardResponseService;
import com.novacroft.nemo.tfl.common.domain.cubic.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.novacroft.nemo.mock_cubic.constant.Constant.ENCODING;
import static com.novacroft.nemo.mock_cubic.service.EditAutoLoadChangeServiceImpl.CARD_NOT_FOUND_ERROR_CODE;
import static com.novacroft.nemo.mock_cubic.service.EditAutoLoadChangeServiceImpl.CARD_NOT_FOUND_ERROR_DESCRIPTION;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Controller to handle all requests to the service. This controller will be used to read the xml request sent in a determine the route.
 */
@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private static final String EQUALS = "=";

    @Autowired
    protected CardResponseService cardResponseService;
    @Autowired
    protected EditAutoLoadChangeService editAutoLoadChangeService;
    @Autowired
    protected UpdateCardService updateCardService;
    @Autowired
    protected XmlModelConverter<CardInfoRequestV2> cardInfoRequestV2Converter;
    @Autowired
    protected XmlModelConverter<RequestFailure> requestFailureConverter;

    @RequestMapping(method = RequestMethod.POST, value = "/main")
    @ResponseBody
    public String handleRequest(@RequestBody String requestBody) {
        String response = "";
        logger.debug(String.format("requestBody [%s]", requestBody));

        String xmlAsString = getXmlAsStringFromRequestBody(requestBody);
        logger.debug(String.format("xmlAsString [%s]", xmlAsString));

        Object requestModel = this.cardInfoRequestV2Converter.convertXmlToObject(xmlAsString);
        logger.debug(String.format("requestModel [%s]", requestModel.toString()));

        response = callService(requestModel);
        logger.debug(String.format("response [%s]", response));

        return response;
    }

    public String callService(final Object requestModel) {
        String response = "";
        if (requestModel instanceof CardInfoRequestV2) {
            response = cardResponseService.getCardDetails((CardInfoRequestV2) requestModel);
        } else if (requestModel instanceof AutoLoadRequest) {
            response = this.editAutoLoadChangeService.getAutoLoadChangeResponse((AutoLoadRequest) requestModel);
        } else if (requestModel instanceof CardUpdatePrePayTicketRequest) {
            response = this.updateCardService.update((CardUpdatePrePayTicketRequest) requestModel);
        } else if (requestModel instanceof CardUpdatePrePayValueRequest) {
            response = this.updateCardService.update((CardUpdatePrePayValueRequest) requestModel);
        } else if (requestModel instanceof CardRemoveUpdateRequest) {
            response = this.updateCardService.remove((CardRemoveUpdateRequest) requestModel);
        }
        return isNotBlank(response) ? response : getCardNotFoundResponse();
    }

    protected String getXmlAsStringFromRequestBody(final String requestBody) {
        try {
            return stripTrailingEquals(URLDecoder.decode(requestBody, ENCODING));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            throw new ControllerException(e.getMessage(), e);
        }
    }

    protected String stripTrailingEquals(String value) {
        if (value.endsWith(EQUALS)) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    protected String getCardNotFoundResponse() {
        RequestFailure requestFailure = new RequestFailure(CARD_NOT_FOUND_ERROR_CODE, CARD_NOT_FOUND_ERROR_DESCRIPTION);
        return this.requestFailureConverter.convertModelToXml(requestFailure);
    }

}
