package com.novacroft.nemo.tfl.services.converter;

import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;
import com.novacroft.nemo.tfl.services.transfer.Item;

/**
 * Bespoke converter for mapping properties from common PayAsYouGoDTO to webservice transfer object PayAsYouGo. 
 *
 */
public interface PayAsYouGoConverter {

    Item convertToItem(PayAsYouGoDTO payAsYouGoDTO);
    
}
