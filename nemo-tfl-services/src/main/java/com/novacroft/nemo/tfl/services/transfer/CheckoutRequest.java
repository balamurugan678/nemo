package com.novacroft.nemo.tfl.services.transfer;


/**
 * object to properties submitted by consumer of webservice during the cart checkout phase.  
 *
 */
public class CheckoutRequest {
    
    private Long stationId;
    private Long orderId;
    private Long paymentCardSettlementId;
    private String paymentAuthoristationReference; 
    
    
    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPaymentCardSettlementId() {
        return paymentCardSettlementId;
    }

    public void setPaymentCardSettlementId(Long paymentCardSettlementId) {
        this.paymentCardSettlementId = paymentCardSettlementId;
    }

    public String getPaymentAuthoristationReference() {
        return paymentAuthoristationReference;
    }

    public void setPaymentAuthoristationReference(String paymentAuthoristationReference) {
        this.paymentAuthoristationReference = paymentAuthoristationReference;
    }
    
    
    

}
