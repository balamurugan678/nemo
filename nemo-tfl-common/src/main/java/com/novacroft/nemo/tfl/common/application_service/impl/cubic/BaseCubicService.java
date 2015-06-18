package com.novacroft.nemo.tfl.common.application_service.impl.cubic;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.tfl.common.transfer.BaseResponseDTO;

public class BaseCubicService extends BaseService{
    
    protected Boolean isErrorResponse(BaseResponseDTO responseDTO) {
        return responseDTO.getErrorCode() != null;
    }
    
}
