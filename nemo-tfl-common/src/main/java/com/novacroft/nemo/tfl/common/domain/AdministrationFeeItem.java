package com.novacroft.nemo.tfl.common.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

/**
 * TfL administration fee item single table inheritance domain definition
 */
@Entity
@DiscriminatorValue("AdministrationFee")
public class AdministrationFeeItem extends Item {
    private static final long serialVersionUID = -6712656857354383248L;
    protected Long administrationFeeId;
    protected AdministrationFeeItem relatedItem;

    public AdministrationFeeItem() {
    }

    public AdministrationFeeItem(Long administrationFeeId, Long cardId, Long cartId, Integer price) {
        this.administrationFeeId = administrationFeeId;
        this.cardId = cardId;
        this.price = price;
    }
    
    public AdministrationFeeItem(Long administrationFeeId, Integer price, String createdUserId, Date createdDateTime) {
        this.administrationFeeId = administrationFeeId;
    	this.price = price;
        this.createdUserId = createdUserId;
        this.createdDateTime = createdDateTime;
    }

    public Long getAdministrationFeeId() {
        return administrationFeeId;
    }

    public void setAdministrationFeeId(Long administrationFeeId) {
        this.administrationFeeId = administrationFeeId;
    }
    
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @Cascade( {org.hibernate.annotations.CascadeType.DELETE} )
    @JoinColumn(name = "RELATEDITEMID" ) 
    public AdministrationFeeItem getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(AdministrationFeeItem relatedItem) {
        this.relatedItem = relatedItem;
    }

}
