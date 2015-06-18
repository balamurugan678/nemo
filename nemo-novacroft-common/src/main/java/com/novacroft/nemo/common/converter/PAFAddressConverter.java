package com.novacroft.nemo.common.converter;

import com.novacroft.nemo.common.transfer.CommonAddressDTO;
import com.novacroft.phoenix.service.paf.bean.PAFFullAddress;

import java.util.List;

/**
 * PAF (Postcode Address File) address converter specification
 */
public interface PAFAddressConverter {
    CommonAddressDTO convertEntityToDto(PAFFullAddress address);

    List<CommonAddressDTO> convertEntitiesToDtos(PAFFullAddress[] addresses);
}
