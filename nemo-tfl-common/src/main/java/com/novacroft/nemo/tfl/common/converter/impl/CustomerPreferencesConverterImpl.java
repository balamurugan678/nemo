package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.CustomerPreferences;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;
import org.springframework.stereotype.Component;

/**
 * TfL customer preferences converter implementation
 */
@Component(value = "customerPreferencesConverter")
public class CustomerPreferencesConverterImpl extends BaseDtoEntityConverterImpl<CustomerPreferences, CustomerPreferencesDTO> {
    @Override
    protected CustomerPreferencesDTO getNewDto() {
        return new CustomerPreferencesDTO();
    }
}
