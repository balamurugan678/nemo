package com.novacroft.nemo.tfl.common.util;

import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import org.apache.commons.lang3.StringUtils;

/**
 * Settlement utilities
 */
public final class SettlementUtil {

    public static Boolean isAutoLoadOn(AutoLoadChangeSettlementDTO autoLoadChangeSettlementDTO) {
        return autoLoadChangeSettlementDTO != null ? isAutoLoadOn(autoLoadChangeSettlementDTO.getAutoLoadState()) : false;
    }

    public static Boolean isAutoLoadOn(Integer autoLoadState) {
        return autoLoadState != null ? !AutoLoadState.NO_TOP_UP.state().equals(autoLoadState) : false;
    }

    public static Boolean isRequested(AutoLoadChangeSettlementDTO autoLoadChangeSettlementDTO) {
        return autoLoadChangeSettlementDTO != null && StringUtils.isNotBlank(autoLoadChangeSettlementDTO.getStatus()) ?
                SettlementStatus.REQUESTED.code().equalsIgnoreCase(autoLoadChangeSettlementDTO.getStatus()) : false;
    }

    private SettlementUtil() {
    }
}
