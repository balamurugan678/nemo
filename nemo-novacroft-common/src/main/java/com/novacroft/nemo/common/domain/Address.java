package com.novacroft.nemo.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

/**
 * TfL address implementation
 */

@Audited
@Entity
public class Address extends CommonAddress {

    @Transient
    private static final long serialVersionUID = 7497456682551481818L;
   
    public Address() {
        super();
    }

    public Address(Long id, String houseNameNumber, String street, String town, Country country, String postcode) {
        super();
        this.id = id;
        this.houseNameNumber = houseNameNumber;
        this.street = street;
        this.town = town;
        this.country = country;
        this.postcode = postcode;
    }

    @SequenceGenerator(name = "ADDRESS_SEQ", sequenceName = "ADDRESS_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }
    
}
