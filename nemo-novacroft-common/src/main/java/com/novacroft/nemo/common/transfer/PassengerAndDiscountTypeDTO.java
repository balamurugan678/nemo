package com.novacroft.nemo.common.transfer;

import java.io.Serializable;

public class PassengerAndDiscountTypeDTO implements Serializable {

	private static final long serialVersionUID = -5948993831390238635L;
	
	protected String passengerType ;
	
	protected String discountType;

	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	
}
