package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * TfL refund cart domain definition
 */
@Entity
@DiscriminatorValue("FailedCardRefund")
public class FailedCardRefundCart extends Cart {
    private static final long serialVersionUID = -6646945007451058314L;

    public FailedCardRefundCart() {
    }

    public FailedCardRefundCart(Long id, Long cardId, Long webAccountId) {
        this.id = id;
        this.cardId = cardId;
        this.webAccountId = webAccountId;
    }
}
