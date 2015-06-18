package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.WebAccountCreditSettlement;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditStatementLineDTO;

import java.util.List;

/**
 * Settlement (by Web Credit) data service specification
 */
public interface WebCreditSettlementDataService extends BaseDataService<WebAccountCreditSettlement, WebCreditSettlementDTO> {
    List<WebCreditStatementLineDTO> findByCustomerId(Long customerId);

    Integer getBalance(Long customerId);

    List<WebCreditSettlementDTO> findByOrderId(Long orderId);
}
