package com.novacroft.nemo.tfl.common.transfer;

/**
 * TfL administration fee item common definition
 */

public class AdministrationFeeItemDTO extends ItemDTO {
    protected Long administrationFeeId;

    public AdministrationFeeItemDTO() {
        super();
    }

    public AdministrationFeeItemDTO(Long id, Long cardId, Long cartId, Integer price, Long administrationFeeId) {
        this.id = id;
        this.cardId = cardId;
        this.cartId = cartId;
        this.price = price;
        this.administrationFeeId = administrationFeeId;
    }
    
    public AdministrationFeeItemDTO(Long cardId, Integer price, Long administrationFeeId) {
        this.cardId = cardId;
        this.price = price;
        this.administrationFeeId = administrationFeeId;
    }

    public AdministrationFeeItemDTO(Long id, Long cardId, Long cartId) {
        this.id = id;
        this.cardId = cardId;
        this.cartId = cartId;
    }

    public Long getAdministrationFeeId() {
        return administrationFeeId;
    }

    public void setAdministrationFeeId(Long administrationFeeId) {
        this.administrationFeeId = administrationFeeId;
    }


}
