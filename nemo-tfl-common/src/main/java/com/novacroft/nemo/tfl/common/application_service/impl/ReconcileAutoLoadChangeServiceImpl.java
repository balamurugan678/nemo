package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.tfl.common.application_service.ReconcileAutoLoadChangeService;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AutoLoadChangeSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Reconcile auto load changes reported by CUBIC, via the Auto Load Change batch file, with auto load change requests
 */
@Service("reconcileAutoLoadChangeService")
public class ReconcileAutoLoadChangeServiceImpl implements ReconcileAutoLoadChangeService {
    protected static final Logger logger = LoggerFactory.getLogger(ReconcileAutoLoadChangeServiceImpl.class);
    protected static final String OK = "OK";
    @Autowired
    protected AutoLoadChangeSettlementDataService autoLoadChangeSettlementDataService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected ApplicationEventService applicationEventService;

    @Override
    public void reconcileChange(Integer requestSequenceNumber, String cardNumber, Integer newAutoLoadConfiguration,
                                String statusOfAttemptedAction, Integer failureReasonCode) {
        AutoLoadChangeSettlementDTO autoLoadChangeSettlementDTO = getSettlement(requestSequenceNumber, cardNumber);
        updateSettlement(autoLoadChangeSettlementDTO, statusOfAttemptedAction);
        createEvent(autoLoadChangeSettlementDTO, isNewAutoLoadConfigurationSameAsRequested(newAutoLoadConfiguration,
                autoLoadChangeSettlementDTO.getAutoLoadState()) ?
                buildAdditionalInformation(cardNumber, statusOfAttemptedAction, failureReasonCode) :
                buildAdditionalInformation(cardNumber, statusOfAttemptedAction, failureReasonCode, newAutoLoadConfiguration,
                        autoLoadChangeSettlementDTO.getAutoLoadState()));
    }

    protected AutoLoadChangeSettlementDTO getSettlement(Integer requestSequenceNumber, String cardNumber) {
        AutoLoadChangeSettlementDTO autoLoadChangeSettlementDTO = this.autoLoadChangeSettlementDataService
                .findByRequestSequenceNumberAndCardNumber(requestSequenceNumber, cardNumber);
        if (autoLoadChangeSettlementDTO == null) {
            String message =
                    String.format(PrivateError.AUTO_LOAD_CHANGE_REQUEST_NOT_FOUND.message(), requestSequenceNumber, cardNumber);
            logger.error(message);
            throw new ApplicationServiceException(message);
        }
        return autoLoadChangeSettlementDTO;
    }

    protected void updateSettlement(AutoLoadChangeSettlementDTO autoLoadChangeSettlementDTO, String statusOfAttemptedAction) {
        autoLoadChangeSettlementDTO.setStatus(resolveStatus(statusOfAttemptedAction).code());
        this.autoLoadChangeSettlementDataService.createOrUpdate(autoLoadChangeSettlementDTO);
    }

    protected SettlementStatus resolveStatus(String statusOfAttemptedAction) {
        return OK.equalsIgnoreCase(statusOfAttemptedAction) ? SettlementStatus.COMPLETE : SettlementStatus.FAILED;
    }

    protected void createEvent(AutoLoadChangeSettlementDTO autoLoadChangeSettlementDTO, String additionalInformation) {
        OrderDTO orderDTO = this.orderDataService.findById(autoLoadChangeSettlementDTO.getOrderId());
        CustomerDTO customerDTO = customerDataService.findById(orderDTO.getCustomerId());
        this.applicationEventService.create(customerDTO.getId(), EventName.AUTO_LOAD_CHANGE,
                additionalInformation);
    }

    protected String buildAdditionalInformation(String cardNumber, String statusOfAttemptedAction, Integer failureReasonCode) {
        return new InformationBuilder().append("status [%s]", statusOfAttemptedAction)
                .append("; failure reason code [%s]", failureReasonCode).append("; Oyster card number [%s]", cardNumber)
                .toString();
    }

    protected String buildAdditionalInformation(String cardNumber, String statusOfAttemptedAction, Integer failureReasonCode,
                                                Integer newAutoLoadConfiguration, Integer requestedAutoLoadConfiguration) {
        return new InformationBuilder().append("", requestedAutoLoadConfiguration, newAutoLoadConfiguration)
                .append("new configuration [%s] does not match requested configuration [%s]; ", requestedAutoLoadConfiguration,
                        newAutoLoadConfiguration)
                .append(buildAdditionalInformation(cardNumber, statusOfAttemptedAction, failureReasonCode)).toString();
    }

    protected boolean isNewAutoLoadConfigurationSameAsRequested(Integer newAutoLoadConfiguration,
                                                                Integer requestedAutoLoadConfiguration) {
        return newAutoLoadConfiguration.equals(requestedAutoLoadConfiguration);
    }
}
