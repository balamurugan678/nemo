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
 * TfL pay as you go item single table inheritance domain definition
 */
@Entity
@DiscriminatorValue("PayAsYouGo")
public class PayAsYouGoItem extends Item {
    private static final long serialVersionUID = -6712656857354383248L;
    protected Long payAsYouGoId;
    protected Date startDate;
    protected Date endDate;
    protected String reminderDate;
    protected Integer autoTopUpAmount;
    protected PayAsYouGoItem relatedItem;

    public Long getPayAsYouGoId() {
        return payAsYouGoId;
    }

    public void setPayAsYouGoId(Long payAsYouGoId) {
        this.payAsYouGoId = payAsYouGoId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Integer getAutoTopUpAmount() {
        return autoTopUpAmount;
    }

    public void setAutoTopUpAmount(Integer autoTopUpAmount) {
        this.autoTopUpAmount = autoTopUpAmount;
    }
    
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @Cascade( {org.hibernate.annotations.CascadeType.DELETE} )
    @JoinColumn(name = "RELATEDITEMID" ) 
    public PayAsYouGoItem getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(PayAsYouGoItem relatedItem) {
        this.relatedItem = relatedItem;
    }

}
