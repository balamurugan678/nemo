package com.novacroft.nemo.tfl.common.transfer;

public class FailedAutoTopUpCaseItemDTO extends ItemDTO {
	private static final long serialVersionUID = -7203603713150770833L;
	
	private Double amount;
	
	public FailedAutoTopUpCaseItemDTO(Long cardId, Double refundableDepositAmount) {
		this.cardId = cardId;
		this.amount = refundableDepositAmount;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
