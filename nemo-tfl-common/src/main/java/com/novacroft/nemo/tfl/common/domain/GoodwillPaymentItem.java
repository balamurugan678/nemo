package com.novacroft.nemo.tfl.common.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

@Entity
@DiscriminatorValue("GoodwillPayment")
public class GoodwillPaymentItem extends Item {
    private static final long serialVersionUID = 8536511641331363555L;

    protected GoodwillReason goodwillReason;

    protected String goodwillOtherText;
    
    protected GoodwillPaymentItem relatedItem;

    public GoodwillPaymentItem() {
    }

    public GoodwillPaymentItem(Long cardId, String createdUserId, Long id, Integer price, Boolean nullable) {
        this.cardId = cardId;
        this.createdUserId = createdUserId;
        this.id = id;
        this.price = price;
        this.nullable = nullable;
    }
    
    public GoodwillPaymentItem(Long cardId, String createdUserId, Date createdDateTime, Integer price, Boolean nullable) {
        this.cardId = cardId;
        this.createdUserId = createdUserId;
        this.createdDateTime = createdDateTime;
        this.price = price;
        this.nullable = nullable;
    }

    @ManyToOne(optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "goodwillPaymentId")
    public GoodwillReason getGoodwillReason() {
        return goodwillReason;

    }

    public void setGoodwillReason(GoodwillReason goodwillReason) {
        this.goodwillReason = goodwillReason;
    }

    public String getGoodwillOtherText() {
        return goodwillOtherText;
    }

    public void setGoodwillOtherText(String goodwillOtherText) {
        this.goodwillOtherText = goodwillOtherText;
    }
    
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @Cascade( {org.hibernate.annotations.CascadeType.DELETE} )
    @JoinColumn(name = "RELATEDITEMID" ) 
    public GoodwillPaymentItem getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(GoodwillPaymentItem relatedItem) {
        this.relatedItem = relatedItem;
    }
}
