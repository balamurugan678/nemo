package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.AdministrationFee;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeDTO;

/**
 * Administrative Fee data service specification
 */
public interface AdministrationFeeDataService extends BaseDataService<AdministrationFee, AdministrationFeeDTO> {

	AdministrationFeeDTO findByPrice(Integer price);

    AdministrationFeeDTO findByType(String type);

}
