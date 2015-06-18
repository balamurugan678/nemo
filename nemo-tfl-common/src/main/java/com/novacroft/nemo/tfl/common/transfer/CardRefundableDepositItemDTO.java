package com.novacroft.nemo.tfl.common.transfer;


/**
 * TfL card refundable deposit item transfer implementation
 */

public class CardRefundableDepositItemDTO extends ItemDTO {
	protected Long cardRefundableDepositId;
	
	public CardRefundableDepositItemDTO() {
	    super();
	}

    public CardRefundableDepositItemDTO(Long cardId, Integer price) {
        super();
        setCardId(cardId);
        setPrice(price);
    }

    @Deprecated
	public CardRefundableDepositItemDTO(Long cartId, Long cardId, Integer price) {
        this.cartId = cartId;
        this.cardId = cardId;
        this.price = price;
    }
	
    public Long getCardRefundableDepositId() {
        return cardRefundableDepositId;
    }
    public void setCardRefundableDepositId(Long cardRefundableDepositId) {
        this.cardRefundableDepositId = cardRefundableDepositId;
    }
	
}
