package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatName;

/**
 * Base email preparation service
 */
@Component
public class BaseEmailPreparationService extends BaseService {
    @Autowired
    protected CustomerDataService customerDataService;

    public String getSalutation(CustomerDTO customerDTO) {
        return formatName(customerDTO.getFirstName(), customerDTO.getLastName());
    }
}
