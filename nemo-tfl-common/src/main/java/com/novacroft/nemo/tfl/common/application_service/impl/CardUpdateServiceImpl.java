package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.CubicConstant.ADD;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.CUBIC_PASSWORD;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.CUBIC_USERID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.tfl.common.application_service.AutoLoadConfigurationChangePushToGateService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.impl.cubic.BaseCubicService;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.HotlistReasonTypes;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.TransferConstants;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardUpdateRequestDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.HotlistReasonDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayTicketRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayValueRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.HotlistReasonDTO;

@Service("cardUpdateService")
public class CardUpdateServiceImpl extends BaseCubicService implements CardUpdateService {

    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected CardUpdateRequestDataService cardUpdateRequestDataService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected AutoLoadConfigurationChangePushToGateService autoLoadConfigurationChangePushToGateService;
    @Autowired
    protected ApplicationEventService applicationEventService;
    @Autowired 
    protected CustomerDataService customerDataService;
    @Autowired
    protected HotlistReasonDataService hotlistReasonDataService;
    
    @Override
    public Integer requestCardUpdatePrePayTicket(String cardNumber, Integer productCode, String startDate, String expiryDate, Integer productPrice,
                    Long pickUpLocation) {
        CardUpdatePrePayTicketRequestDTO requestDTO = new CardUpdatePrePayTicketRequestDTO(
                        cardNumber, ADD, 
                        productCode, startDate, expiryDate, productPrice, pickUpLocation,  
                        this.systemParameterService.getParameterValue(CUBIC_USERID.code()),
                        this.systemParameterService.getParameterValue(CUBIC_PASSWORD.code()));
        CardUpdateResponseDTO responseDTO = this.cardUpdateRequestDataService.addPrePayTicket(requestDTO);
        if (responseDTO != null && isErrorResponse(responseDTO)) {
            throw new ApplicationServiceException(
                    String.format(PrivateError.CARD_UPDATE_REQUEST_FAILED.message(), responseDTO.getErrorCode(),
                            responseDTO.getErrorDescription()));
        }
        return (responseDTO != null)?responseDTO.getRequestSequenceNumber(): null;
    }
    @Override
    public Integer requestCardUpdatePrePayValue(String cardNumber, Long pickupLocation, Integer prePayValue, Integer currency, String cartType) {
        CardUpdatePrePayValueRequestDTO requestDTO = new CardUpdatePrePayValueRequestDTO(
                        cardNumber, ADD, 
                        pickupLocation, this.systemParameterService.getParameterValue(CUBIC_USERID.code()),
                        this.systemParameterService.getParameterValue(CUBIC_PASSWORD.code()),
                        prePayValue, currency);
        CardUpdateResponseDTO responseDTO = this.cardUpdateRequestDataService.addPrePayValue(requestDTO, cartType);
        if (isErrorResponse(responseDTO)) {
            throw new ApplicationServiceException(
                    String.format(PrivateError.CARD_UPDATE_REQUEST_FAILED.message(), responseDTO.getErrorCode(),
                            responseDTO.getErrorDescription()));
        }
        return responseDTO.getRequestSequenceNumber();
    }
    
    @Override
    public Integer requestCardAutoLoadChange(Long cardId, Long pickUpLocation, Integer autoLoadState) {
        CardDTO card = cardDataService.findById(cardId);
        CustomerDTO customerDTO = customerDataService.findById(card.getCustomerId());
        return requestCardAutoLoadChange(cardId, pickUpLocation, autoLoadState, customerDTO.getId());
    }
    
    @Override
    public Integer requestCardAutoLoadChange(Long cardId, Long pickUpLocation, Integer autoLoadState, Long customerId) {
        CardDTO card = cardDataService.findById(cardId);
        createApplicationEvent(cardId, pickUpLocation, autoLoadState, customerId);
        return autoLoadConfigurationChangePushToGateService.requestAutoLoadConfigurationChange(card.getCardNumber(), autoLoadState, pickUpLocation);
    }
    
    protected void createApplicationEvent(Long cardId, Long pickUpLocation, Integer autoLoadState, Long customerId) {
        this.applicationEventService.create(customerId, EventName.AUTO_LOAD_CHANGE, getContent(AutoLoadState.lookUpContentCode(autoLoadState)));
    }
    
    @Override
    public void createLostOrStolenEventForHotlistedCard(String cardNumber, Integer hotlistReasonId) {
        EventName eventName = this.getEventName(hotlistReasonId);
        Long customerId = customerDataService.findByCardNumber(cardNumber).getId();
        String additionalInformation = getAdditionalInformation(cardNumber, hotlistReasonId);
        this.applicationEventService.create(customerId, eventName, additionalInformation);
        eventName = this.getEventName(null);
        additionalInformation = getAdditionalInformation(cardNumber, null);
        this.applicationEventService.create(customerId, eventName, additionalInformation);
    }
    
    protected EventName getEventName(Integer hotlistReasonId) {
    	HotlistReasonDTO hotlistReasonDTO = hotlistReasonDataService.findByDescription(TransferConstants.CARD_TRANSFERRED);
        if (hotlistReasonId == HotlistReasonTypes.LOST_CARD.getCode()) {
            return EventName.OYSTER_CARD_LOST;
        } else if (hotlistReasonId == HotlistReasonTypes.STOLEN_CARD.getCode()) {
            return EventName.OYSTER_CARD_STOLEN;
        } else if (Integer.valueOf(hotlistReasonDTO.getId().intValue()).equals(hotlistReasonId)) {
            return EventName.OYSTER_CARD_TRANSFERRED;
        } else {
            return EventName.OYSTER_CARD_HOTLISTED;
        }
        
    }
    
    protected String getAdditionalInformation(String cardNumber, Integer hotlistReasonId) {
        InformationBuilder additionalInformation = new InformationBuilder();
        if (hotlistReasonId == HotlistReasonTypes.LOST_CARD.getCode()) {
            additionalInformation.append("Card [%s] reported lost by customer using Oyster Online.", cardNumber);            
        } else if (hotlistReasonId == HotlistReasonTypes.STOLEN_CARD.getCode()) {
            additionalInformation.append("Card [%s] reported stolen by customer using Oyster Online.", cardNumber);
        } else {
            additionalInformation.append("Card [%s] hotlisted by customer using Oyster Online.", cardNumber);
        }
        return additionalInformation.toString();
    }
    
}
