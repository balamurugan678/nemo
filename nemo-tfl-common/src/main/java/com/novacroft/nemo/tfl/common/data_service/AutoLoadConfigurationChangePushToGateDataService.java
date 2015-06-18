package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeResponseDTO;

/**
 * Call CUBIC Auto-load Configuration Change service
 */
public interface AutoLoadConfigurationChangePushToGateDataService {
    AutoLoadChangeResponseDTO changeAutoLoadConfigurationRequest(AutoLoadChangeRequestDTO request);
}
