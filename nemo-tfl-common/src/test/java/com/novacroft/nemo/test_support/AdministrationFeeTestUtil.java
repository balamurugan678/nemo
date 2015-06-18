package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.AdministrationFee;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeDTO;

/**
 * Utilities for pay as you go product item tests
 */
public final class AdministrationFeeTestUtil {
    public static final String ADMINISTRATION_FEE_NAME_1 = "Administration Fee";
    public static final Integer ADMINISTRATION_FEE_PRICE_1 = 1000;
    public static final Integer ADMINISTRATION_FEE_PRICE_ZERO = 0;
    public static final Integer NEGATIVE_ADMINISTRATION_FEE = -1000;

    public static AdministrationFeeDTO getTestAdministrationFeeDTO1() {
        return getTestAdministrationFeeDTO(ADMINISTRATION_FEE_NAME_1, ADMINISTRATION_FEE_PRICE_1);
    }

    public static AdministrationFeeDTO getTestAdministrationFeeDTO(String type, Integer price) {
        AdministrationFeeDTO dto = new AdministrationFeeDTO();
        dto.setType(type);
        dto.setPrice(price);
        return dto;
    }

    public static AdministrationFee getTestAdministrationFee1() {
        return getTestAdministrationFee(ADMINISTRATION_FEE_NAME_1, ADMINISTRATION_FEE_PRICE_1);
    }

    public static AdministrationFee getTestAdministrationFee(String type, Integer price) {
        return new AdministrationFee(type, price);
    }

}
