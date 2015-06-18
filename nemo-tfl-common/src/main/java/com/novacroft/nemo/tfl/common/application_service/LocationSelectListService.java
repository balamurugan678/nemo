package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.transfer.SelectListDTO;

/**
 * Location select list (station/NLC list of values) service specification
 */
public interface LocationSelectListService {
    SelectListDTO getLocationSelectList();
}
