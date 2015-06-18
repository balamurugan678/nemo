package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.tfl.common.application_service.AutoLoadConfigurationChangePushToGateService;
import com.novacroft.nemo.tfl.common.application_service.AutoTopUpConfigurationService;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AutoLoadChangeSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

/**
 * Configure Oyster Card Auto Top Up (Autoload)
 */
@Service("autoTopUpConfigurationService")
public class AutoTopUpConfigurationServiceImpl implements AutoTopUpConfigurationService {
    protected static final Logger logger = LoggerFactory.getLogger(ReconcileAutoLoadChangeServiceImpl.class);

    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected AutoLoadConfigurationChangePushToGateService autoLoadConfigurationChangePushToGateService;
    @Autowired
    protected ApplicationEventService applicationEventService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected AutoLoadChangeSettlementDataService autoLoadChangeSettlementDataService;
    @Autowired
    protected LocationDataService locationDataService;

    @Override
    @Transactional
    public void changeConfiguration(Long cardId, Long orderId, Integer amount, Long pickUpLocation) {
        CardDTO cardDTO = this.cardDataService.findById(cardId);
        LocationDTO locationDTO = this.locationDataService.findById(pickUpLocation);
        try {
            Integer requestSequenceNumber =
                    pushToGate(cardDTO, AutoLoadState.lookUpState(amount), pickUpLocation, orderId, amount,
                            locationDTO.getName());
            createSettlement(cardId, orderId, amount, requestSequenceNumber, pickUpLocation);
            createEvent(orderId,
                    buildAdditionalInformation(cardDTO.getCardNumber(), amount, locationDTO.getName(), requestSequenceNumber));
        } catch (ApplicationServiceException e) {
            // no action required here
        }
    }

    protected Integer pushToGate(CardDTO cardDTO, Integer autoLoadState, Long pickUpLocation, Long orderId, Integer amount,
                                 String pickUpLocationName) {
        try {
            return this.autoLoadConfigurationChangePushToGateService
                    .requestAutoLoadConfigurationChange(cardDTO.getCardNumber(), autoLoadState, pickUpLocation);
        } catch (ApplicationServiceException e) {
            createEvent(orderId,
                    buildAdditionalInformation(cardDTO.getCardNumber(), amount, pickUpLocationName, e.getMessage()));
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    protected void createSettlement(Long cardId, Long orderId, Integer amount, Integer requestSequenceNumber,
                                    Long pickUpLocation) {
        AutoLoadChangeSettlementDTO autoLoadChangeSettlementDTO = new AutoLoadChangeSettlementDTO(orderId, SettlementStatus.REQUESTED.code(), amount, new Date(), cardId,
                        requestSequenceNumber, pickUpLocation.intValue(), AutoLoadState.lookUpState(amount));
        this.autoLoadChangeSettlementDataService.createOrUpdate(autoLoadChangeSettlementDTO);
    }

    protected void createEvent(Long orderId, String additionalInformation) {
        OrderDTO orderDTO = this.orderDataService.findById(orderId);
        this.applicationEventService.create(orderDTO.getCustomerId(), EventName.AUTO_LOAD_CHANGE,
                additionalInformation);
    }

    protected String buildAdditionalInformation(String cardNumber, Integer autoLoadState, String pickUpLocationName,
                                                Integer requestSequenceNumber) {
        return buildAdditionalInformation(cardNumber, autoLoadState, pickUpLocationName, requestSequenceNumber, null);
    }

    protected String buildAdditionalInformation(String cardNumber, Integer autoLoadState, String pickUpLocationName,
                                                String error) {
        return buildAdditionalInformation(cardNumber, autoLoadState, pickUpLocationName, null, error);
    }

    protected String buildAdditionalInformation(String cardNumber, Integer amount, String pickUpLocationName,
                                                Integer requestSequenceNumber, String error) {
        return new InformationBuilder().append("Auto Load Change Request").append("; amount [%s]", amount)
                .append("; pick up location [%s]", pickUpLocationName)
                .appendIfNotBlank("; request sequence number [%s]", requestSequenceNumber)
                .appendIfNotBlank("; error [%s]", error).append("; Oyster card number [%s]", cardNumber).toString();
    }
}
