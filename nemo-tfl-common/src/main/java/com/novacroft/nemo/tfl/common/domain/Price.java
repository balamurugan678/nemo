package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.envers.Audited;

import com.novacroft.nemo.common.domain.PeriodicAbstractBaseEntity;

@Audited
@Entity
public class Price extends PeriodicAbstractBaseEntity {

    private static final long serialVersionUID = 4883861690832228871L;
    protected Integer priceInPence;
    
    protected PrePaidTicket prePaidTicket;

    @SequenceGenerator(name = "PRICE_SEQ", sequenceName = "PRICE_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRICE_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }
    
    
    @ManyToOne()
    @JoinColumn(name="PREPAIDTICKETID")
    public PrePaidTicket getPrePaidTicket() {
		return prePaidTicket;
	}



	public void setPrePaidTicket(PrePaidTicket prePaidTicket) {
		this.prePaidTicket = prePaidTicket;
	}



	public Integer getpriceInPence() {
        return priceInPence;
    }

    public void setpriceInPence(Integer priceInPence) {
        this.priceInPence = priceInPence;
    }
}
