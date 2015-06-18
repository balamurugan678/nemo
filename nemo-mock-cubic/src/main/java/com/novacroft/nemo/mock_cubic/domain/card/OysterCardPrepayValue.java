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
* OysterCardPrepayValue  domain class to hold the domain data.
*/

@Entity
@Table(name = "MOCK_OYSTERCARDPREPAYVALUE")
public class OysterCardPrepayValue extends AbstractBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    protected String prestigeId;
    protected Long currency;
    protected Long balance;

    @SequenceGenerator(name = "OYSTERCARDPREPAYVALUE_SEQ", sequenceName = "OYSTERCARDPREPAYVALUE_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OYSTERCARDPREPAYVALUE_SEQ")
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

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
