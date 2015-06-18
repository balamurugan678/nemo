package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

/**
 * TfL card refundable deposit amount single table inheritance domain definition
 */
@Entity
@DiscriminatorValue("cardRefundableDeposit")
public class CardRefundableDepositItem extends Item {
    private static final long serialVersionUID = 5467535755552673855L;	
    protected Long cardRefundableDepositId;
    protected CardRefundableDepositItem relatedItem;
    
    public Long getCardRefundableDepositId() {
        return cardRefundableDepositId;
    }
    public void setCardRefundableDepositId(Long cardRefundableDepositId) {
        this.cardRefundableDepositId = cardRefundableDepositId;
    }
    
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @Cascade( {org.hibernate.annotations.CascadeType.DELETE} )
    @JoinColumn(name = "RELATEDITEMID" ) 
    public CardRefundableDepositItem getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(CardRefundableDepositItem relatedItem) {
        this.relatedItem = relatedItem;
    }
    
}
