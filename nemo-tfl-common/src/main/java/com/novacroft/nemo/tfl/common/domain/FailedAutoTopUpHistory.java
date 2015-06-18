package com.novacroft.nemo.tfl.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

@Entity
@Table(name = "FATU_HISTORY")
public class FailedAutoTopUpHistory extends AbstractBaseEntity {

    private static final long serialVersionUID = 2685455514498512230L;

    private Date autoTopUpDate;
    private Date resettlementAttemptDate;
    private Date hotlistDate;
    private Date refundDate;
    private Long customerId;
    @Column(name = "FATUPAYMENTDETAILID")
    private Long failedAutoTopUpPaymentDetailId;
    @Column(name = "FATUCASEID")
    private Long failedAutoTopUpCaseId;
    private String caseNote;
    private String actor;
    private String caseStatus;
    private Double autoTopUpAmount;

    @SequenceGenerator(name = "FATU_HISTORY_SEQ", sequenceName = "FATU_HISTORY_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FATU_HISTORY_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

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
