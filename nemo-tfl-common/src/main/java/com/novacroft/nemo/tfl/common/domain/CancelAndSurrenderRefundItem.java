package com.novacroft.nemo.tfl.common.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

@Entity
@DiscriminatorValue("CancelAndSurrender")
public class CancelAndSurrenderRefundItem extends Item {
    /**
     * 
     */
    private static final long serialVersionUID = -8249136949659947855L;
    private String magneticTicketNumber;
    protected Date startDate;
    protected Date endDate;
    protected CancelAndSurrenderRefundItem relatedItem;
    
    public CancelAndSurrenderRefundItem() {
    }

    public CancelAndSurrenderRefundItem(Long cardId, Long cartId, String createdUserId, Long id, Integer price, Boolean nullable,String magneticTicketNumber) {
        this.cardId = cardId;
        this.createdUserId = createdUserId;
        this.id = id;
        this.price = price;
        this.nullable = nullable;
        this.magneticTicketNumber = magneticTicketNumber;
    }

    public String getMagneticTicketNumber() {
        return magneticTicketNumber;
    }

    public void setMagneticTicketNumber(String magneticTicketNumber) {
        this.magneticTicketNumber = magneticTicketNumber;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @Cascade( {org.hibernate.annotations.CascadeType.DELETE} )
    @JoinColumn(name = "RELATEDITEMID" ) 
    public CancelAndSurrenderRefundItem getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(CancelAndSurrenderRefundItem relatedItem) {
        this.relatedItem = relatedItem;
    }

}
