package com.novacroft.nemo.common.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;

/**
 * Common address attributes that will be inherited by all implementations.
 */
@Audited
@MappedSuperclass()
public abstract class CommonAddress extends AbstractBaseEntity {
    private static final long serialVersionUID = -3866265491093829377L;

    protected String houseNameNumber;
    protected String street;
    protected String town;
    protected String county;
    protected Country country;
    protected String postcode;

    public CommonAddress() {
        setCreated();
    }

    public String getHouseNameNumber() {
        return houseNameNumber;
    }

    public void setHouseNameNumber(String houseNameNumber) {
        this.houseNameNumber = houseNameNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @ManyToOne
    @JoinColumn(name ="COUNTRY_ID")
    public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

}
