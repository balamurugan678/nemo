package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.GoodwillReason;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;

/**
 * Pay as you go data service specification
 */
public interface GoodwillReasonDataService extends BaseDataService<GoodwillReason, GoodwillReasonDTO> {

    GoodwillReasonDTO findByTicketPrice(Integer price);

    List<GoodwillReasonDTO> findByType(String type);
    
    GoodwillReasonDTO findByReasonId(Long reasonId);
}
