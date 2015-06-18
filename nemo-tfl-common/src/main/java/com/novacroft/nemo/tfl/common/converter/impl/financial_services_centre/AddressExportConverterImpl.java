package com.novacroft.nemo.tfl.common.converter.impl.financial_services_centre;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.converter.financial_services_centre.AddressExportConverter;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.AddressExportDTO;
import org.springframework.stereotype.Service;

import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatLine1;
import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatLine2;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Transform address for export
 */
@Service("addressExportConverter")
public class AddressExportConverterImpl implements AddressExportConverter {
    @Override
    public AddressExportDTO convert(AddressDTO addressDTO) {
        AddressExportDTO addressExportDTO = new AddressExportDTO();
        addressExportDTO.setCustomerAddressPostCode(addressDTO.getPostcode());
        addressExportDTO.setCustomerAddressTown(addressDTO.getTown());

        String line1 = formatLine1(addressDTO.getHouseNameNumber(), addressDTO.getStreet());
        String line2 = formatLine2(addressDTO.getHouseNameNumber(), addressDTO.getStreet());

        if (isBlank(line2)) {
            addressExportDTO.setCustomerAddressStreet(line1);
        } else {
            addressExportDTO.setCustomerAddressStreet(line2);
            addressExportDTO.setCustomerAddressLine2(line1);
        }
        return addressExportDTO;
    }
}
