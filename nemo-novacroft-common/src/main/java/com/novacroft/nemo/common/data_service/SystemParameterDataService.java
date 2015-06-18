package com.novacroft.nemo.common.data_service;

import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.domain.SystemParameter;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;

/**
 * System Parameter data service specification
 */
public interface SystemParameterDataService extends BaseDataService<SystemParameter, SystemParameterDTO> {
    SystemParameterDTO findByCode(String code);
    
    List<SystemParameterDTO> listAllParameters();

    Date findLatestCreatedDateTime();

    Date findLatestModifiedDateTime();
}
