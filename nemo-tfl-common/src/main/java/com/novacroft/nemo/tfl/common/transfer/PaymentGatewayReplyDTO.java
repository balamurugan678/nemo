package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceReplyDTO;

/**
 * Command class specification for payment gateway response
 */
public interface PaymentGatewayReplyDTO {
    PaymentCardSettlementDTO getPaymentCardSettlement();

    CyberSourceReplyDTO getCyberSourceReply();
}
