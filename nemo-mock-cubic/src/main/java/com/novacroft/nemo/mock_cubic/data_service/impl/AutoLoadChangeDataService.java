package com.novacroft.nemo.mock_cubic.data_service.impl;

import com.novacroft.nemo.mock_cubic.domain.AutoLoadChange;
import com.novacroft.nemo.tfl.common.data_service.AutoLoadChangeSettlementDataService;

import java.util.List;

/**
 * CUBIC Mock Auto Load Change Data Service
 */
public interface AutoLoadChangeDataService extends AutoLoadChangeSettlementDataService {
    List<AutoLoadChange> findRequestedAutoLoadChangeRecords();
}
