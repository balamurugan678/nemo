package com.novacroft.nemo.tfl.common.application_service.cyber_source;

import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostRequestDTO;

/**
 * CyberSource payment gateway security
 */
public interface CyberSourceSecurityService {
    void signPostRequest(CyberSourcePostRequestDTO cyberSourcePostRequestDTO);

    Boolean isPostReplySignatureValid(CyberSourcePostReplyDTO cyberSourcePostReplyDTO);
}
