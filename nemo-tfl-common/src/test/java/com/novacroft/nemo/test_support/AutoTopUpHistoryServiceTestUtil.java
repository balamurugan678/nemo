package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.AUTO_TOP_UP_ACTIVITY;
import static com.novacroft.nemo.test_support.DateTestUtil.AUG_22_AT_1723;
import static com.novacroft.nemo.test_support.DateTestUtil.AUG_23_AT_0429;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpHistoryItemDTO;

/**
 * Utilities for auto top-up history tests
 */
public final class AutoTopUpHistoryServiceTestUtil {

    public static final String STATION_NAME = "Angel";
    public static final Integer AUTO_TOPUP_AMOUNT = 2000;

    public static List<AutoTopUpHistoryItemDTO> getAutoTopUpHistoryItems() {
        List<AutoTopUpHistoryItemDTO> autoTopUpHistoryItems = new ArrayList<AutoTopUpHistoryItemDTO>();
        AutoTopUpHistoryItemDTO autoTopUpHistoryItemDTO = getAutoTopUpHistoryItem(AUTO_TOP_UP_ACTIVITY, STATION_NAME, AUG_22_AT_1723, AUTO_TOPUP_AMOUNT,
                        AUG_23_AT_0429, SettlementStatus.ACCEPTED.code());
        autoTopUpHistoryItems.add(autoTopUpHistoryItemDTO);
        return autoTopUpHistoryItems;
    }

    public static AutoTopUpHistoryItemDTO getAutoTopUpHistoryItem(String autoTopUpActivity, String stationName, String activityDateTime, Integer autoTopUpAmount,
                    String settlementDateTime, String settlementStatus) {
        AutoTopUpHistoryItemDTO itemDTO = new AutoTopUpHistoryItemDTO(autoTopUpActivity, stationName, activityDateTime, autoTopUpAmount,
                        settlementDateTime, settlementStatus);
        return itemDTO;
    }

}
