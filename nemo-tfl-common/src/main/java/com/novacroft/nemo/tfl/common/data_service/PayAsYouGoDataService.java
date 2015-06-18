package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGo;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;

/**
 * Pay as you go data service specification
 */
public interface PayAsYouGoDataService extends BaseDataService<PayAsYouGo, PayAsYouGoDTO> {

	PayAsYouGoDTO findByTicketPrice(Integer price);

}
