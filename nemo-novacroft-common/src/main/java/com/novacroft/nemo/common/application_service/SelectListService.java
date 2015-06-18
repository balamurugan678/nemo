package com.novacroft.nemo.common.application_service;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;

import java.util.List;

/**
 * Select list (list of values) service specification
 */
public interface SelectListService {
    List<SelectListOptionDTO> getSelectListOptions(String name);

    SelectListDTO getSelectList(String name);
}
