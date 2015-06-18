package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.tfl.common.constant.AutoTopUpActivityType;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpHistoryItemDTO;

/**
 * Utilities for Auto Top-up history tests
 */
public class AutoTopUpHistoryTestUtil {

    public static final String AUTO_TOP_UP_ACTIVITY = AutoTopUpActivityType.SET_UP.getCode();
    public static final String STATION_NAME = "Angel";
    public static final String ACTIVITY_DATE_TIME = DateTestUtil.AUG_01;
    public static final Integer AUTO_TOP_UP_AMOUNT = 3500;
    public static final String SETTLEMENT_DATE_TIME = DateTestUtil.AUG_02_AT_00_00;
    public static final String SETTLEMENT_STATUS = SettlementStatus.SUCCESSFUL.code();
    
    public static AutoTopUpHistoryItemDTO getTestAutoTopUpHistoryDTO1() {
        return getTestAutoTopUpHistoryItemDTO(AUTO_TOP_UP_ACTIVITY, STATION_NAME, ACTIVITY_DATE_TIME, AUTO_TOP_UP_AMOUNT, SETTLEMENT_DATE_TIME,
                        SETTLEMENT_STATUS);
    }

    public static AutoTopUpHistoryItemDTO getTestAutoTopUpHistoryItemDTO(String autoTopUpActivity, String stationName, String activityDateTime, Integer autoTopUpAmount,
                    String settlementDateTime, String settlementStatus) {
        AutoTopUpHistoryItemDTO dto = new AutoTopUpHistoryItemDTO(autoTopUpActivity, stationName, activityDateTime, autoTopUpAmount,
                    settlementDateTime, settlementStatus);
        return dto;
    }

    public static List<AutoTopUpHistoryItemDTO> getAutoTopUpHistoryList() {
        List<AutoTopUpHistoryItemDTO> autoTopUpHistoryList = new ArrayList<AutoTopUpHistoryItemDTO>();
        autoTopUpHistoryList.add(getTestAutoTopUpHistoryDTO1());
        return autoTopUpHistoryList;
    }
    
    
}
