package com.novacroft.nemo.tfl.common.transfer;


/**
 * Object to hold properties for use during the Cart Order/Settlement process. 
 *
 */
public class CardOrderSettlementDTO {

    private Boolean manageAutoTopUpMode = Boolean.FALSE;
        
    private CartDTO cartDTO; 
    private Long cardId;
    private Long stationId;
  
    private Long paymentCardId;

    private Integer autoTopUpAmount;
    private Integer totalAmount; 
    private Integer toPayAmount;
    private Integer webCreditApplyAmount;
    
    private OrderDTO order;
    private PaymentCardSettlementDTO paymentCardSettlement;

    public Boolean getManageAutoTopUpMode() {
        return manageAutoTopUpMode;
    }

    public void setManageAutoTopUpMode(Boolean manageAutoTopUpMode) {
        this.manageAutoTopUpMode = manageAutoTopUpMode;
    }
    public Long getStationId() {
        return stationId;
    }
    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Integer getAutoTopUpAmount() {
        return autoTopUpAmount;
    }

    public void setAutoTopUpAmount(Integer autoTopUpAmount) {
        this.autoTopUpAmount = autoTopUpAmount;
    }
    public Integer getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Integer getToPayAmount() {
        return toPayAmount;
    }
    public void setToPayAmount(Integer toPayAmount) {
        this.toPayAmount = toPayAmount;
    }
    public PaymentCardSettlementDTO getPaymentCardSettlement() {
        return paymentCardSettlement;
    }
    public void setPaymentCardSettlement(PaymentCardSettlementDTO paymentCardSettlement) {
        this.paymentCardSettlement = paymentCardSettlement;
    }
    public Long getPaymentCardId() {
        return paymentCardId;
    }
    public void setPaymentCardId(Long paymentCardId) {
        this.paymentCardId = paymentCardId;
    }
    public OrderDTO getOrder() {
        return order;
    }
    public void setOrder(OrderDTO order) {
        this.order = order;
    }
    
    
    
    

    
    
}
