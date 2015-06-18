package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.PaymentCardSettlement;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;

/**
 * Settlement (by Payment Card) data service specification
 */
public interface PaymentCardSettlementDataService extends BaseDataService<PaymentCardSettlement, PaymentCardSettlementDTO> {
    List<PaymentCardSettlementDTO> findByOrderId(Long orderId);
    
}
