package com.novacroft.nemo.tfl.common.converter.financial_services_centre;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.AddressExportDTO;

/**
 * Transform address for export
 */
public interface AddressExportConverter {
    AddressExportDTO convert(AddressDTO addressDTO);
}
