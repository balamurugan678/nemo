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
@DiscriminatorValue("JourneyCompleted")
public class JourneyCompletedRefundItem extends Item {

	private static final long serialVersionUID = -3378806675032210858L;

	protected Date processingDate;
	
	protected Date journeyDate;
	
	protected Integer startExitStation;
	
	protected Integer providedStation;
	
	protected Integer pickUpStation;
	
	protected Long refundReason;
	
	protected String errorDescription;
	
	protected Integer journeyID;
	
	protected JourneyCompletedRefundItem relatedItem;

	public Date getProcessingDate() {
		return processingDate;
	}

	public void setProcessingDate(Date processingDate) {
		this.processingDate = processingDate;
	}

	public Date getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(Date journeyDate) {
		this.journeyDate = journeyDate;
	}

	public Integer getStartExitStation() {
		return startExitStation;
	}

	public void setStartExitStation(Integer startExitStation) {
		this.startExitStation = startExitStation;
	}

	public Integer getProvidedStation() {
		return providedStation;
	}

	public void setProvidedStation(Integer providedStation) {
		this.providedStation = providedStation;
	}

	public Integer getPickUpStation() {
		return pickUpStation;
	}

	public void setPickUpStation(Integer pickUpStation) {
		this.pickUpStation = pickUpStation;
	}

	public Long getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(Long refundReason) {
		this.refundReason = refundReason;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public Integer getJourneyID() {
		return journeyID;
	}

	public void setJourneyID(Integer journeyID) {
		this.journeyID = journeyID;
	}
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @Cascade( {org.hibernate.annotations.CascadeType.DELETE} )
    @JoinColumn(name = "RELATEDITEMID" ) 
    public JourneyCompletedRefundItem getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(JourneyCompletedRefundItem relatedItem) {
        this.relatedItem = relatedItem;
    }
	
	
}
