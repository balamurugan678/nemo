package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.transfer.RefundSearchResultDTO;

public interface RefundSearchService {

    List<RefundSearchResultDTO> getAllRefunds();

    List<RefundSearchResultDTO> findBySearchCriteria(RefundSearchCmd search);

}
