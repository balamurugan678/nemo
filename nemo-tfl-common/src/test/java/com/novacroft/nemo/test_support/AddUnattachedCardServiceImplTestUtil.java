package com.novacroft.nemo.test_support;

import com.novacroft.nemo.common.domain.cubic.HolderDetails;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

public class AddUnattachedCardServiceImplTestUtil {

    private AddUnattachedCardServiceImplTestUtil() {

    }

    public static HolderDetails getHolderDetails() {
        HolderDetails holderDetails = new HolderDetails();
        holderDetails.setHouseNumber(AddressTestUtil.TEST_HOUSE_NAME_1);
        holderDetails.setStreet(AddressTestUtil.STREET_1);
        holderDetails.setPostcode(AddressTestUtil.POSTCODE_1);
        holderDetails.setFirstName(CustomerTestUtil.FIRST_NAME_1);
        holderDetails.setLastName(CustomerTestUtil.LAST_NAME_1);
        return holderDetails;
    }

}
