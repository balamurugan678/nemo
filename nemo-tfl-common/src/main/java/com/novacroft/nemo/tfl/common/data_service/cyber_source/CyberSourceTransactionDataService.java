package com.novacroft.nemo.tfl.common.data_service.cyber_source;

import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;

/**
 * CyberSource payment gateway transaction data service
 */
public interface CyberSourceTransactionDataService {
    CyberSourceSoapReplyDTO runTransaction(CyberSourceSoapRequestDTO request);

    CyberSourceSoapReplyDTO deleteToken(CyberSourceSoapRequestDTO request);
}
