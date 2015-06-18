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

/**
 * TfL job centre plus investigation domain definition
 */
@Audited
@Entity
@Table(name = "JCP_INVESTIGATION")
public class JobCentrePlusInvestigation extends AbstractBaseEntity {
    private static final long serialVersionUID = 1199867192446757114L;

    protected Long cardId;
    protected String cardNumber;
    protected Date startDate;
    protected Date expiryDate;

    @SequenceGenerator(name = "JCP_INVESTIGATION_SEQ", sequenceName = "JCP_INVESTIGATION_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JCP_INVESTIGATION_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
