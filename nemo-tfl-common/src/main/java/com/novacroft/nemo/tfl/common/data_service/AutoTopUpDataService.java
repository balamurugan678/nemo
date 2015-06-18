package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.AutoTopUp;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpDTO;

/**
 * Auto top-up data service specification
 */
public interface AutoTopUpDataService extends BaseDataService<AutoTopUp, AutoTopUpDTO> {

    AutoTopUpDTO findByAutoTopUpAmount(Integer autoTopUpAmount);
    


}
