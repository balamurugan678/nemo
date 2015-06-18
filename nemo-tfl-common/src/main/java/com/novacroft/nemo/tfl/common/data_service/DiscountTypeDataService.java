package com.novacroft.nemo.tfl.common.data_service;

import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.DiscountType;
import com.novacroft.nemo.tfl.common.transfer.DiscountTypeDTO;

public interface DiscountTypeDataService extends BaseDataService<DiscountType, DiscountTypeDTO> {
    DiscountTypeDTO findByName(String name);

    DiscountTypeDTO findByCode(String code, Date effectiveDate);
    
    List<DiscountTypeDTO> findAll();
}
