package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import org.springframework.beans.factory.annotation.Autowired;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

/**
 * Base service for ad hoc load status updates
 */
public abstract class BaseAdHocLoadStatusService extends BaseService{
    
    @Autowired
    protected LocationDataService locationDataService;
    @Autowired
    protected AdHocLoadSettlementDataService adHocLoadSettlementDataService;
    @Autowired
    protected ApplicationEventService applicationEventService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected OrderService orderService;

    public Boolean hasAdHocLoadBeenRequested(Integer requestSequenceNumber, String cardNumber) {
        return (null != findSettlement(requestSequenceNumber, cardNumber));
    }
    
    protected AdHocLoadSettlementDTO findSettlement(Integer requestSequenceNumber, String cardNumber) {
        return this.adHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(requestSequenceNumber, cardNumber);

    }

    public void updateStatus(AdHocLoadSettlementDTO adHocLoadSettlementDTO, SettlementStatus settlementStatus) {
        adHocLoadSettlementDTO.setStatus(settlementStatus.code());
        this.adHocLoadSettlementDataService.createOrUpdate(adHocLoadSettlementDTO);
        this.orderService.updateOrderStatus(adHocLoadSettlementDTO.getOrderId());
    }

    public void updateOrderStatus(Long orderId) {
        this.orderService.updateOrderStatus(orderId);
    }

    public void createEvent(String cardNumber, AdHocLoadSettlementDTO adHocLoadSettlementDTO, EventName eventName) {
        String pickUpLocationName = lookupLocationName(adHocLoadSettlementDTO.getPickUpNationalLocationCode());
        this.applicationEventService.create(this.customerDataService.findByCardNumber(cardNumber).getId(), eventName,
                buildAdditionalInformation(cardNumber, adHocLoadSettlementDTO, pickUpLocationName));
    }
    
    protected String lookupLocationName(Integer pickUpNationalLocationCode) {
        return this.locationDataService.findById((long) pickUpNationalLocationCode).getName();
    }

    protected abstract String buildAdditionalInformation(String cardNumber, AdHocLoadSettlementDTO adHocLoadSettlementDTO, String pickUpLocationName);
}
