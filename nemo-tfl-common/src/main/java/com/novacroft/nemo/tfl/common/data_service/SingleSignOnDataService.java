package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;

public interface SingleSignOnDataService {
    
    boolean checkAndUpdateLocalData(SingleSignOnResponseDTO ssoResponseDTO);
}
