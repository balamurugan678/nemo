package com.novacroft.nemo.common.application_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.SystemParameterDataService;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;

/**
 * System Parameter application service specification.
 */
public interface SystemParameterService {
    String getParameterValue(String code);
    
    Integer getIntegerParameterValue(String code);
    
    Float getFloatParameterValue(String code);
    
    String getParameterPurpose(String code);

    void setSystemParameterDataService(SystemParameterDataService systemParameterDataService);

    void setUseCache(Boolean useCache);

    void setCacheRefreshIntervalInSeconds(Integer cacheRefreshIntervalInSeconds);
    
    List<SystemParameterDTO> getAllProperties();

    void saveProperties(List<SystemParameterDTO> parameters);
    
    Boolean getBooleanParameterValue(String code);
}
