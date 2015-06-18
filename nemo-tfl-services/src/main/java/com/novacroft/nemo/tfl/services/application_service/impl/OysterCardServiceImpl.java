package com.novacroft.nemo.tfl.services.application_service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.services.application_service.OysterCardService;
import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.converter.CardConverter;
import com.novacroft.nemo.tfl.services.transfer.Card;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;
import com.novacroft.nemo.tfl.services.util.WebServiceResultUtil;

@Service("oysterCardService")
public class OysterCardServiceImpl extends BaseService implements OysterCardService {
    static final Logger logger = LoggerFactory.getLogger(OysterCardServiceImpl.class);
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected CardConverter cardConverter;
    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CardDataService cardDataService;

    @Override
    public Card getCard(String cardNumber) {
        Card card = getCardFromCubic(cardNumber);
        CardDTO cardDTO = cardDataService.findByCardNumber(cardNumber);
        if (cardDTO != null) {
            card.setId(cardDTO.getExternalId());
        }
        return card;
    }

    @Override
    public WebServiceResult createOysterCard(Long externalCustomerId) {
        try {
            CustomerDTO customerDTO = customerDataService.findByExternalId(externalCustomerId);
            CardDTO cardDTO = customerService.createCard(customerDTO.getId(), null);
            if (cardDTO != null) {
                return WebServiceResultUtil.generateSuccessResult(cardDTO.getExternalId());
            } else {
                return WebServiceResultUtil.generateResult(externalCustomerId, null, WebServiceResultAttribute.CREATE_CARD_FAILURE.name(), null);
            }

        } catch (Exception e) {
            logger.error(String.format(PrivateError.CUSTOMER_NOT_FOUND.message(), externalCustomerId));
            return WebServiceResultUtil.generateResult(externalCustomerId, null, WebServiceResultAttribute.CUSTOMER_NOT_FOUND.name(),
                            getContent(WebServiceResultAttribute.CUSTOMER_NOT_FOUND.contentCode()));
        }
    }

