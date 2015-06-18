package com.novacroft.nemo.mock_cubic.domain.card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;


/**
* OysterCardPpvPending domain class to hold the domain data.
*/

@Entity
@Table(name = "MOCK_OYSTERCARDPPVPENDING")
public class OysterCardPpvPending extends AbstractBaseEntity {
    
    private static final long serialVersionUID = 1L ;
    
    protected String prestigeId;
    protected Long requestSequenceNumber;
    protected String realtimeFlag;
    protected Long prePayValue;
    protected Long currency;
    protected Long pickupLocation;

    @SequenceGenerator(name = "OYSTERCARDPPVPENDING_SEQ", sequenceName = "OYSTERCARDPPVPENDING_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OYSTERCARDPPVPENDING_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
      return id;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public Long getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public void setRequestSequenceNumber(Long requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public String getRealtimeFlag() {
        return realtimeFlag;
    }

    public void setRealtimeFlag(String realtimeFlag) {
        this.realtimeFlag = realtimeFlag;
    }

    public Long getPrePayValue() {
        return prePayValue;
    }

    public void setPrePayValue(Long prePayValue) {
        this.prePayValue = prePayValue;
    }

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Long getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Long pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }    
}
