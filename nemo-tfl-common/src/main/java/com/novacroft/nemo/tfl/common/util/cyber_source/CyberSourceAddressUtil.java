package com.novacroft.nemo.tfl.common.util.cyber_source;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceAddressDTO;
import com.novacroft.nemo.tfl.common.util.AddressFormatUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Utilities for manipulating CyberSource addresses
 */
public final class CyberSourceAddressUtil {

    public static String extractHouseNameNumber(CyberSourceAddressDTO addressDTO) {
        if (StringUtils.isNotBlank(addressDTO.getLine2())) {
            return addressDTO.getLine1();
        }
        String firstWordOfLine1 = StringUtil.extractFirstWord(addressDTO.getLine1());
        if (AddressFormatUtil.isHouseNumber(firstWordOfLine1)) {
            return firstWordOfLine1;
        }
        return StringUtil.EMPTY_STRING;
    }

    public static String extractStreet(CyberSourceAddressDTO addressDTO) {
        if (StringUtils.isNotBlank(addressDTO.getLine2())) {
            return addressDTO.getLine2();
        }
        String firstWordOfLine1 = StringUtil.extractFirstWord(addressDTO.getLine1());
        return AddressFormatUtil.isHouseNumber(firstWordOfLine1) ? StringUtil.extractAfterFirstWord(addressDTO.getLine1()) :
                addressDTO.getLine1();
    }

    public static String extractTown(CyberSourceAddressDTO addressDTO) {
        return addressDTO.getCity();
    }

    public static String extractPostCode(CyberSourceAddressDTO addressDTO) {
        return addressDTO.getPostalCode();
    }

    public static String extractCountry(CyberSourceAddressDTO addressDTO) {
        return addressDTO.getCountry();
    }

    private CyberSourceAddressUtil() {
    }
}
