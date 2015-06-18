package com.novacroft.nemo.tfl.common.transfer.fare_aggregation_engine;

import java.util.Date;

public class RecalculatedOysterChargeResponseDTO {
	
	protected Integer dayPricePence;
	
	protected Long prestigeID;
	
	protected Date travelDate;
	
	protected String customerType;

	public Integer getDayPricePence() {
		return dayPricePence;
	}

	public void setDayPricePence(Integer dayPricePence) {
		this.dayPricePence = dayPricePence;
	}

	public Long getPrestigeID() {
		return prestigeID;
	}

	public void setPrestigeID(Long prestigeID) {
		this.prestigeID = prestigeID;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	

}
