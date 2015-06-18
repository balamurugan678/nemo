package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

public class FailedAutoTopUpPaymentDetailDTO extends AbstractBaseDTO {
    private static final long serialVersionUID = -4534614338775666789L;

    private Long paymentCardId;
    private String actors;
    private Integer attempts;
    private Long customerId;
    private Long failedAutoTopUpCaseId;

    public Long getPaymentCardId() {
        return paymentCardId;
    }

    public void setPaymentCardId(Long paymentCardId) {
        this.paymentCardId = paymentCardId;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getFailedAutoTopUpCaseId() {
        return failedAutoTopUpCaseId;
    }

    public void setFailedAutoTopUpCaseId(Long failedAutoTopUpCaseId) {
        this.failedAutoTopUpCaseId = failedAutoTopUpCaseId;
    }

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

}
