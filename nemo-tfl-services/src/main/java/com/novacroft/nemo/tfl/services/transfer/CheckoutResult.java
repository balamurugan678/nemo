package com.novacroft.nemo.tfl.services.transfer;

public class CheckoutResult extends WebServiceResult {

    private Long orderId;
    private Long paymentSettlementId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPaymentSettlementId() {
        return paymentSettlementId;
    }

    public void setPaymentSettlementId(Long paymentSettlementId) {
        this.paymentSettlementId = paymentSettlementId;
    }

}
