package com.novacroft.nemo.tfl.common.application_service.cyber_source;

import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;

/**
 * CyberSource Secure Acceptance soap application service specification
 */
public interface CyberSourceSoapService {
    CyberSourceSoapRequestDTO preparePaymentRequestData(OrderDTO orderDTO, PaymentCardSettlementDTO settlementDTO,
                                                        String clientIpAddress);
}
