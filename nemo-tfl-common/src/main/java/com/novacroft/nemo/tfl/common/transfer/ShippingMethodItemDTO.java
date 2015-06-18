package com.novacroft.nemo.tfl.common.transfer;


/**
 * TfL shipping method item transfer implementation
 */

public class ShippingMethodItemDTO extends ItemDTO {
	protected Long shippingMethodId;

    public ShippingMethodItemDTO() {
        super();
    }

    public ShippingMethodItemDTO(Long cardId, Integer price, Long shippingMethodId) {
        setCardId(cardId);
        setPrice(price);
        setShippingMethodId(shippingMethodId);
    }

    public Long getShippingMethodId() {
		return shippingMethodId;
	}

	public void setShippingMethodId(Long shippingMethodId) {
		this.shippingMethodId = shippingMethodId;
	}
	
}
