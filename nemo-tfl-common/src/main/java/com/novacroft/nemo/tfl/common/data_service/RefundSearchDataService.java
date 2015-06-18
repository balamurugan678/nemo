package com.novacroft.nemo.tfl.common.data_service;

import java.util.Set;

import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

public interface RefundSearchDataService {

    Set<OrderDTO> findBySearchCriteria(RefundSearchCmd search);

}
