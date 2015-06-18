package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.ChequeSettlement;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

import java.util.List;

/**
 * Cheque settlement data service
 */
public interface ChequeSettlementDataService extends BaseDataService<ChequeSettlement, ChequeSettlementDTO> {
    List<ChequeSettlementDTO> findByStatus(String status);

    List<ChequeSettlementDTO> findAllByOrderNumber(Long orderNumber);

    ChequeSettlementDTO findByOrderNumber(Long orderNumber);

    ChequeSettlementDTO findByChequeSerialNumber(Long chequeSerialNumber);
    
    ChequeSettlementDTO findBySettlementNumber(Long chequeSerialNumber);
}
