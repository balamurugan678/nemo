package com.novacroft.nemo.tfl.common.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.envers.Audited;

import com.novacroft.nemo.common.domain.PeriodicAbstractBaseEntity;

@Audited
@Entity
public class PrePaidTicket extends PeriodicAbstractBaseEntity {

    private static final long serialVersionUID = 1208035925152025073L;
    protected String adHocPrePaidTicketCode;
    protected String description;
    protected Duration fromDuration;
    protected Duration toDuration;
    protected PassengerType passengerType;
    protected Zone startZone;
    protected Zone endZone;
    protected DiscountType discountType;
    protected Set<Price> prices = new HashSet<Price>();
    protected String type;

    @SequenceGenerator(name = "PREPAIDTICKET_SEQ", sequenceName = "PREPAIDTICKET_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PREPAIDTICKET_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }
    
    public String getAdHocPrePaidTicketCode() {
        return adHocPrePaidTicketCode;
    }

    public void setAdHocPrePaidTicketCode(String adHocPrePaidTicketCode) {
        this.adHocPrePaidTicketCode = adHocPrePaidTicketCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FROMDURATIONID")
    public Duration getFromDuration() {
        return fromDuration;
    }

    public void setFromDuration(Duration fromDuration) {
        this.fromDuration = fromDuration;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TODURATIONID")
    public Duration getToDuration() {
        return toDuration;
    }

    public void setToDuration(Duration toDuration) {
        this.toDuration = toDuration;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PASSENGERTYPEID")
    public PassengerType getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(PassengerType passengerType) {
        this.passengerType = passengerType;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STARTZONEID")
    public Zone getStartZone() {
        return startZone;
    }

    public void setStartZone(Zone startZone) {
        this.startZone = startZone;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ENDZONEID")
    public Zone getEndZone() {
        return endZone;
    }

    public void setEndZone(Zone endZone) {
        this.endZone = endZone;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DISCOUNTTYPEID")
    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="prePaidTicket")
    public Set<Price> getPrices() {
        return prices;
    }

    public void setPrices(Set<Price> prices) {
        this.prices = prices;
    }

    public void addPrice(Price price) {
    	price.setPrePaidTicket(this);
        this.getPrices().add(price);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}