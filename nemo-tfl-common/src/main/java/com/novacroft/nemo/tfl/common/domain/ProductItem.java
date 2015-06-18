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
 * TfL productItem single table inheritance domain definition
 */
@Entity
@DiscriminatorValue("Product")
public class ProductItem extends Item {
    private static final long serialVersionUID = -6712656857354383248L;
    protected Long productId;
    protected Date startDate;
    protected Date endDate;
    protected String reminderDate;
    protected Integer startZone;
    protected Integer endZone;
    protected String refundCalculationBasis;
    protected String magneticTicketNumber;
    protected Boolean ticketUnused;
    protected Boolean ticketBackdated;
    protected Boolean deceasedCustomer;
    protected BackdatedRefundReason backdatedRefundReason;
    protected Long backdatedRefundReasonId;
    protected String refundType;
    protected Date tradedDate;
    protected Date dateOfRefund;
    protected Date dateOfCanceAndSurrender;
    protected ProductItem relatedItem;

    public void setBackdatedRefundReason(BackdatedRefundReason backdatedRefundReason) {
        this.backdatedRefundReason = backdatedRefundReason;
    }

    public String getMagneticTicketNumber() {
        return magneticTicketNumber;
    }

    public void setMagneticTicketNumber(String magneticTicketNumber) {
        this.magneticTicketNumber = magneticTicketNumber;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Integer getStartZone() {
        return startZone;
    }

    public void setStartZone(Integer startZone) {
        this.startZone = startZone;
    }

    public Integer getEndZone() {
        return endZone;
    }

    public void setEndZone(Integer endZone) {
        this.endZone = endZone;
    }

    public String getRefundCalculationBasis() {
        return refundCalculationBasis;
    }

    public void setRefundCalculationBasis(String refundCalculationBasis) {
        this.refundCalculationBasis = refundCalculationBasis;
    }

    public Boolean getTicketUnused() {
        return ticketUnused;
    }

    public Boolean getTicketBackdated() {
        return ticketBackdated;
    }

    public void setTicketUnused(Boolean ticketUnused) {
        this.ticketUnused = ticketUnused;
    }

    public void setTicketBackdated(Boolean ticketBackdated) {
        this.ticketBackdated = ticketBackdated;
    }

    public Boolean getDeceasedCustomer() {
        return deceasedCustomer;
    }

    public void setDeceasedCustomer(Boolean deceasedCustomer) {
        this.deceasedCustomer = deceasedCustomer;
    }

    public Long getBackdatedRefundReasonId() {
        return backdatedRefundReasonId;
    }

    public void setBackdatedRefundReasonId(Long backdatedRefundReasonId) {
        this.backdatedRefundReasonId = backdatedRefundReasonId;
    }

    public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public Date getTradedDate() {
        return tradedDate;
    }

    public void setTradedDate(Date tradedDate) {
        this.tradedDate = tradedDate;
    }

    
    public Date getDateOfRefund() {
        return dateOfRefund;
    }

    public void setDateOfRefund(Date dateOfRefund) {
        this.dateOfRefund = dateOfRefund;
    }

	public Date getDateOfCanceAndSurrender() {
		return dateOfCanceAndSurrender;
	}

	public void setDateOfCanceAndSurrender(Date dateOfCanceAndSurrender) {
		this.dateOfCanceAndSurrender = dateOfCanceAndSurrender;
	}
    
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @Cascade( {org.hibernate.annotations.CascadeType.DELETE} )
    @JoinColumn(name = "RELATEDITEMID" ) 
    public ProductItem getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(ProductItem relatedItem) {
        this.relatedItem = relatedItem;
    }

}