    @Override
    public WebServiceResult createOysterCard(Long externalCustomerId, String cardNumber) {
        WebServiceResult result = new WebServiceResult();
        try {
            CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cardNumber);
            WebServiceResult cubicCallResult = rejectIfErrorWithCubicCall(cardInfoResponseV2DTO, externalCustomerId);
            if (cubicCallResult != null) {
                return cubicCallResult;
            }
            Long internalCustomerId = customerDataService.getInternalIdFromExternalId(externalCustomerId);
            WebServiceResult invalidCustomerResult = rejectIfInvalidCustomerId(internalCustomerId, externalCustomerId);
            if (invalidCustomerResult != null) {
                return invalidCustomerResult;
            }

            WebServiceResult cardAlreadyExistsResult = checkIfCardAlreadyExists(cardNumber, internalCustomerId);
            if (cardAlreadyExistsResult != null) {
                return cardAlreadyExistsResult;
            }

            CardDTO cardDTO = customerService.createCard(internalCustomerId, cardNumber);
            if (cardDTO != null) {
                result = WebServiceResultUtil.generateSuccessResult(cardDTO.getExternalId());
            } else {
                result = WebServiceResultUtil.generateResult(externalCustomerId, null, WebServiceResultAttribute.CARD_NOT_FOUND.name(),
                                getContent(WebServiceResultAttribute.CARD_NOT_FOUND.contentCode()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebServiceResultUtil.generateResult(externalCustomerId, null, WebServiceResultAttribute.FAILURE.name(), e.getMessage());
        }
        return result;
    }

    protected WebServiceResult checkIfCardAlreadyExists(String cardNumber, Long internalCustomerId) {
        CardDTO cardDTO = cardDataService.findByCustomerIdAndCardNumber(internalCustomerId, cardNumber);
        if (cardDTO != null) {
            return WebServiceResultUtil.generateResult(cardDTO.getExternalId(), null, WebServiceResultAttribute.CARD_ALREADY_ASSOCIATED_ERROR.name(),
                            getContent(WebServiceResultAttribute.CARD_ALREADY_ASSOCIATED_ERROR.contentCode()));
        } else {
            cardDTO = cardDataService.findByCardNumber(cardNumber);
            if (cardDTO != null) {
                return WebServiceResultUtil.generateResult(cardDTO.getExternalId(), null,
                                WebServiceResultAttribute.CARD_ALREADY_ASSOCIATED_TO_ANOTHER_CUSTOMER_ERROR.name(),
                                getContent(WebServiceResultAttribute.CARD_ALREADY_ASSOCIATED_TO_ANOTHER_CUSTOMER_ERROR.contentCode()));
            }
        }
        return null;
    }

    protected WebServiceResult rejectIfErrorWithCubicCall(CardInfoResponseV2DTO cardInfoResponseV2DTO, Long externalCustomerId) {
        if (null == cardInfoResponseV2DTO) {
            return WebServiceResultUtil.generateResult(externalCustomerId, null, WebServiceResultAttribute.CARD_NOT_FOUND.name(),
                            getContent(WebServiceResultAttribute.CARD_NOT_FOUND.contentCode()));
        }
        if (cardInfoResponseV2DTO.getErrorDescription() != null) {
            WebServiceResultAttribute cardWebServiceResult = WebServiceResultAttribute.getResultFromName(cardInfoResponseV2DTO.getErrorDescription());
            return WebServiceResultUtil.generateResult(externalCustomerId, null, cardWebServiceResult.name(),
                            getContent(cardWebServiceResult.contentCode()));
        }
        return null;
    }

    protected WebServiceResult rejectIfInvalidCustomerId(Long internalCustomerId, Long externalCustomerId) {
        if (internalCustomerId != null) {
            return null;
        }
        return WebServiceResultUtil.generateResult(externalCustomerId, null, WebServiceResultAttribute.CUSTOMER_NOT_FOUND.name(),
                        getContent(WebServiceResultAttribute.CUSTOMER_NOT_FOUND.contentCode()));
    }

    @Override
    public Card getCard(Long externalCardId) {
        Card card = new Card();
        CardDTO cardDTO = cardDataService.findByExternalId(externalCardId);
        if (cardDTO != null && cardDTO.getCardNumber() != null) {
            card = getCardFromCubic(cardDTO.getCardNumber());
            card.setId(externalCardId);
        } else if (cardDTO != null && cardDTO.getCardNumber() == null) {
            card = cardConverter.convert(cardDTO);
        } else {
            card.setErrorCode(WebServiceResultAttribute.CARD_NOT_FOUND.name());
            card.setErrorDescription(getContent(WebServiceResultAttribute.CARD_NOT_FOUND.contentCode()));
        }
        return card;
    }

    protected Card getCardFromCubic(String cardNumber) {
        Card card;
        try {
            CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cardNumber);
            card = cardConverter.convert(cardInfoResponseV2DTO);
        } catch (ApplicationServiceException e) {
            card = new Card();
            card.setErrorCode(e.getErrorCode());
            card.setErrorDescription(e.getMessage());
        }
        return card;
    }

    @Override
    public Card updateCard(Long externalCardId, Card card) {
        CardDTO cardDTO = cardDataService.findByExternalId(externalCardId);
        if (cardDTO != null) {
            WebServiceResult cardAlreadyExistsResult = checkIfCardAlreadyExists(card.getPrestigeId(), cardDTO.getCustomerId());
            if (cardAlreadyExistsResult == null) {
                cardDTO.setCardNumber(card.getPrestigeId());
                cardDataService.createOrUpdate(cardDTO);

                return getCardFromCubic(cardDTO.getCardNumber());
            } else {
                card.setErrorCode(cardAlreadyExistsResult.getResult());
                card.setErrorDescription(cardAlreadyExistsResult.getMessage());
            }
        } else {
            card.setErrorCode(WebServiceResultAttribute.CARD_NOT_FOUND.name());
            card.setErrorDescription(getContent(WebServiceResultAttribute.CARD_NOT_FOUND.contentCode()));
        }
        return card;
    }

}
