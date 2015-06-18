package com.novacroft.nemo.tfl.common.converter.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.ReplyMessage;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;

/**
 * CyberSource payment gateway delete token reply DTO/model converter
 */
public interface CyberSourceSoapDeleteTokenReplyConverter {
    CyberSourceSoapReplyDTO convertModelToDto(ReplyMessage reply);
}
