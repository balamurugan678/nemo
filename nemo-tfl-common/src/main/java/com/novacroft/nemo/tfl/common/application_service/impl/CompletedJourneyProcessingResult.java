package com.novacroft.nemo.tfl.common.application_service.impl;

public class CompletedJourneyProcessingResult {

	protected Integer amount;
	
	protected Boolean refundSuccessfull;

	public CompletedJourneyProcessingResult(Integer amount,
			Boolean refundSuccessfull) {
		super();
		this.amount = amount;
		this.refundSuccessfull = refundSuccessfull;
	}

	public Integer getAmount() {
		return amount;
	}

	public Boolean getRefundSuccessfull() {
		return refundSuccessfull;
	}
	
	
	
	
}
