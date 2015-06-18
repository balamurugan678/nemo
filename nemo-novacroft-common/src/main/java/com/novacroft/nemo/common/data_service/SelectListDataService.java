package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.transfer.SelectListDTO;

/**
 * Select list data service specification
 */
public interface SelectListDataService {
    SelectListDTO findByName(String name);
}
