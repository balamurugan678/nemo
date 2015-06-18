package com.novacroft.nemo.tfl.common.transfer;

/**
 * Handle cubic error response
 */
public interface BaseResponseDTO {
    Integer getErrorCode();
    
    String getErrorDescription();
}
