package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.AutoLoadConfigurationChangePushToGateService;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.data_service.AutoLoadConfigurationChangePushToGateDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.CUBIC_PASSWORD;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.CUBIC_USERID;

/**
 * Push auto load configuration change to the gate (CUBIC)
 */
@Service("autoLoadConfigurationChangePushToGateService")
public class AutoLoadConfigurationChangePushToGateServiceImpl implements AutoLoadConfigurationChangePushToGateService {
    @Autowired
    protected AutoLoadConfigurationChangePushToGateDataService autoLoadConfigurationChangePushToGateDataService;
    @Autowired
    protected SystemParameterService systemParameterService;

    /**
     * @return request sequence number
     */
    @Override
    public Integer requestAutoLoadConfigurationChange(String cardNumber, Integer autoLoadState, Long pickUpLocation) {
        AutoLoadChangeRequestDTO requestDTO = new AutoLoadChangeRequestDTO(cardNumber, autoLoadState, pickUpLocation,
                this.systemParameterService.getParameterValue(CUBIC_USERID.code()),
                this.systemParameterService.getParameterValue(CUBIC_PASSWORD.code()));
        AutoLoadChangeResponseDTO responseDTO =
                this.autoLoadConfigurationChangePushToGateDataService.changeAutoLoadConfigurationRequest(requestDTO);
        if (isErrorResponse(responseDTO)) {
            throw new ApplicationServiceException(
                    String.format(PrivateError.AUTO_LOAD_CHANGE_REQUEST_FAILED.message(), responseDTO.getErrorCode(),
                            responseDTO.getErrorDescription()));
        }
        return responseDTO.getRequestSequenceNumber();
    }

    protected Boolean isErrorResponse(AutoLoadChangeResponseDTO responseDTO) {
        return responseDTO.getErrorCode() != null;
    }
}
