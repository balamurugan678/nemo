package com.novacroft.nemo.tfl.common.converter;

import com.novacroft.nemo.tfl.common.transfer.CustomerSearchResultDTO;

import java.util.List;

public interface CustomerSearchConverter {
    List<CustomerSearchResultDTO> convert(List<?> results);
}
