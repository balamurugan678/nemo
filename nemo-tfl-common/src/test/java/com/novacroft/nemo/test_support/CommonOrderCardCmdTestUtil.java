package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.AddressTestUtil.HOUSE_NAME_NUMBER_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.TOWN_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.LAST_NAME_1;
import static com.novacroft.nemo.test_support.ContactTestUtil.VALUE_1;
import static com.novacroft.nemo.test_support.ContactTestUtil.VALUE_2;

import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;

public final class CommonOrderCardCmdTestUtil {
    
    public static CommonOrderCardCmd getTestCommonOrderCardCmd() {
        CommonOrderCardCmd commonOrderCmd = new CommonOrderCardCmd();
        commonOrderCmd.setFirstName(FIRST_NAME_1);
        commonOrderCmd.setLastName(LAST_NAME_1);

        commonOrderCmd.setHouseNameNumber(HOUSE_NAME_NUMBER_1);
        commonOrderCmd.setTown(TOWN_1);

        commonOrderCmd.setHomePhone(VALUE_1);
        commonOrderCmd.setMobilePhone(VALUE_2);
        return commonOrderCmd;
    }
}
