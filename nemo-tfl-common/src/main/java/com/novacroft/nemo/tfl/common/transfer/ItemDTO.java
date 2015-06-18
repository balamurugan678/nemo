package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;
import com.novacroft.nemo.tfl.common.formatter.PenceFormat;

/**
 * TfL item transfer implementation
 */

public class ItemDTO extends AbstractBaseDTO {
	private static final long serialVersionUID = 2278313037956737501L;
	protected Long cardId;
    protected Long cartId;
    protected Long orderId;

    @PenceFormat
    protected Integer price;
    protected String name;
    protected Long customerId;
    protected Long webAccountId;
    protected ItemDTO relatedItem;
    protected Boolean ticketOverlapped = Boolean.FALSE;

    public ItemDTO() {
        super();
    }

    public ItemDTO(Long cardId, Long cartId, Long orderId, Integer price) {
        super();
        this.cardId = cardId;
        this.cartId = cartId;
        this.orderId = orderId;
        this.price = price;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Long webAccountId) {
        this.webAccountId = webAccountId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long id) {
        this.customerId = id;
    }
    
    public ItemDTO getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(ItemDTO relatedItem) {
        this.relatedItem = relatedItem;
    }

    public Boolean isTicketOverlapped() {
        return ticketOverlapped;
    }

    public void setTicketOverlapped(Boolean ticketOverlapped) {
        this.ticketOverlapped = ticketOverlapped;
    }
}
