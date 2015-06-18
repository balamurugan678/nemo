package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * TfL anonymous goodwill refund cart domain definition
 */
@Entity
@DiscriminatorValue("AnonymousGoodwillRefund")
public class AnonymousGoodwillRefund extends Cart {
    private static final long serialVersionUID = -6646945007451058314L;
    
    public AnonymousGoodwillRefund() {
    }

    public AnonymousGoodwillRefund(Long id, Long cardId, Long webAccountId) {
        this.id = id;
        this.cardId = cardId;
        this.webAccountId = webAccountId;
    }
}
