package com.novacroft.nemo.common.transfer;

/**
 * Card preferences transfer class common definition
 */
public class CommonCardPreferencesDTO extends AbstractBaseDTO {
    protected Long cardId;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}
