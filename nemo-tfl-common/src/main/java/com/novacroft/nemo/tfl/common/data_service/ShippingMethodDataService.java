package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.ShippingMethod;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodDTO;

/**
 * ShippingMethod data service specification
 */
public interface ShippingMethodDataService extends BaseDataService<ShippingMethod, ShippingMethodDTO> {
	ShippingMethodDTO findByShippingMethodName(String name, boolean exact);
}
