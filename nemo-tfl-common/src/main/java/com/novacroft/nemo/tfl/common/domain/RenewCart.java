package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * TfL renew cart domain definition. Renew cart used to renew the about to expire products available on oyster card.
 */
@Entity
@DiscriminatorValue("Renew")
public class RenewCart extends Cart {
    private static final long serialVersionUID = -7780773709056155291L;

    public RenewCart() {
    }

    public RenewCart(Long id, Long cardId, Long webAccountId) {
        this.id = id;
        this.cardId = cardId;
        this.webAccountId = webAccountId;
    }
}
