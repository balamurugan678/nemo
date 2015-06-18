package com.novacroft.nemo.tfl.common.command.impl.fulfilment;

import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatName;
import static com.novacroft.nemo.tfl.common.util.CartUtil.isItemDTOCardRefundableDepositItemDTO;

import java.util.List;

import com.novacroft.nemo.common.command.OysterCardCmd;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;


public class FulfilmentCmd implements OysterCardCmd{

    protected Long fulfilmentPendingQueueCount;
    protected Long autoTopUpPendingQueueCount;
    protected Long payAsYouGoPendingQueueCount;
    protected Long replacementCardPendingQueueCount;
    protected Long autoTopUpReplacementCardPendingQueueCount;
    protected Long goldCardPendingQueueCount;
    protected OrderDTO order;
    protected CustomerDTO customer;
    protected CardDTO card;
    protected Boolean registeredCard = Boolean.TRUE;
    protected Integer cardDepositPrice;
    protected String customerName;
    protected String fulfiledOysterCardNumber;
    protected String shippingMethod;
    protected String paymentMethod;
    protected String currentQueue;
    protected Long currentQueueCount;
    protected AddressDTO customerAddress;
    protected Integer subTotal;
    protected Integer shippingCost;
    protected Integer totalPaid;
    protected Integer webCreditDiscount;
    
    public Long getFulfilmentPendingQueueCount() {
        return fulfilmentPendingQueueCount;
    }

    public void setFulfilmentPendingQueueCount(Long fulfilmentPendingQueueCount) {
        this.fulfilmentPendingQueueCount = fulfilmentPendingQueueCount;
    }

    public Long getAutoTopUpPendingQueueCount() {
        return autoTopUpPendingQueueCount;
    }

    public void setAutoTopUpPendingQueueCount(Long autoTopUpPendingQueueCount) {
        this.autoTopUpPendingQueueCount = autoTopUpPendingQueueCount;
    }

    public Long getPayAsYouGoPendingQueueCount() {
        return payAsYouGoPendingQueueCount;
    }

    public void setPayAsYouGoPendingQueueCount(Long payAsYouGoPendingQueueCount) {
        this.payAsYouGoPendingQueueCount = payAsYouGoPendingQueueCount;
    }

    public Long getReplacementCardPendingQueueCount() {
        return replacementCardPendingQueueCount;
    }

    public void setReplacementCardPendingQueueCount(Long replacementCardPendingQueueCount) {
        this.replacementCardPendingQueueCount = replacementCardPendingQueueCount;
    }

    public Long getAutoTopUpReplacementCardPendingQueueCount() {
        return autoTopUpReplacementCardPendingQueueCount;
    }

    public void setAutoTopUpReplacementCardPendingQueueCount(Long autoTopUpReplacementCardPendingQueueCount) {
        this.autoTopUpReplacementCardPendingQueueCount = autoTopUpReplacementCardPendingQueueCount;
    }

    public Long getGoldCardPendingQueueCount() {
        return goldCardPendingQueueCount;
    }

    public void setGoldCardPendingQueueCount(Long goldCardPendingQueueCount) {
        this.goldCardPendingQueueCount = goldCardPendingQueueCount;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public CardDTO getCard() {
        return card;
    }

    public void setCard(CardDTO card) {
        this.card = card;
    }

    public Boolean getRegisteredCard() {
        return registeredCard;
    }

    public void setRegisteredCard(Boolean registeredCard) {
        this.registeredCard = registeredCard;
    }

    public Integer getCardDepositPrice() {
        List<ItemDTO> orderItems = order.getOrderItems();
        for(ItemDTO orderItem : orderItems) {
            if(isItemDTOCardRefundableDepositItemDTO(orderItem)){
                cardDepositPrice = orderItem.getPrice();
            }
        }
        return cardDepositPrice;
    }

    public void setCardDepositPrice(Integer cardDepositPrice) {
        this.cardDepositPrice = cardDepositPrice;
    }

    public String getCustomerName() {
        return formatName(customer);
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFulfiledOysterCardNumber() {
        return fulfiledOysterCardNumber;
    }

    public void setFulfiledOysterCardNumber(String fulfiledOysterCardNumber) {
        this.fulfiledOysterCardNumber = fulfiledOysterCardNumber;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String getCardNumber() {
        return fulfiledOysterCardNumber;
    }

    public String getCurrentQueue() {
        return currentQueue;
    }

    public void setCurrentQueue(String currentQueue) {
        this.currentQueue = currentQueue;
    }

    public Long getCurrentQueueCount() {
        return currentQueueCount;
    }

    public void setCurrentQueueCount(Long currentQueueCount) {
        this.currentQueueCount = currentQueueCount;
    }

    public AddressDTO getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(AddressDTO customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Integer totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Integer getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Integer shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Integer getWebCreditDiscount() {
        return webCreditDiscount;
    }

    public void setWebCreditDiscount(Integer webCreditDiscount) {
        this.webCreditDiscount = webCreditDiscount;
    }
    
}
