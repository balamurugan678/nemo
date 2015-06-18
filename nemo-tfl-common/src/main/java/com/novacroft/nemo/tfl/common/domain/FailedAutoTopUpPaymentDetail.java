package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

@Entity
@Table(name = "FATU_PAYMENTDETAIL")
public class FailedAutoTopUpPaymentDetail extends AbstractBaseEntity {

    private static final long serialVersionUID = 2685455514498512230L;
    private Long paymentCardId;
    private String actors;
    private Integer attempts;
    private Long customerId;
    @Column(name = "FATUCASEID")
    private Long failedAutoTopUpCaseId;

    @SequenceGenerator(name = "FATU_PAYMENTDETAIL_SEQ", sequenceName = "FATU_PAYMENTDETAIL_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FATU_PAYMENTDETAIL_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

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
