package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Card preferences domain common definition
 */
@Audited
@MappedSuperclass()
public class CommonCardPreferences extends AbstractBaseEntity {
    @Transient
    private static final long serialVersionUID = 1L;
    protected Long cardId;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}
