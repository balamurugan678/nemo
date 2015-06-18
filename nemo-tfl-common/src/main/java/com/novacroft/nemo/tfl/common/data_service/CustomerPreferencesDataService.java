package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.CustomerPreferences;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;

/**
 * Customer preferences data service specification
 */
public interface CustomerPreferencesDataService extends BaseDataService<CustomerPreferences, CustomerPreferencesDTO> {
    CustomerPreferencesDTO findByCustomerId(Long customerId);
}
