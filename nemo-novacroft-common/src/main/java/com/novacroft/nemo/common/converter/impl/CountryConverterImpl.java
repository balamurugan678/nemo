package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.domain.Country;
import com.novacroft.nemo.common.transfer.CountryDTO;
import org.springframework.stereotype.Component;

/**
 * Country converter
 */
@Component(value = "countryConverter")
public class CountryConverterImpl extends BaseDtoEntityConverterImpl<Country, CountryDTO> {
    @Override
    protected CountryDTO getNewDto() {
        return new CountryDTO();
    }
}
