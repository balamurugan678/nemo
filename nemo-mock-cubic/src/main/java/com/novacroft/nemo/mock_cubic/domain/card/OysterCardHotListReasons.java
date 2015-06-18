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
* OysterCardHotListReasons  domain class to hold the domain data.
*/

@Entity
@Table(name = "MOCK_OYSTERCARDHOTLISTREASONS")
public class OysterCardHotListReasons extends AbstractBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    protected String prestigeId;
    protected Long hotListReasonCode;
    
    @SequenceGenerator(name = "OYSTERCARDHOTLISTREASONS_SEQ", sequenceName = "OYSTERCARDHOTLISTREASONS_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OYSTERCARDHOTLISTREASONS_SEQ")
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

    public Long getHotListReasonCode() {
        return hotListReasonCode;
    }

    public void setHotListReasonCode(Long hotListReasonCode) {
        this.hotListReasonCode = hotListReasonCode;
    }        
}
