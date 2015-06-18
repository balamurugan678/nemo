package com.novacroft.nemo.common.transfer;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.novacroft.nemo.common.constant.CommonHashCodeSeed;

/**
 * address transfer common definition
 */
public class CommonAddressDTO extends AbstractBaseDTO implements Serializable {
    private static final long serialVersionUID = 3365678204276637026L;
    
    protected String houseNameNumber;
    protected String street;
    protected String town;
    protected String county;
    protected CountryDTO country;
    protected String postcode;

    public CommonAddressDTO() {
        super();
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

    public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	 @Override
	 public boolean equals(Object obj) {
	     if (!(obj instanceof CommonAddressDTO)) {
	         return false;
	     }
	     
	     if (obj == this){
	         return true;
	     }

	     CommonAddressDTO rhs = (CommonAddressDTO) obj;
	     return new EqualsBuilder().
	                     append(houseNameNumber, rhs.houseNameNumber).
	                     append(street, rhs.street).
	                     append(town, rhs.town).
	                     append(postcode, rhs.postcode).
	                     append(country, rhs.country).
	                     isEquals();
	 }
	    
	 @Override
	 public int hashCode() {
	     return new HashCodeBuilder(CommonHashCodeSeed.COMMON_ADDRESS_DTO.initialiser(),
	                     CommonHashCodeSeed.COMMON_ADDRESS_DTO.multiplier()).append(houseNameNumber).toHashCode();
	 }

	 @Override
	 public String toString() {
	     return new ToStringBuilder(this).append(houseNameNumber).append(street).append(town)
	                     .append(country).append(county).append(postcode).toString();
	 }

	 public CountryDTO getCountry() {
	     return country;
	 }

	 public void setCountry(CountryDTO countryDTO) {
	     this.country = countryDTO;
	 }
}
