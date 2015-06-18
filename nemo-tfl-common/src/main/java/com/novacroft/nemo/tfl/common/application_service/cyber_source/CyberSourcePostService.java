package com.novacroft.nemo.tfl.common.application_service.cyber_source;

import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostRequestDTO;

/**
 * CyberSource Secure Acceptance http post application service specification
 */
public interface CyberSourcePostService {
    CyberSourcePostRequestDTO preparePaymentRequestData(OrderDTO orderDTO, PaymentCardSettlementDTO settlementDTO,
                                                        Boolean createToken, Boolean cookiesEnabledOnClient,
                                                        String clientIpAddress);
}
