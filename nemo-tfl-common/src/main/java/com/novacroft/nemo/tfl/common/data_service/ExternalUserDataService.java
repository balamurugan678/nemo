package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.ExternalUser;
import com.novacroft.nemo.tfl.common.transfer.ExternalUserDTO;

/**
 * ExternalUser data service specification
 */
public interface ExternalUserDataService extends BaseDataService<ExternalUser, ExternalUserDTO> {        
    Long findExternalUserIdByUsername(String loggedInUsername);
    ExternalUserDTO findByUsername(String username);
}
