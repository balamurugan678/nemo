package com.novacroft.nemo.common.transfer;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.novacroft.nemo.common.constant.CommonHashCodeSeed;

/**
 * TfL address transfer implementation
 */

public class AddressDTO extends CommonAddressDTO implements Serializable {

    private static final long serialVersionUID = -4590299984053070131L;

    public AddressDTO() {
    }

    public AddressDTO(Long id, String houseNameNumber, String street, String town, CountryDTO country, String postcode) {
        this.id = id;
        this.houseNameNumber = houseNameNumber;
        this.street = street;
        this.town = town;
        this.country = country;
        this.postcode = postcode;
    }

    public AddressDTO(String houseNameNumber, String street, String town, String postcode, CountryDTO country) {
        this.houseNameNumber = houseNameNumber;
        this.street = street;
        this.town = town;
        this.country = country;
        this.postcode = postcode;
    }

    public AddressDTO(String houseNameNumber, String street, String town, CountryDTO country) {
        this.houseNameNumber = houseNameNumber;
        this.street = street;
        this.town = town;
        this.country = country;
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AddressDTO that = (AddressDTO) object;
        return new EqualsBuilder().append(houseNameNumber, that.houseNameNumber)
        		.append(street,that.street).append(town,that.town)
                .append(postcode,that.postcode).append(country, that.country).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(CommonHashCodeSeed.ADDRESS_DTO.initialiser(),
                CommonHashCodeSeed.ADDRESS_DTO.multiplier()).append(houseNameNumber).append(street).append(town)
                .append(postcode).append(country).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(houseNameNumber).append(street).append(town)
                .append(postcode).append(getCountryName()).toString();
    }
    
    public AddressDTO(String houseNameNumber, String street, String town, String postcode, CountryDTO country, String county) {
        this.houseNameNumber = houseNameNumber;
        this.street = street;
        this.town = town;
        this.postcode = postcode;
        this.country = country;
        this.county = county;
    }
    
    public String getCountryCode() {
      return country == null ? null : country.getCode();
    }
    
    public String getCountryName() {
        return country == null ? null : country.getName();
    }
}
