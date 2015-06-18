package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

import javax.persistence.*;

/**
 * TfL pay as you go domain definition
 */

@Entity
public class PayAsYouGo extends AbstractBaseEntity {
    @Transient
    private static final long serialVersionUID = 6200449962689402878L;

    protected String payAsYouGoName;
    protected Integer ticketPrice;

    public PayAsYouGo() {
        super();
    }

    @SequenceGenerator(name = "PAYASYOUGO_SEQ", sequenceName = "PAYASYOUGO_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYASYOUGO_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public String getPayAsYouGoName() {
        return payAsYouGoName;
    }

    public void setPayAsYouGoName(String payAsYouGoName) {
        this.payAsYouGoName = payAsYouGoName;
    }

    public Integer getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Integer ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

}
