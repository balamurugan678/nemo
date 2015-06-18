package com.novacroft.nemo.tfl.common.converter.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.RequestMessage;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;

/**
 * CyberSource payment gateway request DTO/model converter
 */
public interface CyberSourceSoapRequestConverter {
    RequestMessage convertDtoToModel(CyberSourceSoapRequestDTO requestDTO);
}
