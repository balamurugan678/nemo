/**
 * 
 */
package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.BACSSettlement;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;

/**
 * BACS settlement data service
 *
 */
public interface BACSSettlementDataService extends BaseDataService<BACSSettlement, BACSSettlementDTO> {

    List<BACSSettlementDTO> findByStatus(String status);

    BACSSettlementDTO findBySettlementNumber(Long settlementNumber);
    
    BACSSettlementDTO  findByFinancialServicesReference(Long financialServicesReferenceNumber); 
    
    List<BACSSettlementDTO> findByOrderNumber(Long orderNumber);
    
}
