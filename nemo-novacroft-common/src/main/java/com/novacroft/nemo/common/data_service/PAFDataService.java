package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.transfer.CommonAddressDTO;

import java.util.List;

/**
 * Royal Mail Postcode Address File (PAF) data service wrapper specification
 *
 * @see <a href="http://www.royalmail.com/sites/default/files/docs/pdf/programmers_guide_edition_7_v5.pdf">programmers_guide_edition_7_v5.pdf</a>
 */
public interface PAFDataService {
    List<CommonAddressDTO> findAddressesForPostcode(String postcode);

    void setSearchServiceEndpoint(String endpoint);
}
