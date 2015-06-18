package com.novacroft.nemo.tfl.common.transfer;

import java.util.List;

public class OrderItemsDTO {
    protected OrderDTO order;
    protected List<ItemDTO> items;
    protected WebCreditSettlementDTO webAccountCreditSettlement;
    protected PaymentCardSettlementDTO paymentCardSettlement;
    protected AdHocLoadSettlementDTO adhocLoadSettlement;
    
    public OrderItemsDTO(OrderDTO order, List<ItemDTO> items, WebCreditSettlementDTO webAccountCreditSettlementDTO, PaymentCardSettlementDTO paymentCardSettlementDTO, AdHocLoadSettlementDTO adhocLoadSettlement) {
        super();
        this.order = order;
        this.items = items;
        this.webAccountCreditSettlement = webAccountCreditSettlementDTO;
        this.paymentCardSettlement = paymentCardSettlementDTO;
        this.adhocLoadSettlement = adhocLoadSettlement;
    }
    
    public OrderDTO getOrder() {
        return order;
    }
    public void setOrder(OrderDTO order) {
        this.order = order;
    }
    public List<ItemDTO> getItems() {
        return items;
    }
    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
    public WebCreditSettlementDTO getWebAccountCreditSettlement() {
    	return this.webAccountCreditSettlement;
    }
    public void setWebAccountCreditSettlement(WebCreditSettlementDTO wacs) {
    	this.webAccountCreditSettlement = wacs;
    }
    public PaymentCardSettlementDTO getPaymentCardSettlement() {
    	return this.paymentCardSettlement;
    }
    public void setPaymentCardSettlement(PaymentCardSettlementDTO pcs) {
    	this.paymentCardSettlement = pcs;
    }

    public AdHocLoadSettlementDTO getAdhocLoadSettlement() {
        return adhocLoadSettlement;
    }

    public void setAdhocLoadSettlement(AdHocLoadSettlementDTO adhocLoadSettlement) {
        this.adhocLoadSettlement = adhocLoadSettlement;
    }

}
