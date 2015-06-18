package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.tfl.common.domain.GoodwillReason;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;

public final class GoodwillReasonTestUtil {

    public static final Long REASONID = 1L;
    private static final String DESCRIPTION = "Test Description";
    public static final Long REASONID1 = 10L;
    private static final String DESCRIPTION1 = "Test Description1";
    public static final String TYPE = "Test Type";
    public static final String EXTRA_VALIDATION_CODE = "Test Extra Validation Code";
    public static final String GOODWILL_REASON_OTHER = "OTHER";
    public static final String MESSAGE = "test message";

    public static List<GoodwillReasonDTO> getGoodwillReasonDTOList() {
        List<GoodwillReasonDTO> goodwillReasonDTOs = new ArrayList<GoodwillReasonDTO>();
        goodwillReasonDTOs.add(getGoodwillReasonDTO1());
        goodwillReasonDTOs.add(getGoodwillReasonDTO2());
        return goodwillReasonDTOs;
    }

    public static GoodwillReasonDTO getGoodwillReasonDTO1() {
        return buildDTO(REASONID, DESCRIPTION);
    }

    public static GoodwillReasonDTO getGoodwillReasonDTO2() {
        return buildDTO(REASONID1, DESCRIPTION1);
    }

    private static GoodwillReasonDTO buildDTO(Long reasonId, String description) {
        GoodwillReasonDTO goodwillReasonDTO = new GoodwillReasonDTO(reasonId, description);
        goodwillReasonDTO.setType(TYPE);
        goodwillReasonDTO.setExtraValidationCode(EXTRA_VALIDATION_CODE);
        return goodwillReasonDTO;
    }
    
    private static GoodwillReason buildGoodwillReason(Long reasonId, String description, String type) {
        GoodwillReason reason = new GoodwillReason();
        reason.setReasonId(reasonId);
        reason.setDescription(description);
        reason.setType(type);
        return reason;
    }
    
    public static GoodwillReason getTestGoodwillReason1() {
        return buildGoodwillReason(REASONID1, DESCRIPTION1, TYPE);
    }

}
