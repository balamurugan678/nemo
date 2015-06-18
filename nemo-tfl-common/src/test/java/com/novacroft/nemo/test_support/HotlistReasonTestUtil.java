package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.tfl.common.domain.HotlistReason;
import com.novacroft.nemo.tfl.common.transfer.HotlistReasonDTO;

/**
 * Utilities for Card tests
 */
public final class HotlistReasonTestUtil {

    public static final String HOTLIST_REASON_1 = "Lost";
    public static final String HOTLIST_REASON_2 = "Stolen";
    public static final Long HOTLIST_REASON_ID = 1L;
    public static final Long HOTLIST_REASON_ID_3 = 3L;
    public static final Long HOTLISTED_CARD_REASON_ID = 5001L;
    public static final String LOST_STOLEN_OPTION_TYPE = "refundCard";
    public static final String HOTLIST_STATUS_READYTOEXPORT = "readytoexport";
    
    public static List<HotlistReasonDTO> getHotlistReasonList() {
        List<HotlistReasonDTO> hotlistReasons = new ArrayList<HotlistReasonDTO>(2);
        hotlistReasons.add(getHotlistReasonDTO1());
        hotlistReasons.add(getHotlistReasonDTO2());
        return hotlistReasons;
    }

    public static HotlistReasonDTO getHotlistReasonDTO1() {
        return getTestCard2(HOTLIST_REASON_1);
    }

    public static HotlistReasonDTO getHotlistReasonDTO2() {
        return getTestCard(HOTLIST_REASON_2);
    }
    
    public static HotlistReasonDTO getHotlistReasonDTO3() {
        return getTestCard2(HOTLIST_REASON_2);
    }

    public static HotlistReasonDTO getHotlistReasonDTO4() {
        return getTestCard3(HOTLIST_REASON_2);
    }

    public static HotlistReasonDTO getTestCard(String hotlistReason) {
        HotlistReasonDTO hotlistReasonDTO = new HotlistReasonDTO();
        hotlistReasonDTO.setDescription(hotlistReason);
        return hotlistReasonDTO;
    }
    
    public static HotlistReasonDTO getTestCard2(String hotlistReason) {
        HotlistReasonDTO hotlistReasonDTO = new HotlistReasonDTO();
        hotlistReasonDTO.setDescription(hotlistReason);
        hotlistReasonDTO.setId(HOTLIST_REASON_ID);
        return hotlistReasonDTO;
    }
    
    public static HotlistReasonDTO getTestCard3(String hotlistReason) {
        HotlistReasonDTO hotlistReasonDTO = new HotlistReasonDTO();
        hotlistReasonDTO.setDescription(hotlistReason);
        hotlistReasonDTO.setId(HOTLIST_REASON_ID_3);
        return hotlistReasonDTO;
    }

    public static HotlistReason getTestHotlistReason1() {
        HotlistReason reason = new HotlistReason();
        reason.setId(HOTLIST_REASON_ID);
        reason.setDescription(HOTLIST_REASON_1);
        return reason;
    }
}
