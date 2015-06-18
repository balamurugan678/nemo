package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.common.utils.UriUrlUtil.getUriFromAString;

import org.springframework.beans.factory.annotation.Autowired;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.tfl.common.application_service.EmailMessageService;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.BaseAdhocLoadNotificationService;
import com.novacroft.nemo.tfl.common.application_service.impl.BaseEmailPreparationService;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

/**
 * Base service for ad hoc load notifications
 *
 * <p>ad hoc load notifications include amount, pick up location, card number and pick up expiry date.</p>
 */
public abstract class BaseAdHocLoadNotificationServiceImpl extends BaseEmailPreparationService implements BaseAdhocLoadNotificationService {
    @Autowired
    protected LocationDataService locationDataService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected ApplicationEventService applicationEventService;
    @Autowired
    protected AdHocLoadSettlementDataService adHocLoadSettlementDataService;

    
    @Override
    public Boolean hasAdHocLoadBeenRequested(Integer requestSequenceNumber, String cardNumber) {
        return (this.adHocLoadSettlementDataService
                .findByRequestSequenceNumberAndCardNumber(requestSequenceNumber, cardNumber) != null);
    }
    
    @Override
    public Boolean isNotificationEmailRequired(Integer requestSequenceNumber, String cardNumber) {
        AdHocLoadSettlementDTO adHocLoadsettlementDTO= getSettlement(requestSequenceNumber, cardNumber);
        OrderDTO orderDTO = getOrder(adHocLoadsettlementDTO.getOrderId());
        CustomerDTO customerDTO = getCustomer(orderDTO.getCustomerId());
        return customerDTO.getExternalUserId() == null ? Boolean.TRUE : Boolean.FALSE;
    }
    
    @Override
    public void notifyCustomer(Integer requestSequenceNumber, String cardNumber) {
        AdHocLoadSettlementDTO adHocLoadSettlementDTO = getSettlement(requestSequenceNumber, cardNumber);
        String pickUpLocationName = lookupLocationName(adHocLoadSettlementDTO.getPickUpNationalLocationCode());
        OrderDTO orderDTO = getOrder(adHocLoadSettlementDTO.getOrderId());
        CustomerDTO customerDTO = getCustomer(orderDTO.getCustomerId());
        sendEmail(adHocLoadSettlementDTO, cardNumber, pickUpLocationName, customerDTO, orderDTO);
        createEvent(adHocLoadSettlementDTO, cardNumber, pickUpLocationName, orderDTO);
   }
    
    protected AdHocLoadSettlementDTO getSettlement(Integer requestSequenceNumber, String cardNumber) {
        return this.adHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(requestSequenceNumber, cardNumber);
    }
    
    protected String lookupLocationName(Integer pickUpNationalLocationCode) {
        return this.locationDataService.findById((long) pickUpNationalLocationCode).getName();
    }

    protected OrderDTO getOrder(Long orderId) {
        return this.orderDataService.findById(orderId);
    }

    protected CustomerDTO getCustomer(Long customerId) {
        return this.customerDataService.findById(customerId);
    }

    protected void createEvent(AdHocLoadSettlementDTO adHocLoadSettlementDTO, String cardNumber, String pickUpLocationName,
                               OrderDTO orderDTO) {
        this.applicationEventService.create(orderDTO.getCustomerId(), EventName.EMAIL_SEND,
                buildAdditionalInformation(adHocLoadSettlementDTO, cardNumber, pickUpLocationName));
    }

    protected void sendEmail(AdHocLoadSettlementDTO adHocLoadSettlementDTO, String cardNumber, String pickUpLocationName,
                             CustomerDTO customerDTO, OrderDTO orderDTO) {
        EmailArgumentsDTO arguments = new EmailArgumentsDTO();
        arguments.setBaseUri(getUriFromAString(
                this.systemParameterService.getParameterValue(SystemParameterCode.ONLINE_SYSTEM_BASE_URI.code())));
        arguments.setRefundAmountInPence(adHocLoadSettlementDTO.getAmount());
        arguments.setPickUpLocationName(pickUpLocationName);
        arguments.setCardNumber(cardNumber);
        arguments.setPickedUpOn(adHocLoadSettlementDTO.getSettlementDate());
        arguments.setPickUpExpiresOn(adHocLoadSettlementDTO.getExpiresOn());
        arguments.setSalutation(getSalutation(customerDTO));
        arguments.setToAddress(customerDTO.getEmailAddress());
        arguments.setReferenceNumber(orderDTO.getOrderNumber());
        getEmailMessageService().sendMessage(arguments);
    }

    protected abstract EmailMessageService getEmailMessageService();

    protected abstract String buildAdditionalInformation(AdHocLoadSettlementDTO adHocLoadSettlementDTO, String cardNumber,
                                                         String pickUpLocationName);
}
