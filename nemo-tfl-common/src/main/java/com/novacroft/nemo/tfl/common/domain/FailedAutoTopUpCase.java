package com.novacroft.nemo.tfl.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

@Audited
@Entity
@Table(name = "FATU_CASE")
public class FailedAutoTopUpCase extends AbstractBaseEntity {

    private static final long serialVersionUID = 4024721582718850324L;
    private Long caseReferenceNumber;
    private String caseStatus;
    private String caseProgressionStatus;
    private String customerLiability;
    private Double failedAutoTopUpAmount;
    private Long customerId;
    private Long cardId;
    private String cardNumber;
    private Date resettlementEndDate;
    private String caseNote;

    @SequenceGenerator(name = "FATU_CASE_SEQ", sequenceName = "FATU_CASE_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FATU_CASE_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public Long getCaseReferenceNumber() {
        return caseReferenceNumber;
    }

    public void setCaseReferenceNumber(Long caseReferenceNumber) {
        this.caseReferenceNumber = caseReferenceNumber;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCaseProgressionStatus() {
        return caseProgressionStatus;
    }

    public void setCaseProgressionStatus(String caseProgressionStatus) {
        this.caseProgressionStatus = caseProgressionStatus;
    }

    public String getCustomerLiability() {
        return customerLiability;
    }

    public void setCustomerLiability(String customerLiability) {
        this.customerLiability = customerLiability;
    }

    public Double getFailedAutoTopUpAmount() {
        return failedAutoTopUpAmount;
    }

    public void setFailedAutoTopUpAmount(Double failedAutoTopUpAmount) {
        this.failedAutoTopUpAmount = failedAutoTopUpAmount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getResettlementEndDate() {
        return resettlementEndDate;
    }

    public void setResettlementEndDate(Date resettlementEndDate) {
        this.resettlementEndDate = resettlementEndDate;
    }

	public String getCaseNote() {
		return caseNote;
	}

	public void setCaseNote(String caseNote) {
		this.caseNote = caseNote;
	}
}
