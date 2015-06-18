package com.novacroft.nemo.tfl.batch.domain.product_fare_loader;

import java.util.Date;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;

public class PrePaidTicketRecord implements ImportRecord {

	private String adhocPrePaidTicketCode;
	
	private String productDescription;
	
	private Date effectiveDate;
	
	private String discountDescription;
	
	private String passengerTypeCode;
	
	private String zoneDescription;
	
	private String fromZonecode;
	
	private String toZoneCode;
	
	private Boolean activeFlag;
	
	private Date priceEffectiveDate;
	
	private Integer priceInPence;
	
	private String fromDurationCode;
	
	private String toDurationCode;
	
	private String type;
	
	

	public PrePaidTicketRecord(String adhocPrePaidTicketCode,
			String poductDescription, Date effectiveDate,
			String discountDescription, String passengerTypeCode,
			String zoneDescription, String fromZonecode, String toZoneCode,
			Boolean activeFlag, Date priceEffectiveDate, Integer priceInPence,
			String fromDurationCode, String toDurationCode, String type) {
		super();
		this.adhocPrePaidTicketCode = adhocPrePaidTicketCode;
		this.productDescription = poductDescription;
		this.effectiveDate = effectiveDate;
		this.discountDescription = discountDescription;
		this.passengerTypeCode = passengerTypeCode;
		this.zoneDescription = zoneDescription;
		this.fromZonecode = fromZonecode;
		this.toZoneCode = toZoneCode;
		this.activeFlag = activeFlag;
		this.priceEffectiveDate = priceEffectiveDate;
		this.priceInPence = priceInPence;
		this.fromDurationCode = fromDurationCode;
		this.toDurationCode = toDurationCode;
		this.type = type;
	}

	public String getFromDurationCode() {
		return fromDurationCode;
	}

	public void setFromDurationCode(String fromDurationCode) {
		this.fromDurationCode = fromDurationCode;
	}

	public String getToDurationCode() {
		return toDurationCode;
	}

	public void setToDurationCode(String toDurationCode) {
		this.toDurationCode = toDurationCode;
	}

	public Integer getPriceInPence() {
		return priceInPence;
	}

	public void setPriceInPence(Integer priceInPence) {
		this.priceInPence = priceInPence;
	}

	public String getAdhocPrePaidTicketCode() {
		return adhocPrePaidTicketCode;
	}

	public void setAdhocPrePaidTicketCode(String adhocPrePaidTicketCode) {
		this.adhocPrePaidTicketCode = adhocPrePaidTicketCode;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getDiscountDescription() {
		return discountDescription;
	}

	public void setDiscountDescription(String discountDescription) {
		this.discountDescription = discountDescription;
	}

	public String getPassengerTypeCode() {
		return passengerTypeCode;
	}

	public void setPassengerTypeCode(String passengerTypeCode) {
		this.passengerTypeCode = passengerTypeCode;
	}

	public String getZoneDescription() {
		return zoneDescription;
	}

	public void setZoneDescription(String zoneDescription) {
		this.zoneDescription = zoneDescription;
	}

	public String getFromZonecode() {
		return fromZonecode;
	}

	public void setFromZonecode(String fromZonecode) {
		this.fromZonecode = fromZonecode;
	}

	public String getToZoneCode() {
		return toZoneCode;
	}

	public void setToZoneCode(String toZoneCode) {
		this.toZoneCode = toZoneCode;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Date getPriceEffectiveDate() {
		return priceEffectiveDate;
	}

	public void setPriceEffectiveDate(Date priceEffectiveDate) {
		this.priceEffectiveDate = priceEffectiveDate;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
	
	
}
