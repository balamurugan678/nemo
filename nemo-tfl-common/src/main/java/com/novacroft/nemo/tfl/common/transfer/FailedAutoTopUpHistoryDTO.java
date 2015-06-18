package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

public class FailedAutoTopUpHistoryDTO extends AbstractBaseDTO {

    private static final long serialVersionUID = 2685455514498512230L;

    private Date autoTopUpDate;
    private Date resettlementAttemptDate;
    private Date hotlistDate;
    private Date refundDate;
    private Long customerId;
    private Long failedAutoTopUpPaymentDetailId;
    private Long failedAutoTopUpCaseId;
    private String caseNote;
    private String actor;
    private String caseStatus;
    private Double autoTopUpAmount;

    public Date getAutoTopUpDate() {
        return autoTopUpDate;
    }

    public void setAutoTopUpDate(Date autoTopUpDate) {
        this.autoTopUpDate = autoTopUpDate;
    }

    public Date getResettlementAttemptDate() {
        return resettlementAttemptDate;
    }

    public void setResettlementAttemptDate(Date resettlementAttemptDate) {
        this.resettlementAttemptDate = resettlementAttemptDate;
    }

    public Date getHotlistDate() {
        return hotlistDate;
    }

    public void setHotlistDate(Date hotlistDate) {
        this.hotlistDate = hotlistDate;
    }

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getFailedAutoTopUpPaymentDetailId() {
        return failedAutoTopUpPaymentDetailId;
    }

    public void setFailedAutoTopUpPaymentDetailId(Long failedAutoTopUpPaymentDetailId) {
        this.failedAutoTopUpPaymentDetailId = failedAutoTopUpPaymentDetailId;
    }

    public Long getFailedAutoTopUpCaseId() {
        return failedAutoTopUpCaseId;
    }

    public void setFailedAutoTopUpCaseId(Long failedAutoTopUpCaseId) {
        this.failedAutoTopUpCaseId = failedAutoTopUpCaseId;
    }

	public String getCaseNote() {
		return caseNote;
	}

	public void setCaseNote(String caseNote) {
		this.caseNote = caseNote;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public Double getAutoTopUpAmount() {
		return autoTopUpAmount;
	}

	public void setAutoTopUpAmount(Double autoTopUpAmount) {
		this.autoTopUpAmount = autoTopUpAmount;
	}

}
