package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.Customer;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between customer entity and DTO.
 */
@Component(value = "customerConverter")
public class CustomerConverterImpl extends BaseDtoEntityConverterImpl<Customer, CustomerDTO> {
    @Override
    protected CustomerDTO getNewDto() {
        return new CustomerDTO();
    }
}
