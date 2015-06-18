package com.novacroft.nemo.tfl.common.domain;

import java.util.Date;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

import javax.persistence.*;

/**
 * TfL card refundable deposit amount domain definition
 */
@Entity
public class CardRefundableDeposit extends AbstractBaseEntity {
    private static final long serialVersionUID = -4737628704444041743L;

    protected Integer price;
    protected Date startDate;
    protected Date endDate;

    @SequenceGenerator(name = "CARDREFUNDABLEDEPOSIT_SEQ", sequenceName = "CARDREFUNDABLEDEPOSIT_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARDREFUNDABLEDEPOSIT_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
