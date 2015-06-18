package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeDTO;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;

/**
 * Specification for administration fee service
 */
public interface AdministrationFeeService {
    AdministrationFeeItemDTO getNewAdministrationFeeItemDTO(CartItemCmdImpl cmd);
  
    AdministrationFeeDTO getAdministrationFeeDTO(String cartType);
}
